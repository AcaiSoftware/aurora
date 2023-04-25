package gg.acai.aurora.em;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A clusterer that uses the expectation maximization algorithm to cluster data.
 *
 * @author Clouke
 * @since 18.04.2023 03:45
 * © Aurora - All Rights Reserved
 */
public class ExpectationMaximizationCluster implements ExpectationMaximization {

  private final int k;
  private final double[][] data;
  private final double[][] means;
  private final double[][] variances;
  private final double[] weights;
  private final int maxIterations;
  private final double convergenceThreshold;
  private final Random random;

  public ExpectationMaximizationCluster(int k, double[][] data, int maxIterations, double convergenceThreshold) {
    this.k = k;
    this.data = data;
    this.maxIterations = maxIterations;
    this.convergenceThreshold = convergenceThreshold;
    this.random = ThreadLocalRandom.current();
    this.means = new double[k][data[0].length];
    this.variances = new double[k][data[0].length];
    this.weights = new double[k];
    initialize();
  }

  @Override
  public double[] predict(double[] input) {
    double[] probabilities = new double[k];
    for (int i = 0; i < k; i++) {
      probabilities[i] = weights[i] * gaussian(input, means[i], variances[i]);
    }
    return probabilities;
  }

  /**
   * Initialize the means, variances, and weights
   */
  private void initialize() {
    for (int i = 0; i < k; i++) {
      means[i] = data[random.nextInt(data.length)];
      variances[i] = new double[data[0].length];
      Arrays.fill(variances[i], 1.0);
      weights[i] = 1.0 / k;
    }
  }

  /**
   * Computes the Gaussian probability density function
   *
   * @param x The data point
   * @param mean The mean of the cluster
   * @param variance The variance of the cluster
   * @return Returns the gaussian probability density function
   */
  private double gaussian(double[] x, double[] mean, double[] variance) {
    double p = 1.0;
    for (int i = 0; i < x.length; i++) {
      p *= 1.0 / Math.sqrt(2.0 * Math.PI * variance[i])
        * Math.exp(-0.5 * Math.pow((x[i] - mean[i])
        / Math.sqrt(variance[i]), 2));
    }
    return p;
  }

  /**
   * Computes the probability of each data point belonging to each cluster
   *
   * @return Returns the probabilities of each data point belonging to each cluster
   */
  private double[][] computeProbabilities() {
    double[][] probabilities = new double[data.length][k];
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < k; j++) {
        probabilities[i][j] = weights[j] * gaussian(data[i], means[j], variances[j]);
      }
      double sum = Arrays.stream(probabilities[i]).sum();
      for (int j = 0; j < k; j++) {
        probabilities[i][j] /= sum;
      }
    }
    return probabilities;
  }

  /**
   * Update the means, variances, and weights based on the probabilities
   *
   * @param probabilities The probabilities of each data point belonging to each cluster
   */
  private void update(double[][] probabilities) {
    for (int i = 0; i < k; i++) {
      double weightSum = 0.0;
      double[] mean = new double[data[0].length];
      double[] variance = new double[data[0].length];
      for (int j = 0; j < data.length; j++) {
        weightSum += probabilities[j][i];
        for (int d = 0; d < data[0].length; d++) {
          mean[d] += probabilities[j][i] * data[j][d];
        }
      }
      weights[i] = weightSum / data.length;
      for (int d = 0; d < data[0].length; d++) {
        mean[d] /= weightSum;
      }
      for (int j = 0; j < data.length; j++) {
        for (int d = 0; d < data[0].length; d++) {
          variance[d] += probabilities[j][i] * Math.pow(data[j][d] - mean[d], 2);
        }
      }
      for (int d = 0; d < data[0].length; d++) {
        variance[d] /= weightSum;
        means[i][d] = mean[d];
        variances[i][d] = variance[d];
      }
    }
  }

  /**
   * Convert the clusterer to a model
   *
   * @return Returns the model of this clusterer
   */
  @Nonnull
  public ExpectationMaximizationClusterModel toModel() {
    return new ExpectationMaximizationClusterModel(cluster());
  }

  /**
   * Clusters the data points using the expectation maximization algorithm in this clusterer
   *
   * @return Returns the clusters of the data points
   */
  @Override
  public List<Integer> cluster() {
    double oldLogLikelihood = Double.NEGATIVE_INFINITY;
    for (int iteration = 0; iteration < maxIterations; iteration++) {
      double[][] probabilities = computeProbabilities();
      update(probabilities);
      double logLikelihood = 0.0;
      for (double[] datum : data) {
        double p = 0.0;
        for (int j = 0; j < k; j++) {
          p += weights[j] * gaussian(datum, means[j], variances[j]);
        }
        logLikelihood += Math.log(p);
      }
      double delta = Math.abs(logLikelihood - oldLogLikelihood);
      if (delta < convergenceThreshold) {
        break;
      }
      oldLogLikelihood = logLikelihood;
    }
    List<Integer> clusters = new ArrayList<>();
    for (double[] datum : data) {
      double maxProbability = Double.NEGATIVE_INFINITY;
      int cluster = 0;
      for (int j = 0; j < k; j++) {
        double probability = weights[j] * gaussian(datum, means[j], variances[j]);
        if (probability > maxProbability) {
          maxProbability = probability;
          cluster = j;
        }
      }
      clusters.add(cluster);
    }
    return clusters;
  }
}
