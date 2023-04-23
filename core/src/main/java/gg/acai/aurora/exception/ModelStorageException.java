package gg.acai.aurora.exception;

/**
 * @author Clouke
 * @since 18.04.2023 18:09
 * © Aurora - All Rights Reserved
 */
public class ModelStorageException extends RuntimeException {

  public ModelStorageException(String message) {
    super(message);
  }

  public ModelStorageException(String message, Throwable cause) {
    super(message, cause);
  }

  public ModelStorageException(Throwable cause) {
    super(cause);
  }

}
