package gg.acai.aurora;

import gg.acai.acava.commons.Attributes;
import gg.acai.acava.io.Closeable;
import gg.acai.aurora.model.Evaluation;
import gg.acai.aurora.model.ModelConvertible;
import gg.acai.aurora.model.Trainable;
import gg.acai.aurora.optimizers.Optimizer;
import gg.acai.aurora.sets.TestSet;

/**
 * @author Clouke
 * @since 30.04.2023 17:40
 * © Aurora - All Rights Reserved
 */
public interface NeuralNetwork extends Trainable, ModelConvertible<NeuralNetworkModel>, Closeable {

  /**
   * Get the optimizer of this neural network.
   *
   * @return Returns the optimizer of this neural network.
   */
  Optimizer optimizer();

  /**
   * Get the attributes of this neural network.
   * <p>Contains keys and values of the following:
   *  <ul>
   *    <li>epoch</li>
   *    <li>loss</li>
   *    <li>accuracy</li>
   *    <li>time</li>
   *    <li>stage</li>
   *  </ul>
   * </p>
   *
   * <p>The attributes can be used to get information about the training process.
   *
   * @return Returns a copy of the attributes in this neural network.
   */
  Attributes attributes();

  /**
   * Trains this neural network with the given inputs and outputs.
   *
   * @throws IllegalStateException if no dataset was set.
   */
  @Override
  void train() throws IllegalStateException;

  /**
   * Trains this neural network with the given inputs and outputs.
   *
   * @param inputs The inputs to train with.
   * @param outputs The outputs to train with.
   */
  @Override
  void train(double[][] inputs, double[][] outputs);

  /**
   * Evaluates this neural network with the given test set.
   *
   * @param set The test set to evaluate with.
   * @return Returns the evaluation of this neural network.
   */
  Evaluation evaluate(TestSet set);

  /**
   * Transforms this neural network into a storable model with the given name.
   *
   * @param name The name of the model.
   * @return Returns a storable model of this neural network.
   */
  @Override
  NeuralNetworkModel toModel(String name);

}
