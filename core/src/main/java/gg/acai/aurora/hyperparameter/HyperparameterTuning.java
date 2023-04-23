package gg.acai.aurora.hyperparameter;

import gg.acai.aurora.ml.Trainable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Clouke
 * @since 16.04.2023 21:04
 * © Aurora - All Rights Reserved
 */
public class HyperparameterTuning {

  private final double[][] inputs;
  private final double[][] targets;
  private final HyperparameterOptions options;
  private final Function<Tune, Trainable> processor;
  private final List<Tune> tunes;

  public HyperparameterTuning(double[][] inputs, double[][] targets, HyperparameterOptions options, Function<Tune, Trainable> processor, List<Tune> tunes) {
    this.inputs = inputs;
    this.targets = targets;
    this.options = options;
    this.processor = processor;
    this.tunes = tunes;
  }

  public HyperparameterTuningResult find() {
    Map<Tune, Double> scores = new HashMap<>();
    TuningEvaluator evaluator = options.evaluator();
    Stream<Tune> stream = options.parallel()
      ? tunes.parallelStream()
      : tunes.stream();

    stream.forEach(tune -> {
      Trainable trainable = processor.apply(tune);
      double score = evaluator.evaluate(trainable, inputs, targets);
      scores.put(tune, score);
    });

    return evaluator.context().findBest(scores);
  }

}
