package gg.acai.aurora;

/**
 * @author Clouke
 * @since 16.04.2023 15:37
 * © Aurora - All Rights Reserved
 */
public class ModelLoaderOptions {

  private final String name;
  private final String saveDirectory;
  private final boolean saveOnClose;
  private final boolean ignoreVersion;

  public ModelLoaderOptions(String name, String saveDirectory, boolean saveOnClose, boolean ignoreVersion) {
    this.name = name;
    this.saveDirectory = saveDirectory;
    this.saveOnClose = saveOnClose;
    this.ignoreVersion = ignoreVersion;
  }

  public String name() {
    return name;
  }

  public String saveDirectory() {
    return saveDirectory;
  }

  public boolean saving() {
    return saveOnClose;
  }

  public boolean ignored() {
    return ignoreVersion;
  }

}
