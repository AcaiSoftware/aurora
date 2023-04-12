package gg.acai.aurora.ml;

import gg.acai.acava.io.Closeable;
import gg.acai.aurora.SavePoint;
import gg.acai.aurora.Serializer;

/**
 * @author Clouke
 * @since 02.04.2023 22:43
 * © Aurora - All Rights Reserved
 */
public interface Model extends Serializer, SavePoint, Closeable {

  Model setModel(String name);

  Model setSaveDirectory(String saveDirectory);

  Model setSaveOnClose(boolean saveOnClose);

  String getVersion();

}
