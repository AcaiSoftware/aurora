package gg.acai.aurora;

import javax.annotation.Nonnull;

/**
 * @author Clouke
 * @since 29.01.2023 03:55
 * © Acava - All Rights Reserved
 */
@FunctionalInterface
public interface Serializer {

  /**
   * Serializes this object.
   *
   * @return Returns the serialized object.
   */
  String serialize();

  /**
   * Attempts to serialize the given object.
   *
   * @param t The object to serialize.
   * @param <T> The type of the object to serialize.
   * @return Returns the serialized object, or null if it could not be serialized.
   */
  static <T> String trySerialize(@Nonnull T t) {
    if (t instanceof Serializer) {
      return ((Serializer) t).serialize();
    }

    String s = t.toString();
    // We need to check for default toString() implementation and return null if it is the case.
    return s.startsWith(t.getClass().getName() + "@") && s.contains("0x") ? null : s;
  }

}
