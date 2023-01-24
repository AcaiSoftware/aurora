package gg.acai.aurora;

/**
 * @author Clouke
 * @since 24.01.2023 12:33
 * © Acava - All Rights Reserved
 */
public final class EuclideanDistance {

    /**
     * Calculates the euclidean distance between two double arrays.
     *
     * @param a - first double array
     * @param b - second double array
     * @return double value representing the distance between the two arrays
     */
    public static double distance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += Math.pow(a[i] - b[i], 2);
        return Math.sqrt(sum);
    }

    /**
     * Calculates the euclidean distance between two double arrays, with weights applied to each element.
     *
     * @param a - first double array
     * @param b - second double array
     * @param weights - weights to be applied to each element when calculating the distance
     * @return double value representing the distance between the two arrays
     */
    public static double distance(double[] a, double[] b, double[] weights) {
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += Math.pow(a[i] - b[i], 2) * weights[i];
        return Math.sqrt(sum);
    }

    /**
     * Calculates the euclidean distance between two double arrays, with weights and biases applied to each element.
     *
     * @param a - first double array
     * @param b - second double array
     * @param weights - weights to be applied to each element when calculating the distance
     * @param biases - biases to be applied to each element when calculating the distance
     * @return double value representing the distance between the two arrays
     */
    public static double distance(double[] a, double[] b, double[] weights, double[] biases) {
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += Math.pow(a[i] - b[i], 2) * weights[i] + biases[i];
        return Math.sqrt(sum);
    }

    /**
     * Finds the closest point in a 2D double array to a given 1D double array.
     *
     * @param a - given 1D double array
     * @param b - 2D double array to find the closest point in
     * @return int value representing the index of the closest point in the 2D array
     */
    public static int closestPoint(double[] a, double[][] b) {
        double min = Double.MAX_VALUE;
        int idx = -1;
        for (int i = 0; i < b.length; i++) {
            double dist = distance(a, b[i]);
            if (dist < min) {
                min = dist;
                idx = i;
            }
        }
        return idx;
    }

}
