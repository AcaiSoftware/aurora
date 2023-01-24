package gg.acai.aurora;

import gg.acai.aurora.image.DrawVectorImage;
import gg.acai.aurora.image.Image;

/**
 * @author Clouke
 * @since 24.01.2023 12:45
 * © Acava - All Rights Reserved
 */
public class Vec2D implements Graph {

    private final double[] x;
    private final double[] y;

    public static Vec2D of(double[] x, double[] y) {
        return new Vec2D(x, y);
    }

    public Vec2D(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double x_row(int row) {
        if (row < 0 || row >= x.length)
            throw new IllegalArgumentException("Row out of bounds, row: " + row + ", length: " + x.length);
        return x[row];
    }

    public double y_row(int row) {
        if (row < 0 || row >= y.length)
            throw new IllegalArgumentException("Row out of bounds, row: " + row + ", length: " + y.length);
        return y[row];
    }

    public double[] x() {
        return x;
    }

    public double[] y() {
        return y;
    }

    @SuppressWarnings("all")
    public Vec2D clone() {
        return new Vec2D(x.clone(), y.clone());
    }

    public void revertX() {
        for (int i = 0; i < x.length / 2; i++) {
            double temp = x[i];
            x[i] = x[x.length - i - 1];
            x[x.length - i - 1] = temp;
        }
    }

    public void revertY() {
        for (int i = 0; i < y.length / 2; i++) {
            double temp = y[i];
            y[i] = y[y.length - i - 1];
            y[y.length - i - 1] = temp;
        }
    }

    @Override
    public Image toImage() {
        return new DrawVectorImage(this);
    }

}
