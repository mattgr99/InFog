package com.perdiz.neblina.heuristics;

import java.util.*;


public class TrafficRandomHeuristc extends Thread{
    public static Hashtable<Integer, ArrayList> vmTurnOn;
    public static ArrayList<Integer> ramVM = new ArrayList<Integer>();
    public static ArrayList<Integer> copyRam = new ArrayList<Integer>();
    public static ArrayList<Integer> resetRam = new ArrayList<Integer>();
    public static ArrayList<Double> energyServer = new ArrayList<Double>();
    public static ArrayList<Integer> slots = new ArrayList<Integer>();
    public static ArrayList<Integer> vms_on = new ArrayList<Integer>();
    public static Hashtable<Integer, ArrayList> trfList = new Hashtable<Integer, ArrayList>();
    public static Hashtable<Integer, ArrayList> trfListReset = new Hashtable<Integer, ArrayList>();
    public static int alt11;  //test
    private int timeTr;
    private int intervalRandom1;
    private int intervalRandom2;
    private boolean fnPr;
    private static boolean restoreTr;
    private String nameSen;

    private static int Delta;  // slot duration (s)
    private static int Ns;     //Number of physical server
    private static int M_max;  //Maximum number of Vms per physical servers
    private static double T_on_server; //Time for Turn On a PM delta [s] (0.1 * delta)
    private static double T_on_vm;    //Time for Turn On a VM waited by turned on delta server
    private int CIN;         //Number of consolidations instances
    private static double f_max;   //scalar valued common maximum frequency of the VMs
    private static double f_zero;
    private static double l_zero;
    private static int E_max;
    //private ArrayList<Integer> E_idle = new ArrayList<>();
    private static int E_idle;
    private static int SS;
    private static int m;      //traffic for each vm
    private static int v;
    private static int f;
    private static int y;
    private static double k_e;
    private static double x;
    private static double delta_shaping_factor;
    private static double outputEnergy_server;



    public TrafficRandomHeuristc(int vms, ArrayList<Integer> ramVM1, int sec, String name, Hashtable<Integer, ArrayList> trf, int interval1, int interval2){
        vmTurnOn = new Hashtable<>();
        Delta = 1;
        Ns = 1;
        M_max = vms;
        T_on_server= Math.pow(10,-1);
        T_on_vm= Math.pow(10,-2);
       // this.CIN = cin;
        f_max = 100.11;
        E_max = 453;

        //random numbers between 5 and 450
        /*for (int nvm = 0; nvm<vms; nvm++){
            int rnumber = (int)(Math.random()*(450-5+1)+5);
            this.E_idle.add(rnumber);
        }*/
        SS= 1;
        m=0;
        v=2;
        k_e = 0 * Math.pow(10,-5);
        f_zero = 0;
        l_zero = 0;
        f = 0;
        y= 0;
        x = 0.0;
        delta_shaping_factor = Math.pow(10,-2);
        outputEnergy_server= 0.0;
        this.timeTr = sec;
        //ramVM = ramVM1;
        ramVM = (ArrayList<Integer>)ramVM1.clone();

        trfList = (Hashtable<Integer, ArrayList>) trf.clone();
        /*for (int k=0; k<ramVM1.size(); k++){
            ramVM.add(ramVM1.get(k));
        }*/
        this.fnPr = false;
        this.nameSen = name;
        this.intervalRandom1 = interval1;
        this.intervalRandom2 = interval2;
        restoreTr = false;
        //resetRam = (ArrayList<Integer>)ramVM1.clone();
        for (int k=0; k<ramVM.size(); k++){
            copyRam.add(ramVM.get(k));
            //resetRam.add(ramVM.get(k));

            /* Estado Vms
             0 -> apagado
             1 -> encendido
            */
            //ArrayList<Integer> balance = new ArrayList<>();
            //ArrayList<Integer> balance1 = new ArrayList<>();
            //balance.add(0);
            //balance1.add(0);
            //trfList.put(k,balance);
            //trfListReset.put(k,balance1);
        }
        //trfListReset = (Hashtable<Integer, ArrayList>) trfList.clone();

        //alt11 = 0;



    }

    @Override
    public void run() {
        while (!fnPr){
            esperarXsegundos(this.timeTr);
            calcLoadServer();
        }


    }

    public static void resetServer(ArrayList<Integer> ramVM1){
        vmTurnOn = trfList;
        energyServer();
        //clstrfList = trfListReset;
        restoreTr = true;
        ramVM = (ArrayList<Integer>)ramVM1.clone();
        //trfList = (Hashtable<Integer, ArrayList>) trf.clone();
        //ramVM = resetRam;

        //ramVM.clear();

        Enumeration<Integer> ekey = trfList.keys();
        //System.out.println(trfList);
        //System.out.println(trfAccept);

        while(ekey.hasMoreElements()){
            trfList.remove(ekey.nextElement());
        }

        for (int k=0; k<ramVM.size(); k++){
          //  ramVM.add(copyRam.get(k));

            /* State Vms
             0 -> turn off
             1 -> turn on
            */
            ArrayList<Integer> balance = new ArrayList<>();

            balance.add(0);
            trfList.put(k,balance);
        }
    }


    public void calcLoadServer(){
        //long time_start, time_end;
        int cin1 = (int)(Math.random()*(this.intervalRandom1- this.intervalRandom2 + 1) + this.intervalRandom2);
        int vmOptimum = 0;
        int pos = 0;
        int vmOn = 0;
        boolean flag = false;
        boolean firstTrf =true;
        boolean unf = false;

        Hashtable<Integer, Integer> trfAccept = new Hashtable<Integer, Integer>();



        //System.out.println(trfList);
        Enumeration<Integer> ekey = trfAccept.keys();
        Enumeration<Integer> ekeys = trfAccept.keys();

        System.out.println("--------------Load Server------------------");
        System.out.println("--------------Sensor: "+ this.nameSen+" (" + this.timeTr +  "s) ------------------");

           // time_start = System.currentTimeMillis();
            System.out.println("Server 1 ");
            System.out.println("Traffic "+ cin1);
            if(cin1 > (Ns * M_max * f_max * (Delta - T_on_server - T_on_vm))){
                System.out.println("Consolidation unfeasible at slot " + cin1);
               // System.out.println(" " + (this.Ns * this.M_max * f_max * (this.Delta - this.T_on_server - this.T_on_vm)));
                unf= true;
            }


            //System.out.println(np.getMessage());
           if(!unf){
                for (int j=0; j<ramVM.size(); j++){
                    if(cin1 <= ramVM.get(j)){

                        if(!firstTrf){
                            ArrayList<Integer> serverOn = trfList.get(j);
                            if(serverOn.get(0)==1){
                                int total = ramVM.get(j) - cin1;
                                trfAccept.put(j, total);
                            }

                        }else {
                            int total = ramVM.get(j) - cin1;
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
                            if(cin1 <= slot1){
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
                                        ramVM.set(vm1,slot1 - cin1);
                                        ArrayList<Integer> balanceAdd = trfList.get(vm2);
                                        balanceAdd.add(balanceImp.get(ramPos));
                                        trfList.put(vm2,balanceAdd);

                                        balanceImp.remove(balanceImp.get(ramPos));
                                        balanceImp.add(cin1);
                                        trfList.put(vm1,balanceImp);

                                        System.out.println("-------Transfer traffic " + trfTransfer + " (VM " + (vm1+1) + " RAM " + copyRam.get(vm1) + " Mb) " + "to VM " + (vm2+1) + " RAM " + copyRam.get(vm2)+ " Mb-------");
                                        System.out.println("In Use: " + (copyRam.get(vm2) - ramVM.get(vm2)) + "Mb ");
                                        System.out.println("Available: " + ramVM.get(vm2) + "Mb ");
                                        System.out.println("----------------------------------------------------------------");

                                        System.out.println("-------Process in VM " + (vm1+1) + " Total RAM Memory: " + copyRam.get(vm1) + " Mb-------");
                                        System.out.println("In Use: " + (copyRam.get(vm1) - ramVM.get(vm1)) + "Mb ");
                                        System.out.println("Available: " + ramVM.get(vm1) + "Mb ");
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
                            if(cin1 <= ramVM.get(k)){
                                int total = ramVM.get(k) - cin1;
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
                        balanceTrf.add(cin1);
                        trfList.put(pos,balanceTrf);
                        ramVM.set(pos,vmOptimum);
                        System.out.println("-------Process in VM " + (pos+1) + " Total RAM Memory: " + copyRam.get(pos) + " Mb-------");
                        System.out.println("In Use: " + (copyRam.get(pos) - ramVM.get(pos)) + "Mb ");
                        System.out.println("Available: " + ramVM.get(pos) + "Mb ");
                    }else{
                        System.out.println("The VMs don't support the traffic "+ cin1);

                    }
                }
            }




            System.out.println("");
            //time_end = System.currentTimeMillis();
            //System.out.println("The task has taken "+ ( time_end - time_start ) +" ms");
            System.out.println("****************************************");
            System.out.println("");
            //System.out.println(vmOn);
            vmOn=0;
            flag = false;
            firstTrf =false;
            ekey = trfAccept.keys();
            System.out.println(trfList);
            //System.out.println(ramVM);
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



        //System.out.println("Cost server: " + this.x);
        //System.out.println("--------------------------------");

    }

    public static void energyServer(){

        //ArrayList<Double>
        int workload = 0;
        for (Map.Entry<Integer, ArrayList> von : trfList.entrySet()){


            ArrayList<Integer> serverOn = von.getValue();
            if(serverOn.get(0)==1){
                m++;
                for(int i = 1; i< serverOn.size(); i++){
                    workload = workload + serverOn.get(i);
                }

            }
        }
        vms_on.add(m);
        slots.add(workload);
        //System.out.println(m);
       // System.out.println(workload);
        f_zero = workload/(m * (Delta - T_on_server - T_on_vm));
        E_idle = 25;
        outputEnergy_server = (2/(1 + Math.exp(-(SS)/delta_shaping_factor)) - 1) * ((E_idle + SS *(( E_max - E_idle)/M_max)*
                (Math.pow(f_zero/f_max,v)) + k_e * SS * (Math.pow(f_zero - y,2))));

        System.out.println("--------------------------------------------------------");
        System.out.println("Energy Server                                          |");
        System.out.println("                                                       |");
        System.out.println(outputEnergy_server+ "                                      |");
        System.out.println("--------------------------------------------------------");
        m = 0;
        energyServer.add(outputEnergy_server);
        outputEnergy_server = 0.0;


    }




    public void setFnPr(boolean fnPr) {
        this.fnPr = fnPr;
    }

    private void esperarXsegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
