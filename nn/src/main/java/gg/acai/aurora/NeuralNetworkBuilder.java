package gg.acai.aurora;

import gg.acai.acava.Requisites;
import gg.acai.aurora.earlystop.EarlyStop;
import gg.acai.aurora.earlystop.EarlyStoppers;
import gg.acai.aurora.hyperparameter.Tune;
import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.publics.io.Bar;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.optimizers.Optimizer;
import gg.acai.aurora.optimizers.StochasticGradientDescent;
import gg.acai.aurora.sets.DataSet;
import gg.acai.aurora.sets.TestSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Builder class for the {@link NeuralNetworkTrainer}
 * <p> Example Usage:
 * <p><b>Building a Neural Network</b>
 * <pre>{@code
 *  NeuralNetworkTrainer trainer = new NeuralNetworkBuilder()
 *    .name("my_model")
 *    .learningRate(0.1)
 *    .epochs(1_000_000)
 *    .optimizer(new StochasticGradientDescent()) // most common optimizer
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
 * @author Clouke
 * @since 02.03.2023 13:13
 * © Aurora - All Rights Reserved
 */
public class NeuralNetworkBuilder {

  protected boolean shouldPrintStats = true;
  protected boolean autoSave = false;

  protected int epochs = -1;
  protected double learningRate = -1.0;

  protected String name;
  protected int inputLayerSize;
  protected int outputLayerSize;
  protected int hiddenLayerSize;
  protected ActivationFunction activationFunction = ActivationFunction.SIGMOID;
  protected DataSet set;
  protected EarlyStoppers earlyStoppers = new EarlyStoppers();
  protected Optimizer optimizer = new StochasticGradientDescent();
  protected Set<EpochAction<NeuralNetwork>> epochActions = new HashSet<>();
  protected Bar progressBar = Bar.CLASSIC;
  protected TestSet evaluationSet;
  protected AccuracySupplier accuracySupplier;
  protected NeuralNetworkModel model;
  protected long seed = System.currentTimeMillis();

  /**
   * Applies a model to this builder for re-training / improvement purposes
   *
   * @param model The model to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder from(NeuralNetworkModel model) {
    this.model = model;
    return this;
  }

  /**
   * Applies the randomization seed to this neural network model
   *
   * @param seed The seed to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder seed(long seed) {
    this.seed = seed;
    return this;
  }

  /**
   * Applies a name to this neural network model - for saving and loading purposes
   *
   * @param name The name to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Applies the layer sizes to this neural network model
   * <pre>
   * Example Usage:
   * {@code
   *  .layers(mapper -> mapper
   *    .inputLayers(3) // add your input layer size here
   *    .hiddenLayers(3) // add your hidden layer size here
   *    .outputLayers(1)) // add your output layer size here
   *  }
   * </pre>
   *
   * @param mapper The mapper to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder layers(Consumer<LayerMapper> mapper) {
    LayerMapper layerMapper = new LayerMapper();
    mapper.accept(layerMapper);
    inputLayerSize = layerMapper.inputLayerSize;
    outputLayerSize = layerMapper.outputLayerSize;
    hiddenLayerSize = layerMapper.hiddenLayerSize;
    return this;
  }

  /**
   * Applies the progress bar to display the training progress
   *
   * @param progressBar The progress bar to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder printing(Bar progressBar) {
    this.progressBar = progressBar;
    return this;
  }

  /**
   * Applies the amount of epochs to train the neural network on
   *
   * @param epochs The amount of epochs to train on
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  /**
   * Applies the learning rate to train the neural network on
   *
   * @param learningRate The learning rate to train on
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  /**
   * Applies an accuracy supplier for testing accuracy with custom data
   *
   * @param accuracySupplier The accuracy supplier to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder accuracySupplier(AccuracySupplier accuracySupplier) {
    this.accuracySupplier = accuracySupplier;
    return this;
  }

  /**
   * Applies hyperparameters from a tune
   *
   * @param tune The tune to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder fromTune(Tune tune) {
    this.learningRate = tune.learningRate();
    this.epochs = tune.epochs();
    this.hiddenLayerSize = tune.layers();
    return this;
  }

  /**
   * Applies the activation function to this neural network model
   *
   * @param activationFunction The activation function to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder activationFunction(ActivationFunction activationFunction) {
    this.activationFunction = activationFunction;
    return this;
  }

  /**
   * Applies the optimizer to this neural network model
   *
   * @param optimizer The optimizer to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder optimizer(Optimizer optimizer) {
    this.optimizer = optimizer;
    return this;
  }

  /**
   * Applies the evaluation set to this neural network model after train completion
   *
   * @param set The evaluation set to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder evaluationSet(TestSet set) {
    this.evaluationSet = set;
    return this;
  }

  /**
   * Disables the printing of stats to the console after training completion
   *
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder disableStatsPrint() {
    this.shouldPrintStats = false;
    return this;
  }

  /**
   * Enables auto saving of the neural network model after training completion
   *
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder autoSave() {
    this.autoSave = true;
    return this;
  }

  /**
   * Applies early stoppers to this neural network model
   *
   * @param earlyStops The early stoppers to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder earlyStops(EarlyStop... earlyStops) {
    this.earlyStoppers.add(earlyStops);
    return this;
  }

  /**
   * Applies epoch iterable action listeners to this neural network model
   *
   * @param epochActions The epoch actions to apply
   * @return This builder for chaining
   */
  @SafeVarargs
  public final NeuralNetworkBuilder epochActions(EpochAction<NeuralNetwork>... epochActions) {
    this.epochActions.addAll(Arrays.asList(epochActions));
    return this;
  }

  /**
   * Applies a data set to this neural network model to train on
   *
   * @param set The data set to apply
   * @return This builder for chaining
   */
  public NeuralNetworkBuilder withDataSet(DataSet set) {
    this.set = set;
    return this;
  }

  /**
   * Builds the neural network trainer
   *
   * @return The neural network trainer
   * @throws IllegalArgumentException If the input layer size, output layer size, hidden layer size or epochs are less than 0
   */
  public NeuralNetworkTrainer build() {
    Requisites.checkArgument(inputLayerSize > 0, "Input layer size must be greater than 0");
    Requisites.checkArgument(outputLayerSize > 0, "Output layer size must be greater than 0");
    Requisites.checkArgument(hiddenLayerSize > 0, "Hidden layer size must be greater than 0");
    Requisites.checkArgument(epochs > 0, "Epochs must be greater than 0");
    Requisites.checkArgument(learningRate > 0.0, "Learning rate must be greater than 0.0");
    return new NeuralNetworkTrainer(this);
  }

  public static class LayerMapper {

    private int inputLayerSize;
    private int hiddenLayerSize;
    private int outputLayerSize;

    public LayerMapper inputLayers(int inputLayerSize) {
      this.inputLayerSize = inputLayerSize;
      return this;
    }

    public LayerMapper hiddenLayers(int hiddenLayerSize) {
      this.hiddenLayerSize = hiddenLayerSize;
      return this;
    }

    public LayerMapper outputLayers(int outputLayerSize) {
      this.outputLayerSize = outputLayerSize;
      return this;
    }

    int inputLayerSize() {
      return inputLayerSize;
    }

    int hiddenLayerSize() {
      return hiddenLayerSize;
    }

    int outputLayerSize() {
      return outputLayerSize;
    }

  }

}
