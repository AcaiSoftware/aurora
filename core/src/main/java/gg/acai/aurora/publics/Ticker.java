package gg.acai.aurora.publics;

import java.util.concurrent.Callable;

/**
 * @author Clouke
 * @since 25.04.2023 17:30
 * © Aurora - All Rights Reserved
 */
public abstract class Ticker<T> implements Callable<T> {

  public abstract T read();

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
