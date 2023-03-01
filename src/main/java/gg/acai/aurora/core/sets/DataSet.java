package gg.acai.aurora.core.sets;

import gg.acai.acava.io.Closeable;
import gg.acai.aurora.core.Serializer;

/**
 * @author Clouke
 * @since 28.02.2023 13:54
 * © Aurora - All Rights Reserved
 */
public interface DataSet extends Serializer, Closeable {

    double[][] inputs();

    double[][] targets();

    void add(double[][] input, double[][] target);

    @Override
    void close();

    int size();

}
