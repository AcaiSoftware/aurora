package gg.acai.aurora.model;

/**
 * A context provider for a machine learning context.
 *
 * @author Clouke
 * @since 24.01.2023 12:56
 * © Acai - All Rights Reserved
 */
@FunctionalInterface
public interface MLContextProvider {
  /**
   * Gets the machine learning context.
   *
   * @return Returns the machine learning context.
   */
  MLContext context();
}
