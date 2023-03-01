package gg.acai.aurora.core.sets;

import com.google.gson.Gson;

/**
 * @author Clouke
 * @since 28.02.2023 13:58
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractDataSet implements DataSet {

    protected static final Gson GSON = new Gson();
    private static final int INITIAL_SIZE = 16;

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

    @Override @SuppressWarnings("all")
    public void add(double[][] input, double[][] target) {
        if (input.length != target.length)
            throw new IllegalArgumentException("Inputs and targets must be the same size");

        synchronized (inputs) {
            synchronized (targets) {
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
        return GSON.toJson(this);
    }

    @Override
    public int size() {
        return inputs.length;
    }
}
