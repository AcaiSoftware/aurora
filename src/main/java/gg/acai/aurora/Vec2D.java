package gg.acai.aurora;

import gg.acai.aurora.image.DrawVectorImage;
import gg.acai.aurora.image.Image;

/**
 * Vec2D is a 2 dimensional Vector, holding two arrays of doubles, x and y.
 * The class provides methods for accessing individual elements of the arrays, as well as methods for reversing the order of the elements in the arrays.
 * It also provides a static factory method for creating instances of the class and a method for creating an image representation of the data.
 *
 * @author Clouke
 * @since 24.01.2023 12:45
 * © Acai - All Rights Reserved
 */
public class Vec2D implements Graph {

    private final double[] x;
    private final double[] y;

    /**
     * Factory method for creating instances of the class.
     * @param x the x-coordinates of the vector
     * @param y the y-coordinates of the vector
     * @return an instance of Vec2D with the given x and y coordinates
     */
    public static Vec2D of(double[] x, double[] y) {
        return new Vec2D(x, y);
    }

    /**
     * Constructor for creating an instance of the class with the given x and y coordinates.
     * @param x the x-coordinates of the vector
     * @param y the y-coordinates of the vector
     */
    public Vec2D(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate at the specified row.
     * @param row the row index
     * @return the x-coordinate at the specified row
     * @throws IllegalArgumentException if the row index is out of bounds
     */
    public double x_row(int row) {
        if (row < 0 || row >= x.length)
            throw new IllegalArgumentException("Row out of bounds, row: " + row + ", length: " + x.length);
        return x[row];
    }

    /**
     * Returns the y-coordinate at the specified row.
     * @param row the row index
     * @return the y-coordinate at the specified row
     * @throws IllegalArgumentException if the row index is out of bounds
     */
    public double y_row(int row) {
        if (row < 0 || row >= y.length)
            throw new IllegalArgumentException("Row out of bounds, row: " + row + ", length: " + y.length);
        return y[row];
    }

    /**
     * Returns the x-coordinates array.
     * @return the x-coordinates array
     */
    public double[] x() {
        return x;
    }

    /**
     * Returns the y-coordinates array.
     * @return the y-coordinates array
     */
    public double[] y() {
        return y;
    }

    /**
     * Creates and returns a copy of the Vec2D object.
     * @return a copy of the Vec2D object
     */
    @SuppressWarnings("all")
    public Vec2D clone() {
        return new Vec2D(x.clone(), y.clone());
    }

    /**
     * Reverses the order of the elements in the x-coordinates array.
     */
    public void revertX() {
        for (int i = 0; i < x.length / 2; i++) {
            double temp = x[i];
            x[i] = x[x.length - i - 1];
            x[x.length - i - 1] = temp;
        }
    }

    /**
     * Reverses the order of the elements in the y-coordinates array.
     */
    public void revertY() {
        for (int i = 0; i < y.length / 2; i++) {
            double temp = y[i];
            y[i] = y[y.length - i - 1];
            y[y.length - i - 1] = temp;
        }
    }

    public double dot(Vec2D other) {
        if (x.length != other.x.length || y.length != other.y.length)
            throw new IllegalArgumentException("Vectors must have the same length");
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * other.x[i];
            sum += y[i] * other.y[i];
        }
        return sum;
    }

    public double normalize() {
        if (x.length != y.length)
            throw new IllegalArgumentException("x and y arrays must be of equal length");

        double max = 0;
        for (int i = 0; i < x.length; i++) {
            double length = Math.sqrt(x[i] * x[i] + y[i] * y[i]);
            if (length > max) max = length;
        }
        for (int i = 0; i < x.length; i++) {
            x[i] /= max;
            y[i] /= max;
        }
        return max;
    }

    /**
     * Passes the x and y coordinates to the given image object.
     * @return the image object
     */
    @Override
    public Image toImage() {
        return new DrawVectorImage(this);
    }

}
