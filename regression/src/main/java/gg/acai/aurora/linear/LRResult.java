package gg.acai.aurora.linear;

import gg.acai.aurora.Display;
import gg.acai.aurora.image.Image;

import java.util.function.Consumer;
import java.util.function.DoubleUnaryOperator;

/**
 * @author Clouke
 * @since 24.01.2023 13:18
 * © Acai - All Rights Reserved
 */
public class LRResult implements Display {

    /**
     * The slope of the best-fit line
     */
    private final double slope;

    /**
     * The y-intercept of the best-fit line
     */
    private final double intercept;

    /**
     * The minimum x-value of the data
     */
    private final double min;

    /**
     * The maximum x-value of the data
     */
    private final double max;

    /**
     * The median x-value of the data
     */
    private final double median;

    /**
     * The mean r2-value of the data
     */
    private final double r2;

    public LRResult(double slope, double intercept, double min, double max, double median, double rSquared) {
        this.slope = slope;
        this.intercept = intercept;
        this.min = min;
        this.max = max;
        this.median = median;
        this.r2 = rSquared;
    }

    /**
     * @return Returns the slope of the linear regression line.
     */
    public double slope() {
        return slope;
    }

    /**
     * @return Returns the intercept of the linear regression line.
     */
    public double intercept() {
        return intercept;
    }

    /**
     * @return Returns the minimum x-value of the data.
     */
    public double min() {
        return min;
    }

    /**
     * @return Returns the maximum x-value of the data.
     */
    public double max() {
        return max;
    }

    /**
     * @return Returns the median x-value of the data.
     */
    public double median() {
        return median;
    }

    /**
     * @return Returns the r-squared value of the linear regression line.
     */
    public double rSquared() {
        return r2;
    }

    public void then(Consumer<LRResult> action) {
        action.accept(this);
    }

    public double predict(double x) {
        return slope * x + intercept;
    }

    public DoubleUnaryOperator predict() {
        return x -> slope * x + intercept;
    }

    /**
     * Creates an image of the linear regression line.
     *
     * @return Returns an image of the linear regression line.
     */
    @Override
    public Image toImage() {
        return new LRImage(this);
    }

}
