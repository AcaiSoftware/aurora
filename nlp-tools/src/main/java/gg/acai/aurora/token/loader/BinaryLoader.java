package gg.acai.aurora.token.loader;

import com.google.gson.Gson;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.exception.InvalidModelException;
import gg.acai.aurora.publics.io.bin.BinaryFileIO;
import gg.acai.aurora.token.Tokenizer;

import java.io.File;
import java.io.IOException;

/**
 * @author Clouke
 * @since 05.07.2023 00:08
 * © Aurora - All Rights Reserved
 */
public class BinaryLoader implements TokenizerLoader {
  @Override
  public <T extends Tokenizer> T load(File file, Class<T> rawType) throws InvalidModelException {
    String json;
    Gson gson = GsonSpec.standard();
    try {
      json = BinaryFileIO.loadFromBin(file.getAbsolutePath());
    } catch (IOException e) {
      throw new InvalidModelException("Could not load tokenizer from file " + file.getName(), e);
    }
    return gson.fromJson(json, rawType);
  }
}
