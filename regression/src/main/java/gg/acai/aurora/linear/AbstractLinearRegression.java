package gg.acai.aurora.linear;

import gg.acai.aurora.model.MLContext;
import gg.acai.aurora.model.MLContextProvider;
import gg.acai.aurora.model.Predictable;

import java.util.function.DoubleUnaryOperator;

/**
 * @author Clouke
 * @since 03.05.2023 11:31
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractLinearRegression implements Predictable, MLContextProvider {

  protected double intercept;
  protected double slope;

  public AbstractLinearRegression(double intercept, double slope) {
    this.intercept = intercept;
    this.slope = slope;
  }

  public AbstractLinearRegression() {
    // empty constructor
  }

  @Override
  public double[] predict(double[] input) {
    int m = input.length;
    double[] output = new double[m];
    for (int i = 0; i < m; i++) {
      output[i] = intercept + (slope * input[i]);
    }
    return output;
  }

  public DoubleUnaryOperator predict() {
    return x -> intercept + (slope * x);
  }

  public double mean(double[] input) {
    double[] output = predict(input);
    double sum = 0.0;
    for (double value : output)
      sum += value;
    return sum / output.length;
  }

  public double intercept() {
    return intercept;
  }

  public double slope() {
    return slope;
  }

  @Override
  public MLContext context() {
    return MLContext.LINEAR_REGRESSION;
  }
}
