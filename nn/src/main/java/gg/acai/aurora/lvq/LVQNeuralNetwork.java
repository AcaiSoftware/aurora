package gg.acai.aurora.lvq;

import gg.acai.acava.commons.Attributes;
import gg.acai.acava.commons.AttributesMapper;
import gg.acai.acava.io.Closeable;
import gg.acai.aurora.publics.io.ComplexProgressTicker;
import gg.acai.aurora.model.Trainable;

/**
 * A trainable LVQ neural network implementation.
 *
 * @author Clouke
 * @since 28.04.2023 12:45
 * © Aurora - All Rights Reserved
 */
public class LVQNeuralNetwork extends AbstractLVQNeuralNetwork implements Trainable, Closeable {

  private double learningRate;
  private double decayRate;
  private final double learningRateScale;
  private final double decayRateScale;
  private final int epochs;
  private final ComplexProgressTicker ticker;
  private final Attributes attributes;
  private double accuracy = -1.0;

  public LVQNeuralNetwork(LVQNeuralNetworkBuilder builder) {
    super(builder.inputSize, builder.outputSize);
    this.learningRate = builder.learningRate;
    this.decayRate = builder.decayRate;
    this.learningRateScale = builder.learningRateStep;
    this.decayRateScale = builder.decayStep;
    this.epochs = builder.epochs;
    this.ticker = new ComplexProgressTicker(builder.progressBar, 1);
    this.attributes = new AttributesMapper();
  }

  /**
   * Trains this LVQ on the given inputs and outputs.
   * <p><strong>NOTE:</strong> The outputs must be convertible to an array of ints.</p>
   *
   * @param inputs The inputs to train on
   * @param outputs The outputs to train on
   */
  @Override
  public void train(double[][] inputs, double[][] outputs) {
    int[] targets = new int[outputs.length];
    for (int i = 0; i < outputs.length; i++) { // convert the outputs to an array of ints
      try {
        targets[i] = (int) outputs[i][0];
      } catch (Exception e) {
        throw new IllegalArgumentException("The outputs must support conversion to an array of ints");
      }
    }
    train(inputs, targets);
  }

  /**
   * Trains this LVQ on the given inputs and targets.
   *
   * @param inputs The inputs to train on
   * @param targets The targets to train on
   */
  public void train(double[][] inputs, int[] targets) {
    for (int epoch = 0; epoch <= epochs; epoch++) {
      int i = random.nextInt(inputs.length);
      double[] input = inputs[i];
      int target = targets[i];
      int winner = classify(input); // find the winning neuron

      // update the weights of the winning neuron and its neighbors
      for (int nodeOut = 0; nodeOut < numOutputs; nodeOut++) {
        if (nodeOut == winner) {
          for (int nodeIn = 0; nodeIn < numInputs; nodeIn++) {
            weights[nodeOut][nodeIn] += learningRate * (input[nodeIn] - weights[nodeOut][nodeIn]);
          }
        } else if (targets[nodeOut] == target) {
          for (int nodeIn = 0; nodeIn < numInputs; nodeIn++) {
            weights[nodeOut][nodeIn] -= decayRate * learningRate * (input[nodeIn] - weights[nodeOut][nodeIn]);
          }
        }
      }

      // update loss and accuracy
      double loss = 0.0;
      for (int nodeIn = 0; nodeIn < numInputs; nodeIn++) {
        loss += Math.pow(input[nodeIn] - weights[winner][nodeIn], 2);
      }
      loss = Math.sqrt(loss);

      double accuracy = 0.0;
      for (int nodeOut = 0; nodeOut < numOutputs; nodeOut++) {
        if (nodeOut == winner) {
          accuracy += 1.0;
        } else if (targets[nodeOut] == target) {
          accuracy += 0.5;
        }
      }
      accuracy /= numOutputs;
      this.accuracy = accuracy;

      int pct = 100 * (epoch + 1) / epochs;
      attributes.set("epoch", epoch)
        .set("stage", pct)
        .set("accuracy", accuracy)
        .set("loss", loss);

      ticker.tick(pct, attributes);

      // decrease the learning rate and decay rate
      learningRate *= learningRateScale;
      decayRate *= decayRateScale;
    }
  }

  @Override
  public double accuracy() {
    return accuracy;
  }

  @Override
  public void close() {
    ticker.close();
  }

  @Override
  public Attributes attributes() {
    return attributes.copy();
  }
}
