package gg.acai.aurora.earlystop;

/**
 * A simple buffer for cycle-based early stopping.
 *
 * @author Clouke
 * @since 03.03.2023 16:02
 * © Aurora - All Rights Reserved
 */
public class CycleBuffer {

  private int buffers;
  private final int maxCycles;

  /**
   * Constructs a new {@link CycleBuffer} with the given maximum cycles.
   *
   * @param maxCycles The maximum cycles to buffer for.
   */
  public CycleBuffer(int maxCycles) {
    this.maxCycles = maxCycles;
  }

  /**
   * Gets the maximum cycles to buffer for.
   *
   * @return Returns the maximum cycles to buffer for.
   */
  public int max() {
    return maxCycles;
  }

  /**
   * Sets the current number of buffers.
   *
   * @param buffers The given number of buffers.
   */
  public void set(int buffers) {
    this.buffers = buffers;
  }

  /**
   * Adds one to the current number of buffers.
   *
   * @return Returns whether the maximum number of buffers has been reached.
   */
  public boolean reached() {
    return add() >= maxCycles;
  }

  /**
   * Gets the current number of buffers.
   *
   * @return Returns the current number of buffers.
   */
  public int current() {
    return buffers;
  }

  /**
   * Adds one to the current number of buffers.
   *
   * @return Returns the current number of buffers.
   */
  public int add() {
    return ++buffers;
  }

  /**
   * Wipes the current number of buffers to zero.
   */
  public void wipe() {
    buffers = 0;
  }

}
