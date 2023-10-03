package gg.acai.aurora.policy;

import java.util.Optional;

/**
 * @author Clouke
 * @since 07.09.2023 18:41
 * © Aurora - All Rights Reserved
 */
public interface DecayPolicy<T> {
  T value(int step);

  Optional<T> fold(int step);

  T value(int step, T defaultValue);
}
