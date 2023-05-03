package gg.acai.aurora.hyperparameter;

import gg.acai.aurora.model.Trainable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * HyperparameterTuning can be used to find the best hyperparameters for a given model.
 * <p><b>Example Usage: (in this example, we are tuning a neural network)</b></p>
 * <pre>
 *  {@code
 *    HyperparameterTuning tuning = new HyperparameterTuningBuilder()
 *       .options(options -> options
 *         .generateTunes(MinMaxParameters.builder()
 *           .learningRate(0.01, 0.1, 0.02) // min --> max --> step
 *           .epochs(1, 1_000) // min --> max
 *           .layers(1, 2) // min --> max
 *           .build())
 *         .evaluator(new CrossValidator(4, new BestAccuracyEvaluator())))
 *       .processor(tune -> new NeuralNetworkBuilder()
 *         .learningRate(tune.learningRate())
 *         .epochs(tune.epochs())
 *         .layers(mapper -> mapper
 *           .inputLayers(inputLayerSize)
 *           .hiddenLayers(tune.layers())
 *           .outputLayers(outputLayerSize))
 *         .disableStatsPrint()
 *         .build())
 *       .inputs(inputs)
 *       .targets(outputs)
 *       .build();
 *
 *     HyperparameterTuningResult result = tuning.find();
 *  }
 * </pre>
 *
 * <p>
 *  Tuning is thread-safe and supports parallel tuning.
 *  by default, the tuning will run on a single thread, but can be parallelized by calling
 *  {@link HyperparameterOptionsBuilder#parallel()} before building the options.
 * </p>
 *
 * <p> Supported evaluators:
 * <ul>
 *   <li> BestAccuracyEvaluator </li>
 * </ul>
 * More evaluators will be added in the future.
 *
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

  /**
   * Finds the best hyperparameters for the given model.
   *
   * @return Returns a {@link HyperparameterTuningResult} containing the best hyperparameters.
   */
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
