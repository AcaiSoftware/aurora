package gg.acai.aurora.earlystop;

/**
 * @author Clouke
 * @since 23.04.2023 08:22
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractEarlyStop implements EarlyStop {

  private boolean printed;

  protected void print(String... messages) {
    if (!printed) {
      for (String message : messages)
        System.out.println(message);
      printed = true;
    }
  }

}
