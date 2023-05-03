package gg.acai.aurora.sets;

import gg.acai.acava.io.Closeable;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.Serializer;

/**
 * @author Clouke
 * @since 28.02.2023 13:54
 * © Aurora - All Rights Reserved
 */
public class DataSet implements Serializer, Closeable {

  private double[][] inputs;
  private double[][] targets;

  public static DataSet deserialize(String json) {
    return GsonSpec.standard().fromJson(json, DataSet.class);
  }

  public DataSet() {
    this.inputs = new double[0][];
    this.targets = new double[0][];
  }

  public double[][] inputs() {
    return inputs;
  }

  public double[][] targets() {
    return targets;
  }

  private void resize(int newSize) {
    double[][] newInputs = new double[newSize][];
    double[][] newTargets = new double[newSize][];

    System.arraycopy(inputs, 0, newInputs, 0, inputs.length);
    System.arraycopy(targets, 0, newTargets, 0, targets.length);

    inputs = newInputs;
    targets = newTargets;
  }

  public void add(double[][] input, double[][] target) {
    if (input.length != target.length)
      throw new IllegalArgumentException("Inputs and targets must be the same size");

    int size = inputs.length;
    int newSize = size + input.length;

    if (newSize > size) {
      resize(newSize);
    }

    System.arraycopy(input, 0, inputs, size, input.length);
    System.arraycopy(target, 0, targets, size, target.length);
  }

  public void add(double[][] input, boolean[][] target) {
    if (input.length != target.length)
      throw new IllegalArgumentException("Inputs and targets must be the same size");

    double[][] newTarget = new double[target.length][target[0].length];
    for (int i = 0; i < target.length; i++) {
      for (int j = 0; j < target[i].length; j++) {
        newTarget[i][j] = target[i][j] ? 1 : 0;
      }
    }

    int size = inputs.length;
    int newSize = size + input.length;

    if (newSize > size) {
      resize(newSize);
    }

    System.arraycopy(input, 0, inputs, size, input.length);
    System.arraycopy(newTarget, 0, targets, size, newTarget.length);
  }

  @Override
  public String serialize() {
    return GsonSpec.standard().toJson(this);
  }

  @Override
  public void close() {
    synchronized (this) {
      for (int i = 0; i < inputs.length; i++) {
        inputs[i] = null;
        targets[i] = null;
      }
      inputs = null;
      targets = null;
    }
  }

  public int size() {
    return inputs.length;
  }

}
