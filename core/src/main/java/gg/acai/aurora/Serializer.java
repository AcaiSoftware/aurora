package gg.acai.aurora;

import javax.annotation.Nonnull;

/**
 * @author Clouke
 * @since 29.01.2023 03:55
 * © Acava - All Rights Reserved
 */
@FunctionalInterface
public interface Serializer {

  String serialize();

  static <T> String trySerialize(@Nonnull T t) {
    if (t instanceof Serializer) {
      return ((Serializer) t).serialize();
    }
    String s = t.toString();
    return s.startsWith(t.getClass().getName() + "@") && s.contains("0x") ? null : s;
  }

}
