package gg.acai.aurora.token.loader;

import gg.acai.aurora.exception.InvalidModelException;
import gg.acai.aurora.token.Tokenizer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Clouke
 * @since 05.07.2023 00:01
 * © Aurora - All Rights Reserved
 */
public class TokenizerLoaderService implements TokenizerLoader {

  private static final Map<String, TokenizerLoader> LOADERS;

  static {
    LOADERS = new HashMap<>();
    BinaryLoader bin = new BinaryLoader();
    LOADERS.put("json", new JSONLoader());
    LOADERS.put("model", bin);
    LOADERS.put("bin", bin);
  }

  @Override
  public <T extends Tokenizer> T load(File file, Class<T> rawType) {
    String name = file.getName();
    String type = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
    Optional<TokenizerLoader> loader = Optional.ofNullable(LOADERS.get(type));

    return loader
      .orElseThrow(() -> new InvalidModelException(
        "Could not load tokenizer from file " + name + " (supported file types: .json, .bin, .model)"
      ))
      .load(file, rawType);
  }
}
