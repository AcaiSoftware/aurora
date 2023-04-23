package gg.acai.aurora;

/**
 * @author Clouke
 * @since 09.04.2023 20:28
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Evaluator extends Runnable {

  double evaluate();

  @Override
  default void run() {
    evaluate();
  }
}
