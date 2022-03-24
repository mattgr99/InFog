package com.perdiz.neblina.charts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LineChartTraffic extends Application {
    private String labelCX;
    private String labelCY;
    private String titleChart;
    private String tagFig;
    private boolean scaleData;
    public ArrayList<Integer> workload = new ArrayList<Integer>();

    public LineChartTraffic(ArrayList<Integer> work, String labelX, String labelY, String titleC, String tagF, boolean flag){
            this.workload = work;
            this.titleChart = titleC;
            this.labelCX = labelX;
            this.labelCY = labelY;
            this.tagFig = tagF;
            this.scaleData = flag;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(this.titleChart);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = this.scaleData ? new NumberAxis(0, 5, 1) : new NumberAxis();
        xAxis.setLabel(this.labelCX);
        yAxis.setLabel(this.labelCY);

        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis){
            @Override
            public String toString(Number object){
                return String.format("%7.2f", object);
            }
        });
        final javafx.scene.chart.LineChart<String, Number> lineChart = new javafx.scene.chart.LineChart<String, Number> (xAxis, yAxis);

        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);

        XYChart.Series series1 = new XYChart.Series();

        series1.setName(this.tagFig);
        for (int i = 0; i<workload.size(); i++){
            series1.getData().add(new XYChart.Data("" + ((i+1)*5), workload.get(i)));
        }

        BorderPane pane = new BorderPane();
        pane.setCenter(lineChart);
        Scene scene = new Scene(pane, 800, 600);
        lineChart.getData().addAll(series1);

        stage.setScene(scene);

        stage.show();
    }
}
