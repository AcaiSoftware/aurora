package gg.acai.aurora.core.ml.nn;

import gg.acai.acava.commons.graph.Graph;

import javax.annotation.Nullable;

/**
 * @author Clouke
 * @since 03.03.2023 15:01
 * © Aurora - All Rights Reserved
 */
public class TrainingStats {

  private static final String ANSI_BOLD = "\033[1m";
  private static final String RESET = "\033[0m";

  private final int epochs;
  private final double learningRate;
  private final double accuracy;
  private final long time;
  private final double accurateEstimate;
  private final Graph<Double> trainingGraph;

  public TrainingStats(int epochs, double learningRate, double accuracy, long time, double accurateEstimate, Graph<Double> trainingGraph) {
    this.epochs = epochs;
    this.learningRate = learningRate;
    this.accuracy = accuracy;
    this.time = time;
    this.accurateEstimate = accurateEstimate;
    this.trainingGraph = trainingGraph;
  }

  public int epochs() {
    return epochs;
  }

  public double learningRate() {
    return learningRate;
  }

  public double accuracy() {
    return accuracy;
  }

  public double time() {
    return time;
  }

  public double accurateEstimate() {
    return accurateEstimate;
  }

  @Nullable
  public Graph<Double> graph() {
    return trainingGraph;
  }

  @Override
  public String toString() {
    String acc = accuracy == -1 ? "Missing Accuracy Test" : accuracy + "%";
    double seconds = time / 1000.0D;
    int estimationDiff = (int) Math.abs(seconds - accurateEstimate);
    String s = ANSI_BOLD + "Training Stats:" + "\n" + RESET +
            "• Accuracy: " + acc + "\n" +
            "• Time: " + seconds + "s" + "\n" +
            "• Accurate Estimate Time: " + accurateEstimate + "s" + "\n" +
            "• Estimation Time Difference: " + estimationDiff + "s";

    if (accuracy != -1 && accuracy < 80)
      s += "\n" + ANSI_BOLD + "WARNING: Accuracy is below 80%, this may be due to a bad learning rate or a bad network architecture. (Try re-training the network)" + RESET;

    return s;
  }

  public void print() {
    System.out.println(this);
  }

  public void printGraph() {
    if (trainingGraph != null) {
      System.out.println(trainingGraph.getVisualizer());
      return;
    }

    System.out.println("Cannot print graph: No graph was provided.");
  }

}
