package gg.acai.aurora.ml.nn;

/**
 * @author Clouke
 * @since 02.03.2023 13:09
 * © Aurora - All Rights Reserved
 */
public final class NetworkBuilder { // TODO: Rename to factory

    public static TrainingBuilder training() {
        return new TrainingBuilder();
    }

    public static ModelBuilder model() {
        return new ModelBuilder();
    }
}