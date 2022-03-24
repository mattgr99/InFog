package com.perdiz.neblina.charts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BarChartTraffic extends Application {
    public ArrayList<Double> energyList = new ArrayList<Double>();

    public BarChartTraffic(ArrayList<Double> energy){
        this.energyList = energy;
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Energy Consumption server");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(/*1, 22, 0.5*/);
        xAxis.setLabel("Time(sec)");
        yAxis.setLabel("Energy Consumption (joules)");
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis){
            @Override
            public String toString(Number object){
                return String.format("%7.2f", object);
            }
        });
        final javafx.scene.chart.BarChart<String, Number> barChart = new javafx.scene.chart.BarChart<String, Number> (xAxis, yAxis);

        //barChart.setCreateSymbols(false);
        barChart.setAlternativeRowFillVisible(false);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Server");
        for (int i = 0; i<energyList.size(); i++){
            series1.getData().add(new XYChart.Data("" + ((i+1)*5), energyList.get(i)));
        }



        BorderPane pane = new BorderPane();
        pane.setCenter(barChart);
        Scene scene = new Scene(pane, 800, 600);
        barChart.getData().addAll(series1);

        stage.setScene(scene);

        stage.show();
    }
}
