package gg.acai.aurora.token;

import com.google.gson.Gson;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.model.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Clouke
 * @since 05.07.2023 00:26
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractTokenizerModel implements Tokenizer {

  protected final Map<String, Integer> indices;

  public AbstractTokenizerModel(Map<String, Integer> indices) {
    this.indices = indices;
  }

  public AbstractTokenizerModel() {
    this(new HashMap<>());
  }

  private String name = "tokenizer";
  private String saveDirectory;
  private transient boolean saveOnClose = false;

  @Override
  public String serialize() {
    Gson gson = GsonSpec.standard();
    return gson.toJson(indices);
  }

  @Override
  public Model name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Model saveDirectoryPath(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  @Override
  public Model saveOnClose(boolean saveOnClose) {
    this.saveOnClose = saveOnClose;
    return this;
  }

  @Override
  public String version() {
    return "1.0.0";
  }

  @Override
  public String saveDirectoryPath() {
    return saveDirectory;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public int size() {
    return indices.size();
  }

  @Override
  public void close() {
    if (saveOnClose) save();
  }

  @Override
  public Map<String, Integer> indices() {
    return indices;
  }

  public void add(Map<String, Integer> indices) {
    this.indices.putAll(indices);
  }
}
