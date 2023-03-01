package gg.acai.aurora.core.sets;

/**
 * @author Clouke
 * @since 28.02.2023 13:52
 * © Aurora - All Rights Reserved
 */
public class DataSetBuilder {

    private int maxSize = -1;
    private double[][] immutableInputs;
    private double[][] immutableTargets;

    public DataSetBuilder maxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public DataSetBuilder immutable(double[][] inputs, double[][] targets) {
        this.immutableInputs = inputs;
        this.immutableTargets = targets;
        return this;
    }

    public DataSet build() {
        if (immutableInputs != null && immutableTargets != null)
            return new ImmutableDataSet(immutableInputs, immutableTargets);
        if (maxSize != -1)
            return new FixedSizeDataSet(maxSize);
        return new LocalDataSet();
    }

}
