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

  public NeuralNetworkBuilder name(String name) {
    this.name = name;
    return this;
  }

  public NeuralNetworkBuilder layers(Consumer<LayerMapper> mapper) {
    LayerMapper layerMapper = new LayerMapper();
    mapper.accept(layerMapper);
    inputLayerSize = layerMapper.inputLayerSize;
    outputLayerSize = layerMapper.outputLayerSize;
    hiddenLayerSize = layerMapper.hiddenLayerSize;
    return this;
  }

  public NeuralNetworkBuilder printing(Bar progressBar) {
    this.progressBar = progressBar;
    return this;
  }

  public NeuralNetworkBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  public NeuralNetworkBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  public NeuralNetworkBuilder accuracySupplier(AccuracySupplier accuracySupplier) {
    this.accuracySupplier = accuracySupplier;
    return this;
  }

  public NeuralNetworkBuilder fromTune(Tune tune) {
    this.learningRate = tune.learningRate();
    this.epochs = tune.epochs();
    this.hiddenLayerSize = tune.layers();
    return this;
  }

  public NeuralNetworkBuilder activationFunction(ActivationFunction activationFunction) {
    this.activationFunction = activationFunction;
    return this;
  }

  public NeuralNetworkBuilder optimizer(Optimizer optimizer) {
    this.optimizer = optimizer;
    return this;
  }

  public NeuralNetworkBuilder evaluationSet(TestSet set) {
    this.evaluationSet = set;
    return this;
  }

  public NeuralNetworkBuilder disableStatsPrint() {
    this.shouldPrintStats = false;
    return this;
  }

  public NeuralNetworkBuilder autoSave() {
    this.autoSave = true;
    return this;
  }

  public NeuralNetworkBuilder earlyStops(EarlyStop... earlyStops) {
    this.earlyStoppers.add(earlyStops);
    return this;
  }

  @SafeVarargs
  public final NeuralNetworkBuilder epochActions(EpochAction<NeuralNetwork>... epochActions) {
    this.epochActions.addAll(Arrays.asList(epochActions));
    return this;
  }

  public NeuralNetworkBuilder withDataSet(DataSet set) {
    this.set = set;
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
