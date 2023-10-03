package gg.acai.opt;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.Model;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A regularization graph weighting model.
 *
 * @author Clouke
 * @since 04.07.2023 18:41
 * © Aurora - All Rights Reserved
 */
public class OptWeighting implements Model {

  protected final transient Type serialization = new TypeToken<OptWeighting>(){}.getType();

  protected String name;
  protected String savePath;

  protected final HashMap<Double, Double> weights;
  protected final ActivationFunction activation;
  protected final Random random;
  protected final double learningRate;
  protected final double gamma;
  protected final double beta;
  protected final double epsilon;
  protected double m = 0;
  protected double v = 0;

  public OptWeighting(OptWeightingBuilder builder) {
    OptWeighting pretrained = builder.loaded;
    if (pretrained != null) {
      this.weights = new HashMap<>(pretrained.weights);
      this.random = pretrained.random;
      this.learningRate = pretrained.learningRate;
      this.activation = pretrained.activation;
      this.gamma = pretrained.gamma;
      this.beta = pretrained.beta;
      this.epsilon = pretrained.epsilon;
      this.name = pretrained.name;
      return;
    }
    this.weights = new HashMap<>();
    this.random = new Random(builder.seed);
    this.learningRate = builder.learningRate;
    this.activation = builder.activation;
    this.gamma = builder.gamma;
    this.beta = builder.beta;
    this.epsilon = builder.epsilon;
    this.name = builder.name;
  }

  public void fit(Map<Double, Integer> samples, int epochs) {
    for (double data : samples.keySet())
      weights.put(data, (random.nextDouble() - 0.5));

    for (int i = 0; i < epochs; i++) {
      for (double data : samples.keySet()) {
        double prediction = predict(data)[0];
        double error = samples.get(data) - prediction;
        double delta = error * prediction * (1 - prediction) * learningRate;
        double grad = delta * prediction * (1 - prediction);
        m = beta * m + (1 - beta) * grad;
        v = gamma * v + (1 - gamma) * grad * grad;
        double mHat = m / (1 - Math.pow(beta, i + 1));
        double vHat = v / (1 - Math.pow(gamma, i + 1));
        double weight = weights.get(data);
        weight -= learningRate * mHat / (Math.sqrt(vHat) + epsilon);
        weights.put(
          data,
          weight
        );
      }
    }
  }

  public double[] predict(double... nodes) {
    int len = nodes.length;
    double[] output = new double[len];
    for (int i = 0; i < len; i++) {
      if (weights.containsKey(nodes[i])) {
        double weight = weights.getOrDefault(nodes[i], 1.0);
        output[i] = (1.0 - activation.apply(weight));
      } else {
        double regularizationNode = 0.0;
        for (double node : weights.keySet()) {
          if (Math.abs(node - nodes[i]) < Math.abs(regularizationNode - nodes[i]))
            regularizationNode = node;
        }
        double distance = Math.abs(regularizationNode - nodes[i]);
        double weight = weights.getOrDefault(regularizationNode, 1.0);
        output[i] = (1.0 - activation.apply(weight)) * (1.0 - distance);
      }
    }
    return output;
  }

  public Type serialization() {
    return serialization;
  }

  @Override
  public String toString() {
    return "OptWeighting{" +
      "weights=" + weights +
      ", activation=" + activation +
      ", random=" + random +
      ", learningRate=" + learningRate +
      ", gamma=" + gamma +
      ", beta=" + beta +
      ", epsilon=" + epsilon +
      ", m=" + m +
      ", v=" + v +
      ", name='" + name + '\'' +
      ", savePath='" + savePath + '\'' +
      '}';
  }

  @Override
  public Model name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Model saveDirectoryPath(String saveDirectory) {
    this.savePath = saveDirectory;
    return this;
  }

  @Override
  public Model saveOnClose(boolean saveOnClose) {
    throw new UnsupportedOperationException("OptWeighting does not support this operation.");
  }

  @Override
  public String version() {
    return "1.0.0";
  }

  @Override
  public String saveDirectoryPath() {
    return savePath;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public void close() {}

  @Override
  public String serialize() {
    Gson gson = GsonSpec.standard();
    Type type = serialization();
    return gson.toJson(this, type);
  }
}
