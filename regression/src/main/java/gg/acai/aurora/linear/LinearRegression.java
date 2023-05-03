package gg.acai.aurora.linear;

import gg.acai.acava.commons.Attributes;
import gg.acai.acava.commons.AttributesMapper;
import gg.acai.aurora.model.ModelConvertible;
import gg.acai.aurora.model.Trainable;
import gg.acai.aurora.publics.io.ComplexProgressTicker;

/**
 * @author Clouke
 * @since 03.05.2023 00:31
 * © Aurora - All Rights Reserved
 */

public class LinearRegression extends AbstractLinearRegression implements Trainable, ModelConvertible<LinearRegressionModel> {

  private final ComplexProgressTicker ticker;
  private final Attributes attributes;
  private final double learningRate;
  private final int epochs;
  private double accuracy;

  public LinearRegression(LinearRegressionBuilder builder) {
    LinearRegressionModel model = builder.model;
    if (model != null) {
      super.intercept = model.intercept();
      super.slope = model.slope();
    }
    this.learningRate = builder.learningRate;
    this.epochs = builder.epochs;
    this.ticker = new ComplexProgressTicker(builder.progressBar, 1);
    this.attributes = new AttributesMapper();
  }

  public void fit(double[] x, double[] y) {
    int m = x.length;
    for (int epoch = 0; epoch <= epochs; epoch++) {
      double[] predictions = predict(x);
      double error = cost(predictions, y);
      double gradient0 = (1.0 / m) * sum(predictions, y);
      double gradient1 = (1.0 / m) * sum(x, predictions, y);
      intercept = intercept - (learningRate * gradient0);
      slope = slope - (learningRate * gradient1);
      accuracy = 1.0 - error;

      int pct = (int) Math.round(epoch / (double) epochs * 100);
      attributes.set("epoch", epoch)
        .set("accuracy", accuracy)
        .set("loss", error)
        .set("stage", pct);

      ticker.tick(pct, attributes);
    }
  }

  // trainable support - converts 2d arrays to 1d arrays and fits the model
  @Override
  public void train(double[][] inputs, double[][] outputs) {
    double[] x = new double[inputs.length];
    double[] y = new double[outputs.length];
    for (int i = 0; i < inputs.length; i++) {
      x[i] = inputs[i][0];
      y[i] = outputs[i][0];
    }
    fit(x, y);
  }

  private double sum(double[] x, double[] predictions, double[] y) {
    int m = x.length;
    double sum = 0;
    for (int i = 0; i < m; i++) {
      sum += (predictions[i] - y[i]) * x[i];
    }
    return sum;
  }

  private double cost(double[] predictions, double[] actual) {
    int m = actual.length;
    double sum = 0;
    for (int i = 0; i < m; i++) {
      sum += Math.pow(predictions[i] - actual[i], 2);
    }
    return (1.0 / (2.0 * m)) * sum;
  }

  private double sum(double[] a, double[] b) {
    int m = a.length;
    double sum = 0;
    for (int i = 0; i < m; i++) {
      sum += (a[i] - b[i]);
    }
    return sum;
  }

  @Override
  public double accuracy() {
    return accuracy;
  }

  @Override
  public LinearRegressionModel toModel(String name) {
    LinearRegressionModel model = new LinearRegressionModel(intercept, slope);
    model.name(name);
    return model;
  }
}

