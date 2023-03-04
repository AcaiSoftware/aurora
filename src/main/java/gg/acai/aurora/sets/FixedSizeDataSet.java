package gg.acai.aurora.sets;

/**
 * @author Clouke
 * @since 01.03.2023 15:44
 * © Aurora - All Rights Reserved
 */
public class FixedSizeDataSet extends AbstractDataSet {

  private final int maxSize;

  public FixedSizeDataSet(int maxSize) {
    super.inputs = new double[maxSize][];
    super.targets = new double[maxSize][];
    this.maxSize = maxSize;
  }

  @Override
  public void add(double[][] input, double[][] target) {
    if (size() >= maxSize) {
      // evict the oldest data
      System.arraycopy(super.inputs, input.length, super.inputs, 0, super.inputs.length - input.length);
      System.arraycopy(super.targets, target.length, super.targets, 0, super.targets.length - target.length);

    } else {
      // add to the end
      System.arraycopy(input, 0, super.inputs, size(), input.length);
      System.arraycopy(target, 0, super.targets, size(), target.length);
    }
  }

  public int maxSize() {
    return maxSize;
  }

}
