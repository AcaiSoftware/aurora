package gg.acai.aurora.universal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Clouke
 * @since 03.03.2023 13:33
 * © Aurora - All Rights Reserved
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Compat {
  String[] value();
}
