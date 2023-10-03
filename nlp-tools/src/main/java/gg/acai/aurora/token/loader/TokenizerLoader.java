package gg.acai.aurora.token.loader;

import gg.acai.aurora.exception.InvalidModelException;
import gg.acai.aurora.token.Tokenizer;

import java.io.File;

/**
 * @author Clouke
 * @since 05.07.2023 00:00
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface TokenizerLoader {
  <T extends Tokenizer> T load(File file, Class<T> rawType) throws InvalidModelException;
}
