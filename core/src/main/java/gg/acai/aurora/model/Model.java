package gg.acai.aurora.model;

import gg.acai.acava.io.Closeable;
import gg.acai.aurora.SavePoint;
import gg.acai.aurora.Serializer;
import gg.acai.aurora.exception.ModelStorageException;

import java.util.Optional;

/**
 * <p><b>Model - A model that can be saved, loaded, deployed, and evaluated.</b></p>
 *
 * <p> The model interface is the base interface for all models,
 * providing the basic functionality of saving, loading, deploying, and evaluating models.
 *
 * @see ModelLoader Loading models
 * @see ModelArchive Saving models
 *
 * @author Clouke
 * @since 02.04.2023 22:43
 * © Aurora - All Rights Reserved
 */
public interface Model extends Serializer, SavePoint, Closeable {

  /**
   * Applies the name to this model.
   *
   * @param name The name to apply.
   * @return Returns this model for chaining.
   */
  Model name(String name);

  /**
   * Applies the save directory to this model.
   *
   * @param saveDirectory The save directory to apply.
   * @return Returns this model for chaining.
   */
  Model saveDirectoryPath(String saveDirectory);

  /**
   * Applies rather this model should be saved on close.
   *
   * @param saveOnClose The save on close to apply.
   * @return Returns this model for chaining.
   */
  Model saveOnClose(boolean saveOnClose);

  /**
   * Gets the version of this model.
   *
   * @return Returns the version of this model.
   */
  String version();

  /**
   * Gets the save directory of this model.
   *
   * @return Returns the save directory of this model.
   */
  String saveDirectoryPath();

  /**
   * Gets the name of this model.
   *
   * @return Returns the name of this model.
   */
  String name();

  /**
   * Gets the name of this model as an optional.
   *
   * @return Returns the name of this model as an optional.
   */
  default Optional<String> nameOpt() {
    return Optional.ofNullable(name());
  }

  /**
   * Saves this model.
   *
   * @throws ModelStorageException If an error occurs while saving this model.
   */
  @Override
  default void save() throws ModelStorageException {
    ModelArchive.saveModel(this, e -> {
      throw new ModelStorageException(e);
    });
  }

  /**
   * Saves this model to the specified save directory.
   *
   * @param saveDirectory The save directory to save this model to.
   * @throws ModelStorageException If an error occurs while saving this model.
   */
  default void save(String saveDirectory) throws ModelStorageException {
    saveDirectoryPath(saveDirectory);
    save();
  }

  /**
   * Saves this model to a compressed .bin file.
   *
   * @throws ModelStorageException If an error occurs while saving this model.
   */
  default void saveBinary() throws ModelStorageException {
    ModelArchive.saveBinaryModel(this, e -> {
      throw new ModelStorageException(e);
    });
  }

  /**
   * Saves this model to a compressed .bin file to the specified save directory.
   *
   * @param saveDirectory The save directory to save this model to.
   * @throws ModelStorageException If an error occurs while saving this model.
   */
  default void saveBinary(String saveDirectory) throws ModelStorageException {
    saveDirectoryPath(saveDirectory);
    saveBinary();
  }

}
