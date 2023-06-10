package gg.acai.aurora;

import gg.acai.acava.commons.Attributes;
import gg.acai.acava.commons.AttributesMapper;
import gg.acai.aurora.model.EpochAction;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Clouke
 * @since 10.06.2023 02:38
 * © Aurora - All Rights Reserved
 */
public class GraphEpochIteration<T extends Attributed> extends Application implements EpochAction<T> {

  private final ObservableList<XYChart.Data<Number, Number>> lossData;
  private final ObservableList<XYChart.Data<Number, Number>> accuracyData;

  public GraphEpochIteration(ObservableList<XYChart.Data<Number, Number>> lossData, ObservableList<XYChart.Data<Number, Number>> accuracyData) {
    this.lossData = lossData;
    this.accuracyData = accuracyData;
    Platform.runLater(() -> start(new Stage()));
  }

  public GraphEpochIteration() {
    this(
      FXCollections.observableArrayList(),
      FXCollections.observableArrayList()
    );
  }

  @Override
  public void onEpochIteration(int epoch, T t) {
    Attributes attributes = t.attributes();
    lossData.add(new XYChart.Data<>(epoch, attributes.get("loss")));
    accuracyData.add(new XYChart.Data<>(epoch, attributes.get("accuracy")));
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Training Metrics");

    LineChart<Number, Number> lossChart = createChart("Loss", "Loss", lossData);
    LineChart<Number, Number> accuracyChart = createChart("Accuracy", "Accuracy", accuracyData);

    VBox vbox = new VBox(lossChart, accuracyChart);
    Scene scene = new Scene(vbox, 800, 600);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private LineChart<Number, Number> createChart(String title, String yAxisLabel, ObservableList<XYChart.Data<Number, Number>> data) {
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Iteration");
    yAxis.setLabel(yAxisLabel);

    LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
    chart.setTitle(title);
    chart.getData().add(new XYChart.Series<>(data));

    return chart;
  }

  public static void main(String[] args) {
    GraphEpochIteration<Attributed> graphEpochIteration = new GraphEpochIteration<>();
    Attributes attributes = new AttributesMapper();
    for (int i = 0; i < 100; i++) {
      attributes.set("loss", Math.random());
      attributes.set("accuracy", Math.random());
      graphEpochIteration.onEpochIteration(i, () -> attributes);
      try {
        Thread.sleep(120);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
