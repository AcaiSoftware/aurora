package gg.acai.aurora.model;

/**
 * @author Clouke
 * @since 29.04.2023 16:34
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface ModelConvertible<M extends Model> {

  M toModel(String name);

  default M toModel() {
    return toModel(null);
  }
}
