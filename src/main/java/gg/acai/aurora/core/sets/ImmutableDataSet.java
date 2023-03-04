package gg.acai.aurora.core.sets;

/**
 * @author Clouke
 * @since 28.02.2023 13:54
 * © Aurora - All Rights Reserved
 */
public class ImmutableDataSet extends AbstractDataSet {

  public ImmutableDataSet(double[][] inputs, double[][] targets) {
    super();
    super.inputs = inputs;
    super.targets = targets;
  }

  @Override
  @Deprecated
  public void add(double[][] input, double[][] target) {
    throw new UnsupportedOperationException("Cannot add to an immutable data set");
  }

}
