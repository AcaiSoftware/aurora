package gg.acai.aurora;

import gg.acai.acava.commons.Attributes;
import gg.acai.acava.io.Closeable;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.model.Evaluation;
import gg.acai.aurora.model.ModelConvertible;
import gg.acai.aurora.model.Trainable;
import gg.acai.aurora.optimizers.Optimizer;
import gg.acai.aurora.sets.TestSet;

import java.util.Set;
import java.util.function.Consumer;

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
   * Gets a set of actions that are executed on each epoch iteration.
   *
   * @return Returns a set of actions that are executed on each epoch iteration.
   */
  Set<EpochAction<NeuralNetwork>> iterationActions();

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

  /**
   * Wraps this neural network into a {@link WrappedNeuralNetwork}.
   * Can be used to get the weights and biases of this neural network.
   *
   * @return Returns a wrapper of this neural network.
   */
  WrappedNeuralNetwork wrap();

  /**
   * A completion callback that accepts a function to be executed when this
   * neural network has completed training.
   * <p>
   * Sufficient for asynchronous training processes.
   *
   * @param function The consumer accepting this neural network.
   */
  void onCompletion(Consumer<NeuralNetwork> function);

  /**
   * Gets the activation function of this neural network.
   *
   * @return Returns the activation function of this neural network.
   */
  ActivationFunction activation();

}
