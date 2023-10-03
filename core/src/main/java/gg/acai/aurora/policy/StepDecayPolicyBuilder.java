package gg.acai.aurora.policy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Clouke
 * @since 07.09.2023 18:44
 * © Aurora - All Rights Reserved
 */
public class StepDecayPolicyBuilder<T> {

  private final Map<Integer, T> steps = new HashMap<>();

  public StepDecayPolicyBuilder<T> addStep(int step, T value) {
    steps.put(step, value);
    return this;
  }

  public StepDecayPolicyBuilder<T> fill(int start, int end, T value) {
    for (int i = start; i < end; i++)
      steps.put(i, value);
    return this;
  }

  public StepDecayPolicyBuilder<T> from(Map<Integer, T> steps) {
    this.steps.putAll(steps);
    return this;
  }

  public DecayPolicy<T> build() {
    return new AbstractDecayPolicy<>(steps);
  }


}
