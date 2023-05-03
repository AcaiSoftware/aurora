package gg.acai.aurora.publics;

import java.util.concurrent.Callable;

/**
 * An abstract class that represents a ticker.
 *
 * @author Clouke
 * @since 25.04.2023 17:30
 * © Aurora - All Rights Reserved
 */
public abstract class Ticker<T> implements Callable<T> {

  /**
   * Reads the current ticker value.
   *
   * @return Returns the current ticker value
   */
  public abstract T read();

  /**
   * Calls {@link #read()}.
   *
   * @return Returns the current ticker value
   */
  @Override
  public T call() {
    return read();
  }

  private static final Ticker<Long> SYSTEM_NANOS = new Ticker<Long>() {
    @Override
    public Long read() {
      return System.nanoTime();
    }
  };

  private static final Ticker<Long> SYSTEM_MILLIS = new Ticker<Long>() {
    @Override
    public Long read() {
      return System.currentTimeMillis();
    }
  };

  public static Ticker<Long> systemNanos() {
    return SYSTEM_NANOS;
  }

  public static Ticker<Long> systemMillis() {
    return SYSTEM_MILLIS;
  }


}
