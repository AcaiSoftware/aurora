package gg.acai.aurora.core.ml.nn;

import gg.acai.aurora.QRMath;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Clouke
 * @since 15.02.2023 06:38
 * © Acava - All Rights Reserved
 */
public abstract class AbstractNeuralNetwork {

    protected String model;

    protected final double[][] weights_input_to_hidden;
    protected final double[][] weights_hidden_to_output;
    protected final double[] biases_hidden;
    protected final double[] biases_output;

    public AbstractNeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
        weights_input_to_hidden = new double[inputSize][hiddenSize];
        biases_hidden = new double[hiddenSize];
        Random r = ThreadLocalRandom.current();
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < hiddenSize; j++) {
                weights_input_to_hidden[i][j] = r.nextGaussian();
            }
        }
        for (int i = 0; i < hiddenSize; i++) {
            biases_hidden[i] = r.nextGaussian();
        }
        weights_hidden_to_output = new double[hiddenSize][outputSize];
        biases_output = new double[outputSize];
        for (int i = 0; i < hiddenSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                weights_hidden_to_output[i][j] = r.nextGaussian();
            }
        }
        for (int i = 0; i < outputSize; i++) {
            biases_output[i] = r.nextGaussian();
        }
    }

    public AbstractNeuralNetwork(double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] biases_hidden, double[] biases_output) {
        this.weights_input_to_hidden = weights_input_to_hidden;
        this.weights_hidden_to_output = weights_hidden_to_output;
        this.biases_hidden = biases_hidden;
        this.biases_output = biases_output;
    }

    public Optional<String> getModel() {
        return Optional.ofNullable(model);
    }

    public double[] predict(double[] input) {
        double[] hidden = new double[weights_input_to_hidden[0].length];
        for (int i = 0; i < hidden.length; i++) {
            hidden[i] = biases_hidden[i];
            for (int j = 0; j < input.length; j++) {
                hidden[i] += input[j] * weights_input_to_hidden[j][i];
            }
            hidden[i] = QRMath.sigmoid(hidden[i]);
        }
        double[] output = new double[weights_hidden_to_output[0].length];
        for (int i = 0; i < output.length; i++) {
            output[i] = biases_output[i];
            for (int j = 0; j < hidden.length; j++) {
                output[i] += hidden[j] * weights_hidden_to_output[j][i];
            }
            output[i] = QRMath.sigmoid(output[i]);
        }

        return output;
    }

}