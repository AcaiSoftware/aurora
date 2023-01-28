package gg.acai.aurora.evaluation.lr;

import gg.acai.aurora.Vec2D;
import gg.acai.aurora.evaluation.Evaluator;
import gg.acai.aurora.evaluation.EvaluationContext;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Clouke
 * @since 24.01.2023 12:44
 * © Acai - All Rights Reserved
 */
public final class LR implements Evaluator {

    /**
     * Function that performs the linear regression calculation on a given Vec2D
     * object and returns an array of the slope and y-intercept
     */
    private static final Function<Vec2D, Double[]> LR = (vec) -> {
        double[] x = vec.x();
        double[] y = vec.y();

        if (x.length != y.length)
            throw new IllegalArgumentException("The length of the x and y arrays must be equal");

        int num = x.length;
        double sx = 0, sy = 0, sx2 = 0, sxy = 0;
        for (int i = 0; i < num; i++) {
            sx += x[i];
            sy += y[i];
            sx2 += x[i] * x[i];
            sxy += x[i] * y[i];
        }
        double ybar = sy / num;

        double slope = (num * sxy - sx * sy) / (num * sx2 - sx * sx);
        double intercept = (sy - slope * sx) / num;

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (double d : x) {
            if (d < min) min = d;
            if (d > max) max = d;
        }
        double median = (min + max) / 2;

        double yybar = 0.0;
        for (int i = 0; i < num; i++)
            yybar += (y[i] - ybar) * (y[i] - ybar);

        double ssr = 0.0;
        for (double v : x) {
            double fit = slope * v + intercept;
            ssr += (fit - ybar) * (fit - ybar);
        }

        double r2 = ssr / yybar;
        return new Double[]{slope, intercept, min, max, median, r2};
    };

    public static Function<Vec2D, Double[]> LRF() {
        return LR;
    }

    /**
     * Perform the linear regression calculation on a given Vec2D object and returns a LRResult object
     *
     * @param vector - The Vec2D object containing the independent and dependent variables
     * @return Returns a new {@link LRResult} with the slope and y-intercept of the best-fit line
     */
    public LRResult perform(Vec2D vector) {
        Double[] r = LR.apply(vector);
        return new LRResult(r[0], r[1], r[2], r[3], r[4], r[5]);
    }

    public LRResult perform(Supplier<Vec2D> vector) {
        return perform(vector.get());
    }

    @Override
    public EvaluationContext getContext() {
        return EvaluationContext.LINEAR_REGRESSION;
    }
}
