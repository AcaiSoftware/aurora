package gg.acai.aurora.model;

/**
 * A model convertible represents a class that can be converted to a storable model.
 * <p> This is often used in the context of converting a trainable to a model.
 * @see Model
 *
 * @author Clouke
 * @since 29.04.2023 16:34
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface ModelConvertible<M extends Model> {

  /**
   * Creates a model from the convertible.
   *
   * @param name The name of the model
   * @return Returns the model created from the convertible.
   */
  M toModel(String name);

  /**
   * Creates a model from the convertible.
   *
   * @return Returns the model created from the convertible.
   */
  default M toModel() {
    return toModel(null);
  }
}
