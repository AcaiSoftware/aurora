package gg.acai.aurora.image;

import gg.acai.aurora.LRResult;
import gg.acai.aurora.TestModel;

/**
 * @author Clouke
 * @since 24.01.2023 19:32
 * © Acai - All Rights Reserved
 */
public class LRPredictionImage implements Image {

    private final LRResult lr;
    private final double prediction;

    public LRPredictionImage(LRResult lr, double prediction) {
        this.lr = lr;
        this.prediction = prediction;
    }

    @Override @Deprecated
    public String draw() {
        TestModel model = new TestModel();
        double[] x = model.x();
        double[] y = model.y();

        double slope = lr.slope();
        double intercept = lr.intercept();

        StringBuilder builder = new StringBuilder();
        for (int i = -10; i < 10; i++) {
            double y1 = slope * i + intercept;
            double y2 = slope * (i + 1) + intercept;

            for (int j = 0; j < x.length; j++) {
                if (x[j] == i && y[j] == y1) {
                    builder.append("o");
                } else if (x[j] == i + 1 && y[j] == y2) {
                    builder.append("o");
                } else {
                    builder.append(" ");
                }
            }
        }

        return builder.toString();
    }
}
