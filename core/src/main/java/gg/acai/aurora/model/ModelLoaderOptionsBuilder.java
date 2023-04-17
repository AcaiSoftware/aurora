package gg.acai.aurora.model;

/**
 * @author Clouke
 * @since 16.04.2023 15:44
 * © Aurora - All Rights Reserved
 */
public class ModelLoaderOptionsBuilder {

  private String name;
  private String saveDirectory;
  private boolean saveOnClose = true;
  private boolean ignoreVersion = false;

  public ModelLoaderOptionsBuilder name(String name) {
    this.name = name;
    return this;
  }

  public ModelLoaderOptionsBuilder saveDirectory(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  public ModelLoaderOptionsBuilder enableAutoSave() {
    this.saveOnClose = false;
    return this;
  }

  public ModelLoaderOptionsBuilder ignoreVersion() {
    this.ignoreVersion = true;
    return this;
  }

  public ModelLoaderOptions build() {
    return new ModelLoaderOptions(name, saveDirectory, saveOnClose, ignoreVersion);
  }
}