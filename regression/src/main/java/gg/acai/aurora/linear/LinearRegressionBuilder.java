package gg.acai.aurora.linear;

import com.google.common.base.Preconditions;
import gg.acai.aurora.publics.io.Bar;

/**
 * @author Clouke
 * @since 03.05.2023 11:45
 * © Aurora - All Rights Reserved
 */
public class LinearRegressionBuilder {

  protected double learningRate = -1.0;
  protected int epochs = -1;
  protected Bar progressBar = Bar.CLASSIC;
  protected LinearRegressionModel model;

  public LinearRegressionBuilder from(LinearRegressionModel model) {
    this.model = model;
    return this;
  }

  public LinearRegressionBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  public LinearRegressionBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  public LinearRegressionBuilder printing(Bar progressBar) {
    this.progressBar = progressBar;
    return this;
  }

  public LinearRegression build() {
    Preconditions.checkArgument(learningRate > 0.0, "Learning rate must be greater than 0.0");
    Preconditions.checkArgument(epochs > 0, "Epochs must be greater than 0");
    return new LinearRegression(this);
  }

}
