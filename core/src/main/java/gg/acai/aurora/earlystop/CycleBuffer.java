package gg.acai.aurora.earlystop;

/**
 * @author Clouke
 * @since 03.03.2023 16:02
 * © Aurora - All Rights Reserved
 */
public class CycleBuffer {

  private int buffers;
  private final int maxCycles;

  public CycleBuffer(int maxCycles) {
    this.maxCycles = maxCycles;
  }

  public int max() {
    return maxCycles;
  }

  public void set(int buffers) {
    this.buffers = buffers;
  }

  public boolean reached() {
    return add() >= maxCycles;
  }

  public int current() {
    return buffers;
  }

  public int add() {
    return ++buffers;
  }

  public void wipe() {
    buffers = 0;
  }

}
