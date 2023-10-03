package gg.acai.aurora.model;

import gg.acai.aurora.sets.TestSet;

import java.util.Map;

/**
 * <p>A common model evaluation metric provider, providing the following metrics:
 * <ul>
 *   <li>Accuracy</li>
 *   <li>Precision</li>
 *   <li>Recall</li>
 *   <li>F1 Score</li>
 * </ul>
 *
 * Call {@link #evaluate()} to calculate the metrics.
 *
 * <p>Example Usage:
 * <pre>{@code
 *  ModelMetrics metrics = new ModelMetrics(model, testSet);
 *  metrics.evaluate();
 *  System.out.println(metrics.summary());
 * }</pre>
 *
 * @author Clouke
 * @since 27.04.2023 21:05
 * © Aurora - All Rights Reserved
 */
public class ModelMetrics implements Evaluation {

  private final Predictable model;
  private final TestSet testSet;
  private final double threshold;
  private double accuracy;
  private double precision;
  private double recall;
  private double f1Score;

  public ModelMetrics(Predictable model, TestSet testSet, double threshold) {
    this.model = model;
    this.testSet = testSet;
    this.threshold = threshold;
  }

  public ModelMetrics(Predictable model, TestSet testSet) {
    this(
      model,
      testSet,
      0.5
    );
  }

  @Override
  public void evaluate() {
    int truePos = 0;
    int falsePos = 0;
    int trueNeg = 0;
    int falseNeg = 0;

    for (Map.Entry<double[], double[]> entry : testSet.asMap().entrySet()) {
      double[] input = entry.getKey();
      double[] output = model.predict(input);
      double[] target = entry.getValue();

      for (int j = 0; j < output.length; j++) {
        if (output[j] >= threshold && target[j] == 1.0) {
          truePos++;
        } else if (output[j] >= threshold && target[j] == 0.0) {
          falsePos++;
        } else if (output[j] < threshold && target[j] == 0.0) {
          trueNeg++;
        } else if (output[j] < threshold && target[j] == 1.0) {
          falseNeg++;
        }
      }
    }

    // Calculate metrics
    accuracy = (double) (truePos + trueNeg) / (truePos + trueNeg + falsePos + falseNeg);
    precision = (double) truePos / (truePos + falsePos);
    recall = (double) truePos / (truePos + falseNeg);
    f1Score = 2.0 * precision * recall / (precision + recall);
  }

  @Override
  public double accuracy() {
    return accuracy;
  }

  @Override
  public double precision() {
    return precision;
  }

  @Override
  public double recall() {
    return recall;
  }

  @Override
  public double f1Score() {
    return f1Score;
  }

  @Override
  public String summary() {
    StringBuilder sb = new StringBuilder()
      .append("Evaluation Summary of ");
    String type = model.getClass().getSimpleName();
    if (model instanceof MLContextProvider) {
      MLContextProvider provider = (MLContextProvider) model;
      type = provider.context().toString();
    }
    sb.append("Type: ").append(type);
    if (model instanceof Model) {
      sb.append(", Model: ")
        .append(((Model) model)
          .nameOpt()
        .orElse("N/A"));
    }
    sb.append("\n");
    sb.append(" Accuracy: ").append(accuracy).append("\n");
    sb.append(" Precision: ").append(precision).append("\n");
    sb.append(" Recall: ").append(recall).append("\n");
    sb.append(" F1 Score: ").append(f1Score).append("\n");
    return sb.toString();
  }

  @Override
  public String toString() {
    return "ModelMetrics{" +
      "accuracy=" + accuracy +
      ", precision=" + precision +
      ", recall=" + recall +
      ", f1Score=" + f1Score +
      '}';
  }
}

