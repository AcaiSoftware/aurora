package gg.acai.aurora;

import gg.acai.acava.io.Closeable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Clouke
 * @since 23.04.2023 06:34
 * © Aurora - All Rights Reserved
 */
@SuppressWarnings("unchecked")
public class Attribute implements Closeable {

  private final Map<String, Object> attributes;

  public Attribute(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public Attribute() {
    this(new HashMap<>());
  }

  public Attribute set(String key, Object value) {
    attributes.put(key, value);
    return this;
  }

  public <T> T get(String key) {
    return (T) attributes.get(key);
  }

  public <T> T get(String key, T def) {
    return (T) attributes.getOrDefault(key, def);
  }

  public void remove(String key) {
    attributes.remove(key);
  }

  public Map<String, Object> asMap() {
    return attributes;
  }

  @Override
  public void close() {
    attributes.clear();
  }
}
