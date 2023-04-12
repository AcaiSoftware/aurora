package gg.acai.aurora.exception;

/**
 * @author Clouke
 * @since 02.03.2023 20:10
 * © Aurora - All Rights Reserved
 */
public class InvalidModelException extends RuntimeException {
  public InvalidModelException(String message) {
    super(message);
  }

  public InvalidModelException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidModelException(Throwable cause) {
    super(cause);
  }
}
