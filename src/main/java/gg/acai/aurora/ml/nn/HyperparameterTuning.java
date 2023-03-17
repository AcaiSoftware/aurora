package gg.acai.aurora.ml.nn;

import gg.acai.acava.Requisites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Clouke
 * @since 17.03.2023 01:09
 * © Aurora - All Rights Reserved
 */
public final class HyperparameterTuning {

  private final List<Tune> tunes;
  private final Function<Tune, NeuralNetworkTrainer> generate;

  public HyperparameterTuning(double[][] inputs, double[][] targets, List<Tune> tunes) {
    this.tunes = tunes;
    this.generate = tune -> {
      NeuralNetworkTrainer trainer = NetworkBuilder.training()
        .learningRate(tune.learningRate())
        .epochs(tune.epochs())
        .inputLayerSize(inputs[0].length)
        .hiddenLayerSize(tune.hiddenLayers())
        .outputLayerSize(targets[0].length)
        .disableStatsPrint()
        .disableProgressPrint()
        .accuracyTest(new double[]{targets[0][0]})
        .build();

      trainer.train(inputs, targets);

      return trainer;
    };
  }

  public Tune find() {
    return tunes.stream()
      .map(generate)
      .map(trainer -> trainer.stats().accuracy())
      .collect(Collectors.collectingAndThen(Collectors.toList(),
        list -> {
          double bestAccuracy = Collections.max(list);
          return tunes.get(list.indexOf(bestAccuracy));
        }));
  }

  public static class Builder {

    private double[][] inputs;
    private double[][] targets;

    private final List<Tune> tunes = new ArrayList<>();

    public Builder withTunes(List<Tune> tunes) {
      this.tunes.addAll(tunes);
      return this;
    }

    public Builder withTunes(Tune... tunes) {
      this.tunes.addAll(Arrays.asList(tunes));
      return this;
    }

    public Builder withInputs(double[][] inputs) {
      this.inputs = inputs;
      return this;
    }

    public Builder withTargets(double[][] targets) {
      this.targets = targets;
      return this;
    }

    public HyperparameterTuning build() {
      Requisites.requireNonNull(inputs, "Inputs cannot be null!");
      Requisites.requireNonNull(targets, "Targets cannot be null!");
      Requisites.checkArgument(tunes.size() > 0, "Tunes cannot be empty!");
      return new HyperparameterTuning(inputs, targets, tunes);
    }
  }
}
