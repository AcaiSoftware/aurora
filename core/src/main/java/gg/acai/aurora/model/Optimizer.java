package gg.acai.aurora.model;

/**
 * @author Clouke
 * @since 19.04.2023 20:09
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Optimizer {
  void update(double[][] inputs, double[][] targets, double[][] weightsInputToHidden, double[][] weightsHiddenToOutput, double[] biasesHidden, double[] biasesOutput);

  default Optimizer andThen(Optimizer other) {
    return (inputs, targets, weightsInputToHidden, weightsHiddenToOutput, biasesHidden, biasesOutput) -> {
      update(inputs, targets, weightsInputToHidden, weightsHiddenToOutput, biasesHidden, biasesOutput);
      other.update(inputs, targets, weightsInputToHidden, weightsHiddenToOutput, biasesHidden, biasesOutput);
    };
  }
}

