package gg.acai.aurora.cluster.hierarchy;

import gg.acai.aurora.Evaluator;

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
    int n = data.length;
    double[] silhouettes = new double[n];
    for (int i = 0; i < n; i++) {
      double a = 0, b = Double.MAX_VALUE;
      int cluster = labels[i];
      HierarchyClusterFamily family = classifier.cluster(data[i]).clone();
      for (int j = 0; j < n; j++) {
        if (labels[j] == cluster) {
          double dist = Math.abs(data[i] - data[j]);
          if (i != j) {
            a += dist;
          }
        } else {
          classifier.cluster(data[j]);
          double dist = Math.abs(data[i] - data[j]);
          if (dist < b) {
            b = dist;
          }
        }
      }
      a /= Math.max(1, family.size() - 1);
      silhouettes[i] = (b - a) / Math.max(a, b);
    }
    double sum = 0;
    for (double s : silhouettes) {
      sum += s;
    }
    return sum / n;
  }

}
