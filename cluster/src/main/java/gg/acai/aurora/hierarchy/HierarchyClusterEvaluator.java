package gg.acai.aurora.hierarchy;

import gg.acai.aurora.model.Evaluator;

/**
 * @author Clouke
 * @since 09.04.2023 20:14
 * © Aurora - All Rights Reserved
 */
public class HierarchyClusterEvaluator implements Evaluator {

  private final HierarchyClusterClassifier classifier;
  private final double[] data;
  private final int[] labels;

  public HierarchyClusterEvaluator(HierarchyClusterClassifier classifier, double[] data, int[] labels) {
    this.classifier = classifier;
    this.data = data;
    this.labels = labels;
  }

  @Override
  public double evaluate() {
    int correctCount = 0;
    int length = data.length;
    for (int i = 0; i < length; i++) {
      double[] prediction = classifier.predict(new double[]{data[i]});
      for (double datum : prediction) {
        if (datum == labels[i]) {
          correctCount++;
          break;
        }
      }
    }

    return (double) correctCount / length;
  }

}
