package gg.acai.aurora.earlystop;

/**
 * @author Clouke
 * @since 03.03.2023 16:02
 * © Aurora - All Rights Reserved
 */
public class CycleBuffer {

  private int buffers;
  private final int max;

  public CycleBuffer(int max) {
    this.max = max;
  }

  public void set(int buffers) {
    this.buffers = buffers;
  }

  public boolean reached() {
    return add() >= max;
  }

  public int current() {
    return buffers;
  }

  public int add() {
    return ++buffers;
  }

  public void reset() {
    buffers = 0;
  }

}
