package gg.acai.aurora.model;

import gg.acai.acava.io.Closeable;
import gg.acai.aurora.SavePoint;
import gg.acai.aurora.Serializer;
import gg.acai.aurora.exception.ModelStorageException;

import java.util.Optional;

/**
 * @author Clouke
 * @since 02.04.2023 22:43
 * © Aurora - All Rights Reserved
 */
public interface Model extends Serializer, SavePoint, Closeable {

  Model name(String name);

  Model saveDirectoryPath(String saveDirectory);

  Model saveOnClose(boolean saveOnClose);

  String version();

  String saveDirectoryPath();

  String name();

  default Optional<String> nameOpt() {
    return Optional.ofNullable(name());
  }

  @Override
  default void save() throws ModelStorageException {
    ModelArchive.saveModel(this, e -> {
      throw new ModelStorageException(e);
    });
  }

  default void save(String saveDirectory) throws ModelStorageException {
    saveDirectoryPath(saveDirectory);
    save();
  }

}
