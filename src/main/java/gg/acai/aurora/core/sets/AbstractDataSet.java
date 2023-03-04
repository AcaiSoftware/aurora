package gg.acai.aurora.core.sets;

import gg.acai.aurora.GsonSpec;

/**
 * @author Clouke
 * @since 28.02.2023 13:58
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractDataSet implements DataSet {

  private static final int INITIAL_SIZE = 16;
  private static final Object LOCK = new Object();

  protected double[][] inputs;
  protected double[][] targets;

  public AbstractDataSet(int baseSize) {
    this.inputs = new double[baseSize][];
    this.targets = new double[baseSize][];
  }

  public AbstractDataSet() {
    this(INITIAL_SIZE);
  }

  @Override
  public double[][] inputs() {
    return inputs;
  }

  @Override
  public double[][] targets() {
    return targets;
  }

  @Override
  public void add(double[][] input, double[][] target) {
    if (input.length != target.length)
      throw new IllegalArgumentException("Inputs and targets must be the same size");

    synchronized (LOCK) {
      int size = inputs.length;
      int newSize = size + input.length;

      if (newSize > size) {
        double[][] newInputs = new double[newSize][];
        double[][] newTargets = new double[newSize][];

        System.arraycopy(inputs, 0, newInputs, 0, size);
        System.arraycopy(targets, 0, newTargets, 0, size);

        inputs = newInputs;
        targets = newTargets;
      }

      System.arraycopy(input, 0, inputs, size, input.length);
      System.arraycopy(target, 0, targets, size, target.length);
    }
  }

  @Override
  public void add(double[][] input, boolean[][] target) {
    double[][] converted = new double[target.length][target[0].length];
    for (int i = 0; i < target.length; i++) {
      for (int j = 0; j < target[i].length; j++) {
        converted[i][j] = target[i][j] ? 1 : 0;
      }
    }
    add(input, converted);
  }

  @Override
  public final void close() {
    synchronized (this) {
      inputs = null;
      targets = null;
    }
  }

  @Override
  public String serialize() {
    return GsonSpec.standard().toJson(this);
  }

  @Override
  public int size() {
    return inputs.length;
  }
}
