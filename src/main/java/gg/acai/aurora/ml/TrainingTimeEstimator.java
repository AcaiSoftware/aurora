package gg.acai.aurora.ml;

import gg.acai.aurora.TimeEstimator;

/**
 * @author Clouke
 * @author Jonathan
 * @since 09.02.2023 17:10
 * © Acava - All Rights Reserved
 */
public final class TrainingTimeEstimator implements TimeEstimator<Integer> {

    private long last = -1;
    private final int maxEpochs;
    private double average = -1.0;

    public TrainingTimeEstimator(int maxEpochs) {
        this.maxEpochs = maxEpochs;
    }

    @Override
    public void tick() {
        last = System.nanoTime();
    }

    @Override
    public double estimated(Integer currentEpoch) {
        if (last == -1) return 0.0;

        long now = System.nanoTime();
        long diff = now - last;

        double latest = (diff * (maxEpochs - currentEpoch)) / 1e9;
        average = average == -1.0 ? latest : average * (5.0 / 6.0) + latest * (1.0 / 6.0);

        return Math.round(average * 10.0) / 10.0;
    }
}