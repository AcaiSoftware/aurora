package gg.acai.aurora.linear;

import com.google.common.base.Preconditions;
import gg.acai.aurora.publics.io.Bar;

/**
 * A builder class for linear regression models.
 *
 * @author Clouke
 * @since 03.05.2023 11:45
 * © Aurora - All Rights Reserved
 */
public class LinearRegressionBuilder {

  protected double learningRate = -1.0;
  protected int epochs = -1;
  protected Bar progressBar = Bar.CLASSIC;
  protected LinearRegressionModel model;

  /**
   * Applies the given model to this builder for re-training or improving the model.
   *
   * @param model The model to apply
   * @return Returns this builder for chaining
   */
  public LinearRegressionBuilder from(LinearRegressionModel model) {
    this.model = model;
    return this;
  }

  /**
   * Applies the given learning rate to this builder.
   *
   * @param learningRate The learning rate to apply
   * @return Returns this builder for chaining
   */
  public LinearRegressionBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  /**
   * Applies the given epochs to this builder.
   *
   * @param epochs The epochs to apply
   * @return Returns this builder for chaining
   */
  public LinearRegressionBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  /**
   * Applies the given progress bar to this builder.
   *
   * @param progressBar The progress bar to apply
   * @return Returns this builder for chaining
   */
  public LinearRegressionBuilder printing(Bar progressBar) {
    this.progressBar = progressBar;
    return this;
  }

  /**
   * Builds the trainable linear regression model.
   *
   * @return Returns the built linear regression model
   */
  public LinearRegression build() {
    Preconditions.checkArgument(learningRate > 0.0, "Learning rate must be greater than 0.0");
    Preconditions.checkArgument(epochs > 0, "Epochs must be greater than 0");
    return new LinearRegression(this);
  }

}
