package gg.acai.aurora;

import gg.acai.acava.Requisites;
import gg.acai.acava.commons.graph.Graph;
import gg.acai.aurora.earlystop.EarlyStop;
import gg.acai.aurora.earlystop.EarlyStoppers;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.extension.ModelTrainListener;
import gg.acai.aurora.optimizers.Optimizer;
import gg.acai.aurora.optimizers.StochasticGradientDescent;
import gg.acai.aurora.sets.DataSet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author Clouke
 * @since 02.03.2023 13:13
 * © Aurora - All Rights Reserved
 */
public class TrainingBuilder {

  protected boolean shouldPrintTrainingProgress = true;
  protected boolean shouldPrintStats = true;
  protected boolean autoSave = false;

  protected int epochs = -1;
  protected double learningRate = -1.0;

  protected int inputLayerSize;
  protected int outputLayerSize;
  protected int hiddenLayerSize;
  protected ExecutorService thread;
  protected ModelTrainListener listener;
  protected double[] accuracyTest;
  protected int maxCycleBuffer = 30;
  protected Graph<Double> graph;
  protected ActivationFunction activationFunction = ActivationFunction.SIGMOID;
  protected DataSet set;
  protected EarlyStoppers earlyStoppers = new EarlyStoppers();
  protected Optimizer optimizer = new StochasticGradientDescent();

  public TrainingBuilder layers(Consumer<LayerOptions> options) {
    LayerOptions layerOptions = new LayerOptions();
    options.accept(layerOptions);
    inputLayerSize = layerOptions.inputLayerSize;
    outputLayerSize = layerOptions.outputLayerSize;
    hiddenLayerSize = layerOptions.hiddenLayerSize;
    return this;
  }

  public TrainingBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  public TrainingBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  public TrainingBuilder activationFunction(ActivationFunction activationFunction) {
    this.activationFunction = activationFunction;
    return this;
  }

  public TrainingBuilder maxCycleBuffer(int maxCycleBuffer) {
    this.maxCycleBuffer = maxCycleBuffer;
    return this;
  }

  public TrainingBuilder optimizer(Optimizer optimizer) {
    this.optimizer = optimizer;
    return this;
  }

  public TrainingBuilder disableCycleBuffer() {
    this.maxCycleBuffer = -1;
    return this;
  }

  public TrainingBuilder disableProgressPrint() {
    this.shouldPrintTrainingProgress = false;
    return this;
  }

  public TrainingBuilder disableStatsPrint() {
    this.shouldPrintStats = false;
    return this;
  }

  public TrainingBuilder autoSave() {
    this.autoSave = true;
    return this;
  }

  public TrainingBuilder earlyStops(EarlyStop... earlyStops) {
    this.earlyStoppers.add(earlyStops);
    return this;
  }

  public TrainingBuilder thread(ExecutorService thread) {
    this.thread = thread;
    return this;
  }

  public TrainingBuilder accuracyTest(double[] accuracyTest) {
    this.accuracyTest = accuracyTest;
    return this;
  }

  public TrainingBuilder withDataSet(DataSet set) {
    this.set = set;
    return this;
  }

  public TrainingBuilder async() {
    return thread(Executors.newSingleThreadExecutor());
  }

  public TrainingBuilder listener(ModelTrainListener listener) {
    this.listener = listener;
    return this;
  }

  public NeuralNetworkTrainer build() {
    Requisites.checkArgument(inputLayerSize > 0, "Input layer size must be greater than 0");
    Requisites.checkArgument(outputLayerSize > 0, "Output layer size must be greater than 0");
    Requisites.checkArgument(hiddenLayerSize > 0, "Hidden layer size must be greater than 0");
    Requisites.checkArgument(epochs > 0, "Epochs must be greater than 0");
    Requisites.checkArgument(learningRate > 0.0, "Learning rate must be greater than 0.0");
    return new NeuralNetworkTrainer(this);
  }

  public static class LayerOptions {

    private int inputLayerSize;
    private int hiddenLayerSize;
    private int outputLayerSize;

    public LayerOptions inputLayers(int inputLayerSize) {
      this.inputLayerSize = inputLayerSize;
      return this;
    }

    public LayerOptions hiddenLayers(int hiddenLayerSize) {
      this.hiddenLayerSize = hiddenLayerSize;
      return this;
    }

    public LayerOptions outputLayers(int outputLayerSize) {
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
