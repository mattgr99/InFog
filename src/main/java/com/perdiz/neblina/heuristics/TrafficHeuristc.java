package com.perdiz.neblina.heuristics;

import com.perdiz.neblina.util.Console;

import java.util.*;

import static com.perdiz.neblina.app.controller.AppController.footer;

public class TrafficHeuristc {
    private Hashtable<Integer, ArrayList> vmTurnOn;
    private String nameFogServer;
    private int Delta;  // slot duration (s)
    private int Ns;     //Number of physical server
    private int M_max;  //Maximum number of Vms per physical servers
    private double T_on_server; //Time for Turn On a PM delta [s] (0.1 * delta)
    private double T_on_vm;    //Time for Turn On a VM waited by turned on delta server
    private int CIN;         //Number of consolidations instances
    private double f_max;   //scalar valued common maximum frequency of the VMs
    private double f_zero;
    private double l_zero;
    private int E_max;
    //private ArrayList<Integer> E_idle = new ArrayList<>();
    private int E_idle;
    private int SS;
    private int m;      //traffic for each vm
    private int v;
    private int f;
    private int y;
    private double k_e;
    private double x;
    private double delta_shaping_factor;
    private double outputEnergy_server;

    public TrafficHeuristc(int vms, int cin, String nameFg){
        this.nameFogServer = nameFg;
        this.vmTurnOn = new Hashtable<>();
        this.Delta = 1;
        this.Ns = 1;
        this.M_max = vms;
        this.T_on_server= Math.pow(10,-1);
        this.T_on_vm= Math.pow(10,-2);
        this.CIN = cin;
        this.f_max = 100.11;
        this.E_max = 453;

        //random numbers between 5 and 450
        /*for (int nvm = 0; nvm<vms; nvm++){
            int rnumber = (int)(Math.random()*(450-5+1)+5);
            this.E_idle.add(rnumber);
        }*/
        this.SS= 1;
        this.m=0;
        this.v=2;
        this.k_e = 0 * Math.pow(10,-5);
        this.f_zero = 0;
        this.l_zero = 0;
        this.f = 0;
        this.y= 0;
        this.x = 0.0;
        this.delta_shaping_factor = Math.pow(10,-2);
        this.outputEnergy_server= 0.0;


    }

    public void calcLoadServer(ArrayList<Integer> cin1, ArrayList<Integer> ramVM){
        long time_start, time_end;
        int vmOptimum = 0;
        int pos = 0;
        int vmOn = 0;
        boolean flag = false;
        boolean firstTrf =true;
        Hashtable<Integer, ArrayList> trfList = new Hashtable<Integer, ArrayList>();
        Hashtable<Integer, Integer> trfAccept = new Hashtable<Integer, Integer>();
        ArrayList<Integer> copyRam = new ArrayList<Integer>();

        for (int k=0; k<ramVM.size(); k++){
            copyRam.add(ramVM.get(k));

            /* Estado Vms
             0 -> apagado
             1 -> encendido
            */
            ArrayList<Integer> balance = new ArrayList<>();

            balance.add(0);
            trfList.put(k,balance);
        }
        //System.out.println(trfList);
        Enumeration<Integer> ekey = trfAccept.keys();
        Enumeration<Integer> ekeys = trfAccept.keys();

        System.out.println("--------------Load Server------------------");
        footer.addLog("${_}", "--------------Load Server------------------");
        for (int i=0; i<this.CIN; i++){

            time_start = System.currentTimeMillis();
            System.out.println("Server: "   + this.nameFogServer);
            footer.addLog("${_}", "Server: " + this.nameFogServer);
            System.out.println("Traffic "+ cin1.get(i));
            footer.addLog("${_}", "Traffic "+ cin1.get(i));
            if(cin1.get(i) > (this.Ns * this.M_max * f_max * (this.Delta - this.T_on_server - this.T_on_vm))){
                System.out.println("Consolidation unfeasible at slot " + cin1.get(i));
                footer.addLog("${_}", "Consolidation unfeasible at slot " + cin1.get(i));
               // System.out.println(" " + (this.Ns * this.M_max * f_max * (this.Delta - this.T_on_server - this.T_on_vm)));
                continue;
            }

            for (int j=0; j<ramVM.size(); j++){
                if(cin1.get(i) <= ramVM.get(j)){

                    if(!firstTrf){
                        ArrayList<Integer> serverOn = trfList.get(j);
                        if(serverOn.get(0)==1){
                            int total = ramVM.get(j) - cin1.get(i);
                            trfAccept.put(j, total);
                        }

                    }else {
                        int total = ramVM.get(j) - cin1.get(i);
                        trfAccept.put(j, total);
                    }

                }
            }

            for (int j=0; j<ramVM.size(); j++){

                ArrayList<Integer> serverOn1 = trfList.get(j);
                if(serverOn1.get(0)==1){
                    vmOn++;
                }

            }


            if (trfAccept.isEmpty()){
                boolean vmMig = false;
                if(vmOn>1){

                    //------------------------------------------------------------------------
                    for (int vm1=0; vm1<ramVM.size(); vm1++){
                        ArrayList<Integer> serverOn = trfList.get(vm1);
                        if((serverOn.get(0)!=1) || (ramVM.get(vm1)==0)){
                            continue;
                        }
                        ArrayList<Integer> balanceImp = trfList.get(vm1);
                        int ramPos = balanceImp.size()-1;
                        int slot1 = balanceImp.get(ramPos) + ramVM.get(vm1);
                        if(cin1.get(i) <= slot1){
                            for (int vm2=0; vm2<ramVM.size(); vm2++){
                                ArrayList<Integer> serverTOn = trfList.get(vm2);
                                if((serverTOn.get(0)!=1) || (ramVM.get(vm2)==0)){
                                    continue;
                                }
                                if (vm2==vm1)
                                    continue;
                                if (balanceImp.get(ramPos) <= ramVM.get(vm2)){

                                    int trfTransfer = balanceImp.get(ramPos);
                                    ramVM.set(vm2,ramVM.get(vm2) - balanceImp.get(ramPos));
                                    ramVM.set(vm1,slot1 - cin1.get(i));
                                    ArrayList<Integer> balanceAdd = trfList.get(vm2);
                                    balanceAdd.add(balanceImp.get(ramPos));
                                    trfList.put(vm2,balanceAdd);

                                    balanceImp.remove(balanceImp.get(ramPos));
                                    balanceImp.add(cin1.get(i));
                                    trfList.put(vm1,balanceImp);

                                    System.out.println("-------Transfer traffic " + trfTransfer + " (VM " + (vm1+1) + " RAM " + copyRam.get(vm1) + " Mb) " + "to VM " + (vm2+1) + " RAM " + copyRam.get(vm2)+ " Mb-------");
                                    footer.addLog("${_}", "-------Transfer traffic " + trfTransfer + " (VM " + (vm1+1) + " RAM " + copyRam.get(vm1) + " Mb) " + "to VM " + (vm2+1) + " RAM " + copyRam.get(vm2)+ " Mb-------");
                                    System.out.println("In Use: " + (copyRam.get(vm2) - ramVM.get(vm2)) + "Mb ");
                                    footer.addLog("${_}", "In Use: " + (copyRam.get(vm2) - ramVM.get(vm2)) + "Mb ");
                                    System.out.println("Available: " + ramVM.get(vm2) + "Mb ");
                                    footer.addLog("${_}", "Available: " + ramVM.get(vm2) + "Mb ");
                                    System.out.println("----------------------------------------------------------------");
                                    footer.addLog("${_}", "----------------------------------------------------------------");
                                    System.out.println("-------Process in VM " + (vm1+1) + " Total RAM Memory: " + copyRam.get(vm1) + " Mb-------");
                                    footer.addLog("${_}", "-------Process in VM " + (vm1+1) + " Total RAM Memory: " + copyRam.get(vm1) + " Mb-------");
                                    System.out.println("In Use: " + (copyRam.get(vm1) - ramVM.get(vm1)) + "Mb ");
                                    footer.addLog("${_}", "In Use: " + (copyRam.get(vm1) - ramVM.get(vm1)) + "Mb ");
                                    System.out.println("Available: " + ramVM.get(vm1) + "Mb ");
                                    footer.addLog("${_}", "Available: " + ramVM.get(vm1) + "Mb ");
                                    flag = true;
                                    break;

                                }
                            }
                        }
                        if(flag){
                            break;
                        }
                    }

                //--------------------------------------------------------------------------


                }else{
                    vmMig = true;
                }

                if(!flag){
                    vmMig = true;
                }

                if (vmMig){
                    for (int k=0; k<ramVM.size(); k++){
                        if(cin1.get(i) <= ramVM.get(k)){
                            int total = ramVM.get(k) - cin1.get(i);
                            trfAccept.put(k, total);
                        }
                    }
                }
            }

            if(!flag){
                for (Map.Entry<Integer, Integer> von : trfAccept.entrySet()){

                    vmOptimum = von.getValue();
                    pos = von.getKey();

                }

                for (Map.Entry<Integer, Integer> e : trfAccept.entrySet()){
                    if ( e.getValue() < vmOptimum){
                        vmOptimum =e.getValue();
                        pos = e.getKey();
                    }
                }

                if (!trfAccept.isEmpty()){
                    ArrayList<Integer> balanceTrf = trfList.get(pos);
                    balanceTrf.set(0,1);
                    balanceTrf.add(cin1.get(i));
                    trfList.put(pos,balanceTrf);
                    ramVM.set(pos,vmOptimum);
                    System.out.println("-------Process in VM " + (pos+1) + " Total RAM Memory: " + copyRam.get(pos) + " Mb-------");
                    footer.addLog("${_}", "-------Process in VM " + (pos+1) + " Total RAM Memory: " + copyRam.get(pos) + " Mb-------");
                    System.out.println("In Use: " + (copyRam.get(pos) - ramVM.get(pos)) + "Mb ");
                    footer.addLog("${_}", "In Use: " + (copyRam.get(pos) - ramVM.get(pos)) + "Mb ");
                    System.out.println("Available: " + ramVM.get(pos) + "Mb ");
                    footer.addLog("${_}", "Available: " + ramVM.get(pos) + "Mb ");
                }else{
                    System.out.println("The VMs don't support the traffic "+ cin1.get(i));
                    footer.addLog("${_}", "The VMs don't support the traffic "+ cin1.get(i));

                }
            }


            System.out.println("");
            time_end = System.currentTimeMillis();
            System.out.println("The task has taken "+ ( time_end - time_start ) +" ms");
            footer.addLog("${_}", "The task has taken "+ ( time_end - time_start ) +" ms");
            System.out.println("****************************************");
            footer.addLog("${_}", "****************************************");
            System.out.println("");
            //System.out.println(vmOn);
            vmOn=0;
            flag = false;
            firstTrf =false;
            ekey = trfAccept.keys();
            System.out.println(trfList);
            footer.addLog("${_}", "" + trfList);
            //System.out.println(trfAccept);

            while(ekey.hasMoreElements()){
                trfAccept.remove(ekey.nextElement());
            }
            //trfAccept = new Hashtable<Integer, Integer>();

            /*
----------------------------------------------------------------------------------------------
 */
            //random numbers between 5 and 450
            //this.E_idle solo cuando esta prendido la vm
           // this.E_idle = (int)(Math.random()*(450-5+1)+5);
            //this.E_idle = 25;
            //this.f_zero = cin1.get(i)/(this.m * (this.Delta - this.T_on_server - this.T_on_vm));
            //this.l_zero = cin1.get(i)/this.m;
           /* this.x = this.E_idle + (this.SS + 1)*((this.E_max - this.E_idle)/this.M_max)*(Math.pow(this.f_zero/this.f_max,this.v)) +
                    (this.SS + 1) * this.k_e * (Math.pow(this.f_zero - this.y,2));       //vedo se mi conviene mettere la VM sul k-esimo server
*/
            //energia total
           /* this.x =  (((2/(1 + Math.exp(0/this.delta_shaping_factor)) - 1)* 2/(1 + Math.exp(0/this.delta_shaping_factor)) - 1))*
                    (E_idle + (this.SS*((this.E_max - this.E_idle)/this.M_max)*(Math.pow(f_zero/f_max,v))) +
                            this.SS * this.k_e *(Math.pow(this.f_zero - this.y,2)));
*/

/*
----------------------------------------------------------------------------------------------
 */
        }

        this.vmTurnOn = trfList;
        //System.out.println("Cost server: " + this.x);
        System.out.println("--------------------------------");
        footer.addLog("${_}", "--------------------------------");

    }

    public void energyServer(){

        //ArrayList<Double>
        int workload = 0;
        for (Map.Entry<Integer, ArrayList> von : vmTurnOn.entrySet()){


            ArrayList<Integer> serverOn = von.getValue();
            if(serverOn.get(0)==1){
                this.m++;
                for(int i = 1; i< serverOn.size(); i++){
                    workload = workload + serverOn.get(i);
                }

            }
        }
        //System.out.println(m);
        //System.out.println(workload);
        this.f_zero = workload/(this.m * (this.Delta - this.T_on_server - this.T_on_vm));
        this.E_idle = 25;
        this.outputEnergy_server = (2/(1 + Math.exp(-(this.SS)/this.delta_shaping_factor)) - 1) * ((this.E_idle + this.SS *(( this.E_max - this.E_idle)/this.M_max)*
                (Math.pow(f_zero/f_max,v)) + this.k_e * this.SS * (Math.pow(this.f_zero - this.y,2))));

        System.out.println("Energy Server");
        footer.addLog("${_}", "Energy Server");
        System.out.println("");
        footer.addLog("${_}", "");
        System.out.println(this.outputEnergy_server);
        footer.addLog("${_}", ""+this.outputEnergy_server);
        this.m = 0;


    }
}
