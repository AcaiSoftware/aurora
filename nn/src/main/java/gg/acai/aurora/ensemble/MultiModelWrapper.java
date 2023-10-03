package gg.acai.aurora.ensemble;

import com.google.common.base.Preconditions;
import gg.acai.acava.collect.pairs.ImmutablePair;
import gg.acai.acava.collect.pairs.Pair;
import gg.acai.acava.commons.Attributes;
import gg.acai.aurora.AbstractNeuralNetwork;
import gg.acai.aurora.NeuralNetwork;
import gg.acai.aurora.NeuralNetworkBuilder;
import gg.acai.aurora.NeuralNetworkModel;
import gg.acai.aurora.WrappedNeuralNetwork;
import gg.acai.aurora.batch.BatchIterator;
import gg.acai.aurora.initializers.Initializer;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.model.Evaluation;
import gg.acai.aurora.model.ModelLoader;
import gg.acai.aurora.noise.NoiseContext;
import gg.acai.aurora.optimizers.AdamBuilder;
import gg.acai.aurora.optimizers.Optimizer;
import gg.acai.aurora.optimizers.Optimizers;
import gg.acai.aurora.optimizers.StochasticGradientDescent;
import gg.acai.aurora.sets.TestSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @author Clouke
 * @since 10.09.2023 15:11
 * © Aurora - All Rights Reserved
 */
public class MultiModelWrapper extends AbstractMultiModelWrapper implements NeuralNetwork {

  public MultiModelWrapper(List<NeuralNetwork> networks, boolean partitioning, double partitionRate) {
    super(
      networks,
      partitioning,
      partitionRate
    );
  }

  public MultiModelWrapper(String name, List<NeuralNetwork> networks, boolean partitioning, double partitionRate, int outputSize, int trainCount) {
    super(
      name,
      networks,
      partitioning,
      partitionRate,
      outputSize,
      trainCount
    );
  }

  @Override
  public void train(double[][] inputs, double[][] outputs) {
    outputSize = outputs[0].length;
    if (partitioning) {
      List<Pair<double[][], double[][]>> splits = new ArrayList<>();
      networks.forEach(network -> {
        int split = (int) (inputs.length * partitionRate);
        double[][] trainInputs = new double[split][];
        double[][] trainOutputs = new double[split][];
        for (int i = 0; i < split; i++) {
          trainInputs[i] = inputs[i];
          trainOutputs[i] = outputs[i];
        }
        splits.add(new ImmutablePair<>(
          trainInputs,
          trainOutputs
        ));
      });

      for (int i = 0; i < networks.size(); i++) {
        NeuralNetwork network = networks.get(i);
        Pair<double[][], double[][]> split = splits.get(i);
        network.train(split.left(), split.right());
        trainCount++;
      }
    } else {
      for (NeuralNetwork network : networks) {
        network.train(inputs, outputs);
        trainCount++;
      }
    }
  }

  @Override
  public double[] predict(double[] input) {
    Preconditions.checkState(outputSize != -1, "Networks have not been trained yet!");
    Preconditions.checkState(trainCount >= networks.size(), "Networks have not been trained yet");

    double[] product = new double[outputSize];
    networks.forEach(
      network -> {
        AbstractNeuralNetwork abstr = (AbstractNeuralNetwork) network;
        double[] networkPrediction = abstr.predict(input);
        for (int i = 0; i < outputSize; i++)
          product[i] += networkPrediction[i];
      });

    IntStream.range(0, outputSize)
      .forEach(
        i -> product[i] /= networks.size()
      );

    return product;
  }

  @Override
  public double accuracy() {
    return 0;
  }

  @Override
  public Optimizer optimizer() {
    return null;
  }

  @Override
  public Attributes attributes() {
    return null;
  }

  @Override
  public Set<EpochAction<NeuralNetwork>> iterationActions() {
    return null;
  }

  @Override
  public void train() throws IllegalStateException {

  }

  @Override
  public Evaluation evaluate(TestSet set) {
    return null;
  }

  @Override
  public NeuralNetworkModel toModel(String name) {
    return null;
  }

  @Override
  public WrappedNeuralNetwork wrap() {
    return null;
  }

  @Override
  public void onCompletion(Consumer<NeuralNetwork> function) {

  }

  @Override
  public ActivationFunction activation() {
    throw new UnsupportedOperationException(
      "MultiModelWrapper uses multiple activation functions"
    );
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public boolean paused() {
    return false;
  }
}
