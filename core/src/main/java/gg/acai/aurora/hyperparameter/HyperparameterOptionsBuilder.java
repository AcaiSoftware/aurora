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

  public HyperparameterOptionsBuilder evaluator(TuningEvaluator evaluator) {
    this.evaluator = evaluator;
    return this;
  }

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

  public HyperparameterOptionsBuilder generateTunes() {
    return generateTunes(MinMaxParameters.DEFAULT);
  }

  public HyperparameterOptionsBuilder parallel() {
    this.parallel = true;
    return this;
  }

  public HyperparameterOptions build() {
    if (evaluator == null) evaluator = new BestAccuracyEvaluator();
    return new HyperparameterOptions(evaluator, tunes, parallel);
  }

}
