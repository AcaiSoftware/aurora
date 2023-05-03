package gg.acai.aurora.model;

import org.apache.commons.lang.StringUtils;

/**
 * A machine learning context for a model.
 *
 * @author Clouke
 * @since 24.01.2023 12:57
 * © Acai - All Rights Reserved
 */
public enum MLContext {
  LINEAR_REGRESSION,
  LOGISTIC_REGRESSION,
  NEURAL_NETWORK,
  LEARNING_VECTOR_QUANTIZATION,
  NATURAL_LANGUAGE_PROCESSING,
  CLUSTERING;

  /**
   * Gets the string representation of this MLContext.
   *
   * @return Returns the string representation of this MLContext.
   */
  public String toString() {
    String name = name();
    String[] words = name.contains("_") ? name.split("_") : new String[]{name};
    for (int i = 0; i < words.length; i++) {
      words[i] = StringUtils.capitalize(words[i].toLowerCase());
    }
    return StringUtils.join(words, " ");
  }

  /**
   * Gets the short string representation of this MLContext.
   *
   * @return Returns the short string representation of this MLContext.
   */
  public String toShort() {
    switch(this) {
      case LINEAR_REGRESSION: return "LINREG";
      case LOGISTIC_REGRESSION: return "LOGREG";
      case NEURAL_NETWORK: return "NN";
      case LEARNING_VECTOR_QUANTIZATION: return "LVQ";
      case NATURAL_LANGUAGE_PROCESSING: return "NLP";
      case CLUSTERING: return "CLUST";
      default:
        throw new IllegalArgumentException("Unknown MLContext value: " + this);
    }
  }

}
