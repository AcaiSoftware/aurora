package gg.acai.aurora;

import com.google.common.collect.Maps;
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

import java.util.Map;

/**
 * @author Clouke
 * @since 10.06.2023 02:38
 * © Aurora - All Rights Reserved
 */
public class GraphEpochIteration<T extends Attributed> extends Application implements EpochAction<T> {

  private final ObservableList<XYChart.Data<Number, Number>> lossData;
  private final ObservableList<XYChart.Data<Number, Number>> accuracyData;

  private static final Map<String, String> DESIGN;

  static {
    DESIGN = Maps.newHashMap();
    DESIGN.put("background-color", "#1b1b1f");
    DESIGN.put("horizontal-grid-lines", "#ffffff");
    DESIGN.put("vertical-grid-lines", "#ffffff");
    DESIGN.put("series-line", "#0000ff");
    DESIGN.put("loss-line", "#a33eb8");
    DESIGN.put("accuracy-line", "#3eb895");
  }

  static String apply(String target) {
    return DESIGN.getOrDefault(target, "#ffffff");
  }

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
    vbox.setStyle("-fx-background-color: " + apply("background-color") + ";");
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
    chart.setStyle("-fx-background-color: " + apply("background-color") + ";");
    chart.setTitle(title);

    XYChart.Series<Number, Number> series = new XYChart.Series<>(data);
    chart.getData().add(series);
    chart.lookup(".default-color0.chart-series-line").setStyle("-fx-stroke: " + apply(title.toLowerCase() + "-line") + ";");
    chart.setCreateSymbols(false);

    /*
    for (XYChart.Data<Number, Number> point : series.getData()) {
      Node node = point.getNode();
      node.setStyle("-fx-background-color: gray;");
    }

    // Add a listener to handle new data points
    data.addListener((ListChangeListener<XYChart.Data<Number, Number>>) change -> {
      while (change.next()) {
        if (change.wasAdded()) {
          Platform.runLater(() -> {
            for (XYChart.Data<Number, Number> point : change.getAddedSubList()) {
              Node node = point.getNode();
              node.setStyle("-fx-background-color: gray;");
            }
          });
        }
      }
    });
     */

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