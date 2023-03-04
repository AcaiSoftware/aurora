package gg.acai.aurora.sets;

import gg.acai.acava.io.Closeable;
import gg.acai.aurora.Serializer;

/**
 * @author Clouke
 * @since 28.02.2023 13:54
 * © Aurora - All Rights Reserved
 */
public interface DataSet extends Serializer, Closeable {

    static DataSetBuilder builder() {
        return new DataSetBuilder();
    }

    double[][] inputs();

    double[][] targets();

    void add(double[][] input, double[][] target);

    void add(double[][] input, boolean[][] target);

    @Override
    void close();

    int size();

}
