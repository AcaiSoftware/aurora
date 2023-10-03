package gg.acai.opt;

import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.ModelLoader;

/**
 * @author Clouke
 * @since 04.07.2023 18:44
 * © Aurora - All Rights Reserved
 */
public class OptWeightingBuilder {

  protected String name = "OptWeighting";
  protected double learningRate = 0.01;
  protected double gamma = 0.9;
  protected double beta = 0.9;
  protected double epsilon = 1e-8;
  protected long seed = System.currentTimeMillis();
  protected ActivationFunction activation;
  protected OptWeighting loaded;

  public OptWeightingBuilder from(OptWeighting loaded) {
    this.loaded = loaded;
    return this;
  }

  public OptWeightingBuilder from(ModelLoader loader) throws RuntimeException {
    try {
      this.loaded = loader.load(OptWeighting.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to load model", e);
    } finally {
      loader.close();
    }
    return this;
  }

  public OptWeightingBuilder name(String name) {
    this.name = name;
    return this;
  }

  public OptWeightingBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  public OptWeightingBuilder gamma(double gamma) {
    this.gamma = gamma;
    return this;
  }

  public OptWeightingBuilder beta(double beta) {
    this.beta = beta;
    return this;
  }

  public OptWeightingBuilder epsilon(double epsilon) {
    this.epsilon = epsilon;
    return this;
  }

  public OptWeightingBuilder seed(long seed) {
    this.seed = seed;
    return this;
  }

  public OptWeightingBuilder activation(ActivationFunction activation) {
    this.activation = activation;
    return this;
  }

  public OptWeighting build() {
    return new OptWeighting(this);
  }


}
