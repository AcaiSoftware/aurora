package gg.acai.aurora.hyperparameter.evaluator;

import gg.acai.aurora.hyperparameter.EvaluatorScoreContext;
import gg.acai.aurora.hyperparameter.TuningEvaluator;
import gg.acai.aurora.model.Trainable;

/**
 * A cross validator is a tuning evaluator that uses k-fold cross validation to evaluate the accuracy of a trainable.
 *
 * @author Clouke
 * @since 18.04.2023 18:41
 * © Aurora - All Rights Reserved
 */
public class CrossValidator implements TuningEvaluator {

  private final int folds;
  private final TuningEvaluator delegate;

  public CrossValidator(int folds, TuningEvaluator delegate) {
    if (delegate instanceof CrossValidator)
      throw new IllegalArgumentException("CrossValidator cannot be used as a TuningEvaluator for CrossValidator");
    this.folds = folds;
    this.delegate = delegate;
  }

  @Override
  public double evaluate(Trainable trainable, double[][] inputs, double[][] outputs) {
    double[] scores = new double[folds];
    int foldSize = inputs.length / folds;
    for (int i = 0; i < folds; i++) {
      double[][] foldInputs = new double[foldSize][inputs[0].length];
      double[][] foldOutputs = new double[foldSize][outputs[0].length];
      System.arraycopy(inputs, i * foldSize, foldInputs, 0, foldSize);
      System.arraycopy(outputs, i * foldSize, foldOutputs, 0, foldSize);
      double[][] trainingInputs = new double[inputs.length - foldSize][inputs[0].length];
      double[][] trainingOutputs = new double[outputs.length - foldSize][outputs[0].length];
      System.arraycopy(inputs, 0, trainingInputs, 0, i * foldSize);
      System.arraycopy(outputs, 0, trainingOutputs, 0, i * foldSize);
      System.arraycopy(inputs, (i + 1) * foldSize, trainingInputs, i * foldSize, inputs.length - (i + 1) * foldSize);
      System.arraycopy(outputs, (i + 1) * foldSize, trainingOutputs, i * foldSize, outputs.length - (i + 1) * foldSize);
      trainable.train(trainingInputs, trainingOutputs);
      scores[i] = delegate.evaluate(trainable, foldInputs, foldOutputs);
    }
    double sum = 0.0;
    for (double score : scores) {
      sum += score;
    }
    return sum / scores.length;
  }

  @Override
  public EvaluatorScoreContext context() {
    return delegate.context();
  }
}
