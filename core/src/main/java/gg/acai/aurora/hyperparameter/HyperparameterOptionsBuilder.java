package gg.acai.aurora.hyperparameter;

import gg.acai.aurora.hyperparameter.evaluator.BestAccuracyEvaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clouke
 * @since 16.04.2023 21:20
 * © Aurora - All Rights Reserved
 */
public class HyperparameterOptionsBuilder {

  private final List<Tune> tunes = new ArrayList<>();
  private TuningEvaluator evaluator;
  private boolean parallel;

  /**
   * Applies the tuning evaluator.
   *
   * @param evaluator The tuning evaluator to be used when tuning.
   * @return Returns this builder for chaining.
   */
  public HyperparameterOptionsBuilder evaluator(TuningEvaluator evaluator) {
    this.evaluator = evaluator;
    return this;
  }

  /**
   * Generates tunes from the min and max parameters.
   *
   * @param p The min and max parameters.<p>
   *         Use {@link MinMaxParameters#DEFAULT} for default parameters.
   *          or {@link MinMaxParameters#builder()} to create your own.
   * @return Returns this builder for chaining.
   */
  public HyperparameterOptionsBuilder generateTunes(MinMaxParameters p) {
    for (double learningRate = p.minLearningRate(); learningRate <= p.maxLearningRate(); learningRate += p.learningRateStep()) {
      for (int epoch = p.minEpochs(); epoch <= p.maxEpochs(); epoch++) {
        for (int layer = p.minLayers(); layer <= p.maxLayers(); layer++) {
          tunes.add(new StandardTune(learningRate, epoch, layer));
        }
      }
    }
    return this;
  }

  /**
   * Generates tunes from the default min and max parameters.
   *
   * @return Returns this builder for chaining.
   */
  public HyperparameterOptionsBuilder generateTunes() {
    return generateTunes(MinMaxParameters.DEFAULT);
  }

  /**
   * Marks the tuning to be done in parallel.
   *
   * @return Returns this builder for chaining.
   */
  public HyperparameterOptionsBuilder parallel() {
    this.parallel = true;
    return this;
  }

  /**
   * Builds the hyperparameter options.
   *
   * @return Returns the hyperparameter options.
   */
  public HyperparameterOptions build() {
    if (evaluator == null) evaluator = new BestAccuracyEvaluator();
    return new HyperparameterOptions(evaluator, tunes, parallel);
  }

}
