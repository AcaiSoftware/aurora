package gg.acai.aurora.universal;

import gg.acai.aurora.Aurora;
import gg.acai.aurora.universal.Compat;

import java.util.Arrays;
import java.util.List;

/**
 * @author Clouke
 * @since 03.03.2023 14:31
 * © Aurora - All Rights Reserved
 */
public final class Compatibility {

  private static final String[] IGNORED = {"ignore", "all"};

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

  public static List<String> getCompatibleVersions(Class<?> clazz) {
    Compat compat = clazz.getAnnotation(Compat.class);
    if (compat == null)
      return null;
    return Arrays.asList(compat.value());
  }
}