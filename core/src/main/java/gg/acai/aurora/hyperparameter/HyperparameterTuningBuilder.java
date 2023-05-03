package gg.acai.aurora.hyperparameter;

import gg.acai.acava.Requisites;
import gg.acai.aurora.model.Trainable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Clouke
 * @since 16.04.2023 21:19
 * © Aurora - All Rights Reserved
 */
public class HyperparameterTuningBuilder {

  private final HyperparameterOptionsBuilder builder = new HyperparameterOptionsBuilder();
  private final List<Tune> tunes = new ArrayList<>();

  private double[][] inputs;
  private double[][] targets;
  private HyperparameterOptions options;
  private Function<Tune, Trainable> processor;

  public HyperparameterTuningBuilder inputs(double[][] inputs) {
    this.inputs = inputs;
    return this;
  }

  public HyperparameterTuningBuilder targets(double[][] targets) {
    this.targets = targets;
    return this;
  }

  public HyperparameterTuningBuilder data(double[][] inputs, double[][] targets) {
    this.inputs = inputs;
    this.targets = targets;
    return this;
  }

  public HyperparameterTuningBuilder options(Consumer<HyperparameterOptionsBuilder> options) {
    options.accept(builder);
    this.options = builder.build();
    return this;
  }

  public HyperparameterTuningBuilder processor(Function<Tune, Trainable> processor) {
    this.processor = processor;
    return this;
  }

  public HyperparameterTuning build() {
    List<Tune> optionsTunes = options.tunes();
    if (!optionsTunes.isEmpty()) {
      tunes.addAll(optionsTunes);
    }
    Requisites.requireNonNull(inputs, "inputs cannot be null");
    Requisites.requireNonNull(targets, "targets cannot be null");
    Requisites.checkArgument(inputs.length == targets.length, "inputs and targets must have the same length");
    Requisites.checkArgument(!tunes.isEmpty(), "tunes cannot be empty");
    return new HyperparameterTuning(inputs, targets, options, processor, tunes);
  }

}
