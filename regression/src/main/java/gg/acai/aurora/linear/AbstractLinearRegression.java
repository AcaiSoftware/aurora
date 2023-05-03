package gg.acai.aurora.linear;

import gg.acai.aurora.model.MLContext;
import gg.acai.aurora.model.MLContextProvider;
import gg.acai.aurora.model.Predictable;

import java.util.function.DoubleUnaryOperator;

/**
 * An abstract implementation of a linear regression
 *
 * @author Clouke
 * @since 03.05.2023 11:31
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractLinearRegression implements Predictable, MLContextProvider {

  protected double intercept;
  protected double slope;

  /**
   * Constructs a linear regression with the given intercept and slope
   *
   * @param intercept The intercept
   * @param slope The slope
   */
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

  /**
   * Predicts the output of the given input
   *
   * @return Returns a {@link DoubleUnaryOperator} that predicts the output of the given input
   */
  public DoubleUnaryOperator predict() {
    return x -> intercept + (slope * x);
  }

  /**
   * Predicts the mean of the given input
   *
   * @param input The input to predict the mean of
   * @return Returns the mean of the given input
   */
  public double mean(double[] input) {
    double[] output = predict(input);
    double sum = 0.0;
    for (double value : output)
      sum += value;
    return sum / output.length;
  }

  /**
   * Gets the intercept of this linear regression
   *
   * @return Returns the intercept of this linear regression
   */
  public double intercept() {
    return intercept;
  }

  /**
   * Gets the slope of this linear regression
   *
   * @return Returns the slope of this linear regression
   */
  public double slope() {
    return slope;
  }

  @Override
  public MLContext context() {
    return MLContext.LINEAR_REGRESSION;
  }
}
