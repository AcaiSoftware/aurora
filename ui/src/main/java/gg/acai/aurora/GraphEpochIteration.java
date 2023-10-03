package gg.acai.aurora;

import com.google.common.annotations.Beta;
import com.google.common.collect.Maps;
import com.sun.javafx.application.PlatformImpl;
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
@Beta
public class GraphEpochIteration<T extends Attributed> extends Application implements EpochAction<T> {

  private final ObservableList<XYChart.Data<Number, Number>> lossData;
  private final ObservableList<XYChart.Data<Number, Number>> accuracyData;
  private final ObservableList<XYChart.Data<Number, Number>> deltaLossData = FXCollections.observableArrayList();
  private final ObservableList<XYChart.Data<Number, Number>> deltaAccuracyData = FXCollections.observableArrayList();
  private double lastLoss = 0.0;
  private double lastAccuracy = 0.0;

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
    PlatformImpl.startup(() -> {
      start(new Stage());
    });
    //Platform.runLater(() -> start(new Stage()));
  }

  public GraphEpochIteration() {
    this(
      FXCollections.observableArrayList(),
      FXCollections.observableArrayList()
    );
  }

  @Override
  public void onEpochIteration(int epoch, T t) {
    if (epoch % 10 != 0) return;

    Attributes attributes = t.attributes();
    double loss = attributes.get("loss");
    double scaledLoss = loss * 1000;
    double accuracy = attributes.get("accuracy");
    Platform.runLater(() -> {
      lossData.add(new XYChart.Data<>(epoch, scaledLoss));
      accuracyData.add(new XYChart.Data<>(epoch, accuracy));
      deltaLossData.add(new XYChart.Data<>(epoch, (scaledLoss - lastLoss)));
      deltaAccuracyData.add(new XYChart.Data<>(epoch, (accuracy - lastAccuracy)));
      lastLoss = scaledLoss;
      lastAccuracy = accuracy;
    });

  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Training Metrics");

    LineChart<Number, Number> combinedChart = createCombinedChart();
    VBox vbox = new VBox(combinedChart);
    vbox.setStyle("-fx-background-color: " + apply("background-color") + ";");
    Scene scene = new Scene(vbox, 800, 600);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  LineChart<Number, Number> createCombinedChart() {
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Iteration");

    LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
    chart.setStyle("-fx-background-color: " + apply("background-color") + ";");
    chart.setTitle("Loss and Accuracy");

    XYChart.Series<Number, Number> lossSeries = new XYChart.Series<>(lossData);
    lossSeries.setName("Loss");
    chart.getData().add(lossSeries);
    //chart.lookup(".default-color0.chart-series-line").setStyle("-fx-stroke: " + apply("loss-line") + ";");

    XYChart.Series<Number, Number> accuracySeries = new XYChart.Series<>(accuracyData);
    accuracySeries.setName("Accuracy");
    chart.getData().add(accuracySeries);
    //chart.lookup(".default-color1.chart-series-line").setStyle("-fx-stroke: " + apply("accuracy-line") + ";");

    XYChart.Series<Number, Number> deltaLossSeries = new XYChart.Series<>(deltaLossData);
    deltaLossSeries.setName("Delta Loss");
    chart.getData().add(deltaLossSeries);
    //chart.lookup(".default-color2.chart-series-line").setStyle("-fx-stroke: " + apply("loss-line") + ";");

    XYChart.Series<Number, Number> deltaAccuracySeries = new XYChart.Series<>(deltaAccuracyData);
    deltaAccuracySeries.setName("Delta Accuracy");
    chart.getData().add(deltaAccuracySeries);
    //chart.lookup(".default-color3.chart-series-line").setStyle("-fx-stroke: " + apply("accuracy-line") + ";");

    chart.setCreateSymbols(false);

    return chart;
  }

}