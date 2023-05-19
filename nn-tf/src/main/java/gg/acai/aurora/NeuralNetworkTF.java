package gg.acai.aurora;


import com.google.gson.Gson;
import gg.acai.acava.commons.Attributes;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.model.Evaluation;
import gg.acai.aurora.model.ModelMetrics;
import gg.acai.aurora.model.Predictable;
import gg.acai.aurora.optimizers.Optimizer;
import gg.acai.aurora.sets.DataSet;
import gg.acai.aurora.sets.TestSet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Clouke
 * @since 19.05.2023 20:56
 * © Aurora - All Rights Reserved
 */
public class NeuralNetworkTF implements NeuralNetwork, Predictable {

  private double[][] weights_input_hidden;
  private double[][] weights_hidden_output;
  private double[] biases_hidden;
  private double[] biases_output;

  private final String pythonPath;
  private final int epochs;
  private final String optimizer;
  private final double learningRate;
  private final List<Dense> layers;
  private final DataSet dataSet;
  private Consumer<NeuralNetwork> completion;

  public NeuralNetworkTF(NeuralNetworkTFBuilder builder) {
    this.pythonPath = builder.pythonPath;
    this.epochs = builder.epochs;
    this.optimizer = builder.optimizer;
    this.learningRate = builder.learningRate;
    this.layers = builder.layers;
    this.dataSet = builder.dataSet;
  }

  @Override
  public void train(double[][] inputs, double[][] outputs) {
    try {
      /*
       * Flags:
       * - inputs: The inputs of the data set
       * - outputs: The outputs of the data set
       * - e: The amount of epochs
       * - opt: The optimizer
       * - layers: Dense layers
       * - lr: The learning rate
       */
      Process process = new ProcessBuilder(
        "python", pythonPath,
        "-inputs", Arrays.deepToString(inputs),
        "-outputs", Arrays.deepToString(outputs),
        "-e", String.valueOf(epochs),
        "-opt", optimizer,
        "-layers", String.valueOf(layers),
        "-lr", String.valueOf(learningRate))
        /*
         * Inherit the IO of the current process to get the output of the python script
         * and print it to the console
         */
        .inheritIO()
        .start();

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      String lastLine = null;
      while ((line = reader.readLine()) != null) {
        lastLine = line;
      }

      WrappedNeuralNetwork wrapper;
      try {
        Gson gson = GsonSpec.standard();
        wrapper = gson.fromJson(lastLine, WrappedNeuralNetwork.class);
      } catch (Exception e) {
        throw new Exception("Failed to parse JSON: " + lastLine);
      }

      this.weights_input_hidden = wrapper.weights_input_to_hidden();
      this.weights_hidden_output = wrapper.weights_hidden_to_output();
      this.biases_hidden = wrapper.biases_hidden();
      this.biases_output = wrapper.biases_output();

      int exitCode = process.waitFor();
      if (exitCode != 0)
        throw new Exception("Python script exited with code " + exitCode);
      System.out.println("Completed training");
      if (completion != null)
        completion.accept(this);

      process.destroy();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void train() throws IllegalStateException {
    if (dataSet == null)
      throw new IllegalStateException("No data set provided");
    train(dataSet.inputs(), dataSet.targets());
  }

  @Override
  public double[] predict(double[] input) {
    int inputSize = layers.get(0).units();
    int outputSize = layers.get(layers.size() - 1).units();

    // forward pass
    double[] hiddenLayerOutput = new double[inputSize];
    double[] output = new double[outputSize];

    // hidden layer output
    for (int i = 0; i < inputSize; i++) {
      double weightedSum = biases_hidden[i];
      for (int j = 0; j < inputSize; j++) {
        weightedSum += input[j] * weights_input_hidden[j][i];
      }
      ActivationFunction activation = ActivationFunction.optionally(
        layers.get(0)
          .activation())
        .orElse(ActivationFunction.SIGMOID);
      hiddenLayerOutput[i] = activation.apply(weightedSum);
    }

    // output layer output
    for (int i = 0; i < outputSize; i++) {
      double weightedSum = biases_output[i];
      for (int j = 0; j < inputSize; j++) {
        weightedSum += hiddenLayerOutput[j] * weights_hidden_output[j][i];
      }
      ActivationFunction activation = ActivationFunction.optionally(
        layers.get(layers.size() - 1)
          .activation())
        .orElse(ActivationFunction.SIGMOID);
      output[i] = activation.apply(weightedSum);
    }

    return output;
  }

  @Override
  public Evaluation evaluate(TestSet set) {
    return new ModelMetrics(this, set);
  }

  @Override
  public NeuralNetworkModel toModel(String name) {
    return new NeuralNetworkModel(name, weights_input_hidden, weights_hidden_output, biases_hidden, biases_output);
  }

  @Override
  public WrappedNeuralNetwork wrap() {
    return new WrappedNeuralNetwork(
      weights_input_hidden,
      weights_hidden_output,
      biases_hidden,
      biases_output
    );
  }

  @Override
  public Optimizer optimizer() {
    throw new UnsupportedOperationException("");
  }

  @Override
  public Attributes attributes() {
    throw new UnsupportedOperationException("");
  }

  @Override
  public Set<EpochAction<NeuralNetwork>> iterationActions() {
    throw new UnsupportedOperationException("");
  }

  @Override
  public double accuracy() {
    return 0;
  }

  @Override
  public void onCompletion(Consumer<NeuralNetwork> function) {
    this.completion = function;
  }

  @Override
  public ActivationFunction activation() {
    return null;
  }

  @Override
  public void close() {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public boolean paused() {
    return false;
  }
}
