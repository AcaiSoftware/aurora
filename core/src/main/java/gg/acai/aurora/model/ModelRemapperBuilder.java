package gg.acai.aurora.model;

/**
 * @author Clouke
 * @since 16.04.2023 15:44
 * © Aurora - All Rights Reserved
 */
public class ModelRemapperBuilder {

  private String name;
  private String saveDirectory;
  private boolean saveOnClose = true;
  private boolean ignoreVersion = false;

  public ModelRemapperBuilder name(String name) {
    this.name = name;
    return this;
  }

  public ModelRemapperBuilder saveDirectory(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  public ModelRemapperBuilder enableAutoSave() {
    this.saveOnClose = false;
    return this;
  }

  public ModelRemapperBuilder ignoreVersion() {
    this.ignoreVersion = true;
    return this;
  }

  public ModelRemapper build() {
    return new ModelRemapper(name, saveDirectory, saveOnClose, ignoreVersion);
  }
}