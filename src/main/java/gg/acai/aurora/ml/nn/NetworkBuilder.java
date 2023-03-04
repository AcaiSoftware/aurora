package gg.acai.aurora.ml.nn;

import gg.acai.aurora.ml.nn.AbstractNetworkBuilder;
import gg.acai.aurora.ml.nn.ModelBuilder;
import gg.acai.aurora.ml.nn.TrainingBuilder;

/**
 * @author Clouke
 * @since 02.03.2023 13:09
 * © Aurora - All Rights Reserved
 */
public class NetworkBuilder extends AbstractNetworkBuilder {

    public static TrainingBuilder training() {
        return new TrainingBuilder();
    }

    public static ModelBuilder model() {
        return new ModelBuilder();
    }
}