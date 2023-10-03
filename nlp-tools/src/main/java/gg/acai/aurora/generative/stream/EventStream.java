package gg.acai.aurora.generative.stream;

/**
 * @author Clouke
 * @since 05.07.2023 22:08
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface EventStream<T> {
  void emit(T waterfall);
}

