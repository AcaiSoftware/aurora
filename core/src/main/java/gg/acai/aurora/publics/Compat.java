package gg.acai.aurora.publics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Compatibility annotation for versioning.
 *
 * @author Clouke
 * @since 03.03.2023 13:33
 * © Aurora - All Rights Reserved
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Compat {
  /**
   * The versions that this class is compatible with.
   *
   * @return Returns the versions
   */
  String[] value();
}
