package gg.acai.aurora.publics;

import gg.acai.aurora.Aurora;

import java.util.Arrays;
import java.util.List;

/**
 * A versioning compatibility checker.
 *
 * @author Clouke
 * @since 03.03.2023 14:31
 * © Aurora - All Rights Reserved
 */
public final class Compatibility {

  private static final String[] IGNORED = {"ignore", "all"};

  /**
   * Checks if the given class is compatible with the current version.
   *
   * @param clazz the class to check
   * @return Returns {@code true} if the class is compatible
   */
  public static boolean isCompatible(Class<?> clazz) {
    String version = Aurora.version();
    Compat compat = clazz.getAnnotation(Compat.class);
    if (compat == null)
      return true;

    List<String> compatible = Arrays.asList(compat.value());
    boolean ignored = Arrays
      .stream(IGNORED)
      .anyMatch(compatible::contains);

    return ignored || compatible.contains(version);
  }

  /**
   * Gets the compatible versions of the given class.
   *
   * @param clazz the class to get the compatible versions of
   * @return Returns the compatible versions
   */
  public static List<String> getCompatibleVersions(Class<?> clazz) {
    Compat compat = clazz.getAnnotation(Compat.class);
    if (compat == null)
      return null;
    return Arrays.asList(compat.value());
  }
}
