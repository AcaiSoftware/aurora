package gg.acai.aurora.core.sets;

import gg.acai.acava.Requisites;
import gg.acai.aurora.GsonSpec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Clouke
 * @since 28.02.2023 13:52
 * © Aurora - All Rights Reserved
 */
public class DataSetBuilder {

    private int maxSize = -1;
    private double[][] immutableInputs;
    private double[][] immutableTargets;
    private DataSet imported;

    public DataSetBuilder maxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public DataSetBuilder immutable(double[][] inputs, double[][] targets) {
        this.immutableInputs = inputs;
        this.immutableTargets = targets;
        return this;
    }

    public DataSetBuilder importFrom(File file) {
        Requisites.requireNonNull(file, "file");
        FileReader reader;
        try {
            reader = new FileReader(file);
            imported = GsonSpec.standard().fromJson(reader, ImmutableDataSet.class);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + file.getAbsolutePath());
        }
        return this;
    }

    public DataSetBuilder importFrom(String json) {
        Requisites.requireNonNull(json, "json");
        imported = GsonSpec.standard().fromJson(json, ImmutableDataSet.class);
        return this;
    }

    public DataSet build() {
        int r = buildProcedureResult();
        switch (r) {
            case 1: return imported;
            case 2: return new ImmutableDataSet(immutableInputs, immutableTargets);
            case 3: return new FixedSizeDataSet(maxSize);
            default:
                return new LocalDataSet();
        }
    }

    private int buildProcedureResult() {
        if (imported != null) return 1;
        if (immutableInputs != null && immutableTargets != null) return 2;
        if (maxSize != -1) return 3;
        return 0;
    }

}
