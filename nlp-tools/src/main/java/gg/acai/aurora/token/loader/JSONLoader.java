package gg.acai.aurora.token.loader;

import com.google.gson.Gson;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.exception.InvalidModelException;
import gg.acai.aurora.token.Tokenizer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Clouke
 * @since 05.07.2023 00:07
 * © Aurora - All Rights Reserved
 */
public class JSONLoader implements TokenizerLoader {
  @Override
  public <T extends Tokenizer> T load(File file, Class<T> rawType) throws InvalidModelException {
    T tokenizer;
    Gson gson = GsonSpec.standard();
    try (FileReader reader = new FileReader(file)) {
      tokenizer = gson.fromJson(reader, rawType);
    } catch (IOException e) {
      throw new InvalidModelException("Could not load tokenizer from file " + file.getName(), e);
    }
    return tokenizer;
  }
}
