package gg.acai.aurora.core.sets;

/**
 * @author Clouke
 * @since 01.03.2023 15:44
 * © Aurora - All Rights Reserved
 */
public class FixedSizeDataSet extends AbstractDataSet {

    private final int maxSize;

    public FixedSizeDataSet(int maxSize) {
        if (maxSize < 1)
            throw new IllegalArgumentException("Max size must be greater than 0");

        this.maxSize = maxSize;
    }

    @Override
    public void add(double[][] input, double[][] target) {
        if (size() >= maxSize) {
            inputs = shift(super.inputs, input);
            targets = shift(super.targets, target);
            return;
        }

        super.add(input, target);
    }

    private double[][] shift(double[][] array, double[][] toAdd) {
        double[][] newArray = new double[array.length][];
        System.arraycopy(array, toAdd.length, newArray, 0, array.length - toAdd.length);
        System.arraycopy(toAdd, 0, newArray, array.length - toAdd.length, toAdd.length);
        return newArray;
    }

    public int maxSize() {
        return maxSize;
    }

}
