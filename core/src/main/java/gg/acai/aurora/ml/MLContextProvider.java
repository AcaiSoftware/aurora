package gg.acai.aurora.ml;

/**
 * @author Clouke
 * @since 24.01.2023 12:56
 * © Acai - All Rights Reserved
 */
@FunctionalInterface
public interface MLContextProvider {
  MLContext context();
}