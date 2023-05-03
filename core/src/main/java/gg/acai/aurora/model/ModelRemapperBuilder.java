package gg.acai.aurora.model;

/**
 * Builder class for {@link ModelRemapper}.
 *
 * @author Clouke
 * @since 16.04.2023 15:44
 * © Aurora - All Rights Reserved
 */
public class ModelRemapperBuilder {

  private String name;
  private String saveDirectory;
  private boolean saveOnClose = true;
  private boolean ignoreVersion = false;

  /**
   * Applies the remapper name to change the name of the model.
   *
   * @param name The name of the model
   * @return Returns this builder for chaining.
   */
  public ModelRemapperBuilder name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Applies the save directory to change the save directory of the model.
   *
   * @param saveDirectory The save directory of the model
   * @return Returns this builder for chaining.
   */
  public ModelRemapperBuilder saveDirectory(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  /**
   * Enables auto saving on close.
   *
   * @return Returns this builder for chaining.
   */
  public ModelRemapperBuilder enableAutoSave() {
    this.saveOnClose = false;
    return this;
  }

  /**
   * Ignores version control to check if the model is up-to-date.
   *
   * @return Returns this builder for chaining.
   */
  public ModelRemapperBuilder ignoreVersion() {
    this.ignoreVersion = true;
    return this;
  }

  /**
   * Builds the model remapper.
   *
   * @return Returns the model remapper.
   */
  public ModelRemapper build() {
    return new ModelRemapper(name, saveDirectory, saveOnClose, ignoreVersion);
  }
}