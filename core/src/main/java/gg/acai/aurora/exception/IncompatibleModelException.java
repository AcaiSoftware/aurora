package gg.acai.aurora.exception;

/**
 * A runtime exception thrown when a model is incompatible such as version mismatch.
 *
 * @author Clouke
 * @since 03.03.2023 13:36
 * © Aurora - All Rights Reserved
 */
public class IncompatibleModelException extends RuntimeException {

  public IncompatibleModelException(String message) {
    super(message);
  }

  public IncompatibleModelException(String message, Throwable cause) {
    super(message, cause);
  }

  public IncompatibleModelException(Throwable cause) {
    super(cause);
  }

}
