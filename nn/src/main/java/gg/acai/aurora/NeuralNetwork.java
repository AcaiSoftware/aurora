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
 * A neural network that can be trained and evaluated, and can be converted into a storable model.
 * <p> Example Usage:
 * <p><b>Building a Neural Network</b>
 * <pre>{@code
 *  NeuralNetworkTrainer trainer = new NeuralNetworkBuilder()
 *    .name("my_model")
 *    .learningRate(0.1)
 *    .epochs(1_000_000)
 *    .optimizer(new StochasticGradientDescent())
 *    .earlyStops(new Stagnation(20)) // in case stagnation happens, we will stop training to prevent unnecessary training
 *    .printing(Bar.CLASSIC)
 *    .activationFunction(ActivationFunction.SIGMOID)
 *    .epochActions(new EpochAutoSave(10_000, "C:\\Users\\my_user\\models")) // every 10K epochs
 *    .layers(mapper -> mapper
 *      .inputLayers(3) // add your input layer size here
 *      .hiddenLayers(3) // add your hidden layer size here
 *      .outputLayers(1)) // add your output layer size here
 *    .build();
 *
 *  trainer.train(inputs, outputs); // train the neural network
 *}</pre>
 *
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
   *
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
   * @throws IllegalStateException if no dataset was applied.
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
