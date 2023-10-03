package gg.acai.aurora.policy;

import java.util.Map;
import java.util.Optional;

/**
 * @author Clouke
 * @since 07.09.2023 18:49
 * © Aurora - All Rights Reserved
 */
public class AbstractDecayPolicy<T> implements DecayPolicy<T> {

  private final Map<Integer, T> steps;

  public AbstractDecayPolicy(Map<Integer, T> steps) {
    this.steps = steps;
  }

  @Override
  public T value(int step) {
    return steps.get(step);
  }

  @Override
  public Optional<T> fold(int step) {
    return Optional.ofNullable(steps.get(step));
  }

  @Override
  public T value(int step, T defaultValue) {
    return steps.getOrDefault(
      step,
      defaultValue
    );
  }
}
