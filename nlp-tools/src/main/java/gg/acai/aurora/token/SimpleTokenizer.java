package gg.acai.aurora.token;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Clouke
 * @since 05.07.2023 00:32
 * © Aurora - All Rights Reserved
 */
public class SimpleTokenizer extends AbstractTokenizerModel {

  @Override
  public Collection<String> tokenize(String text) {
    return Arrays.asList(text.split(" "));
  }

  @Override
  public int indexOf(String token) {
    throw new UnsupportedOperationException("Indexing is not supported by this tokenizer");
  }

  @Override
  public String wordOf(int index) {
    throw new UnsupportedOperationException("Indexing is not supported by this tokenizer");
  }

  @Override
  public void fit(Iterable<String> corpus) {
    throw new UnsupportedOperationException("Fitting is not supported by this tokenizer");
  }

  @Override
  public long countCharacters() {
    return -1L;
  }
}
