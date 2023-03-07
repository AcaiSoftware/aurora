package gg.acai.aurora.ml.nn.extension;

import javax.annotation.Nullable;

/**
 * @author Clouke
 * @since 09.02.2023 17:00
 * © Acava - All Rights Reserved
 */
public final class TrainingTickEvent {

    private final int epoch;
    private final double max;
    private final double percentageComplete;
    private final String progress;
    private final double secondsLeft;
    private final boolean finished;

    public TrainingTickEvent(int epoch, double max, double percentageComplete, String progress, double secondsLeft) {
        this.epoch = epoch;
        this.max = max;
        this.finished = epoch == max;
        this.percentageComplete = percentageComplete;
        this.progress = progress;
        this.secondsLeft = secondsLeft;
    }

    public int getEpoch() {
        return epoch;
    }

    public double getMax() {
        return max;
    }

    public boolean isFinished() {
        return finished;
    }

    public double getPercentageComplete() {
        return percentageComplete;
    }

    @Nullable
    public String getProgress() {
        return progress;
    }

    public double getEstimatedTimeLeft() {
        return secondsLeft;
    }

}
