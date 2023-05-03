package gg.acai.aurora.hyperparameter;

import gg.acai.acava.Requisites;
import gg.acai.aurora.model.Trainable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A builder for {@link HyperparameterTuning}.
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

  /**
   * Applies the required inputs for the tuning.
   *
   * @param inputs the inputs for the model
   * @return Returns this builder for chaining.
   */
  public HyperparameterTuningBuilder inputs(double[][] inputs) {
    this.inputs = inputs;
    return this;
  }

  /**
   * Applies the required targets for the tuning.
   *
   * @param targets the targets for the model
   * @return Returns this builder for chaining.
   */
  public HyperparameterTuningBuilder targets(double[][] targets) {
    this.targets = targets;
    return this;
  }

  /**
   * Applies the required inputs and targets for the tuning.
   *
   * @param inputs the inputs for the model
   * @param targets the targets for the model
   * @return Returns this builder for chaining.
   */
  public HyperparameterTuningBuilder data(double[][] inputs, double[][] targets) {
    this.inputs = inputs;
    this.targets = targets;
    return this;
  }

  /**
   * Applies the optional options for the tuning.
   *
   * @param options the options for the tuning
   * @return Returns this builder for chaining.
   */
  public HyperparameterTuningBuilder options(Consumer<HyperparameterOptionsBuilder> options) {
    options.accept(builder);
    this.options = builder.build();
    return this;
  }

  /**
   * Applies the required processor for the tuning.
   * <p>
   * The processor is a function that accepts a tune and returns a trainable model.
   * See the example usage in the class documentation.
   *
   * <p>Example Trainables:
   * <ul>
   *   <li> NeuralNetwork </li>
   *   <li> LogisticRegression </li>
   *   <li> LinearRegression </li>
   *   <li> LearningVectorQuantization </li>
   * </ul>
   *
   * as well as any other model that implements {@link Trainable}.
   *
   * @param processor the processor for the tuning
   * @return Returns this builder for chaining.
   */
  public HyperparameterTuningBuilder processor(Function<Tune, Trainable> processor) {
    this.processor = processor;
    return this;
  }

  /**
   * Builds the {@link HyperparameterTuning} instance.
   *
   * @return Returns the built instance.
   */
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
