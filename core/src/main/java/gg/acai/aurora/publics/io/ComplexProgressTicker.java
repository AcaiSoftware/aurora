package gg.acai.aurora.publics.io;

import gg.acai.acava.commons.Attributes;
import gg.acai.acava.io.Closeable;

/**
 * @author Clouke
 * @since 27.04.2023 15:35
 * © Aurora - All Rights Reserved
 */
public class ComplexProgressTicker implements Closeable {

  private final Bar progressBar;
  private int lastTick = -1;

  public ComplexProgressTicker(Bar progressBar, int startRate) {
    this.progressBar = progressBar;
    if (startRate != 0 && progressBar != null) {
      for (int i = 0; i < startRate; i++) {
        progressBar.tick();
      }
    }
  }

  public ComplexProgressTicker(Bar progressBar) {
    this(progressBar, 0);
  }

  public void tick(int tick, Attributes attributes) {
    if (progressBar == null)
      return;
    if (tick != lastTick) {
      progressBar.tick();
      progressBar.print(attributes);
    }
    lastTick = tick;
  }

  @Override
  public void close() {
    if (progressBar != null) {
      progressBar.close();
      System.out.println("\n");
    }
  }
}
