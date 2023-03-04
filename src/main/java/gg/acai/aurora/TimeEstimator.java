package gg.acai.aurora;

/**
 * @author Clouke
 * @since 10.02.2023 21:20
 * © Acava - All Rights Reserved
 */
public interface TimeEstimator<T> {

    void tick();

    double estimated(T t);

}
