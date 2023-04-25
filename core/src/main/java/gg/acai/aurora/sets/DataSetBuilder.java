package gg.acai.aurora.sets;

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
  private DataSet imported;

  public DataSetBuilder maxSize(int maxSize) {
    this.maxSize = maxSize;
    return this;
  }

  public DataSet build() {
    int r = buildProcedureResult();
    switch (r) {
      case 1: return imported;
      case 2: return new FixedSizeDataSet(maxSize);
      default: return new LocalDataSet();
    }
  }

  private int buildProcedureResult() {
    if (imported != null) return 1;
    if (maxSize != -1) return 2;
    return 0;
  }

}
