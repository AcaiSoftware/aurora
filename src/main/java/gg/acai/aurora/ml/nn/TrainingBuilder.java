package gg.acai.aurora.ml.nn;

import gg.acai.acava.Requisites;
import gg.acai.acava.commons.graph.Graph;
import gg.acai.aurora.ml.ActivationFunction;
import gg.acai.aurora.ml.nn.extension.ModelTrainEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Clouke
 * @since 02.03.2023 13:13
 * © Aurora - All Rights Reserved
 */
public class TrainingBuilder { // TODO: Rename to factory

  protected boolean shouldPrintTrainingProgress = true;
  protected boolean shouldPrintStats = true;
  protected boolean autoSave = false;

  protected int epochs = -1;
  protected double learningRate = -1.0;


  protected int inputLayerSize;
  protected int outputLayerSize;
  protected int hiddenLayerSize;
  protected ExecutorService thread;
  protected ModelTrainEvent listener;
  protected double[] accuracyTest;
  protected int maxCycleBuffer = 30;
  protected Graph<Double> graph;
  protected ActivationFunction activationFunction = ActivationFunction.SIGMOID;

  public TrainingBuilder inputLayerSize(int inputLayerSize) {
    this.inputLayerSize = inputLayerSize;
    return this;
  }

  public TrainingBuilder outputLayerSize(int outputLayerSize) {
    this.outputLayerSize = outputLayerSize;
    return this;
  }

  public TrainingBuilder hiddenLayerSize(int hiddenLayerSize) {
    this.hiddenLayerSize = hiddenLayerSize;
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

  public TrainingBuilder thread(ExecutorService thread) {
    this.thread = thread;
    return this;
  }

  public TrainingBuilder accuracyTest(double[] accuracyTest) {
    this.accuracyTest = accuracyTest;
    return this;
  }

  public TrainingBuilder useGraph(int maxDisplayValue, int maxSize) {
    this.graph = Graph.newBuilder()
      .setMaxDisplayValue(maxDisplayValue)
      .setFixedSize(maxSize)
      .build();
    return this;
  }

  public TrainingBuilder useGraph() {
    return useGraph(10, 120);
  }

  public TrainingBuilder async() {
    return thread(Executors.newSingleThreadExecutor());
  }

  public TrainingBuilder listener(ModelTrainEvent listener) {
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

}
