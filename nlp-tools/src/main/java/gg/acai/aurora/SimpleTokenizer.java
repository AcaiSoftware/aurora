package gg.acai.aurora;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clouke
 * @since 03.05.2023 12:59
 * © Aurora - All Rights Reserved
 */
public class SimpleTokenizer implements Tokenizer {

  private static SimpleTokenizer INSTANCE;
  private final char[] abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private final char[] numbers = "0123456789".toCharArray();

  public SimpleTokenizer() {
    if (INSTANCE != null) {
      throw new IllegalStateException("Singleton class");
    }
    INSTANCE = this;
  }

  @Override
  public List<String> tokenize(String text) {
    List<String> out = new ArrayList<>();
    String[] split = text.split(" ");
    for (String s : split) {
      StringBuilder sb = new StringBuilder();
      for (char c : s.toCharArray()) {
        for (char alphabet : abc) {
          if (c == alphabet) sb.append(c);
        }
        for (char number : numbers) {
          if (c == number) sb.append(c);
        }
      }
      out.add(sb.toString());
    }
    return out;
  }

  public static void main(String[] args) {
    System.out.println(SimpleTokenizer.instance().tokenize("Hello, World!"));
  }

  public static SimpleTokenizer instance() {
    if (INSTANCE == null) new SimpleTokenizer();
    return INSTANCE;
  }
}
