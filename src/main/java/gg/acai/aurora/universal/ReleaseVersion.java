package gg.acai.aurora.universal;

import gg.acai.acava.io.filter.reader.BufferedFilterableReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.function.Supplier;

import static gg.acai.aurora.Aurora.log;

/**
 * @author Clouke
 * @since 01.04.2023 15:33
 * © Aurora - All Rights Reserved
 */
public class ReleaseVersion {

  private static Supplier<String> VERSION;

  public static String version() {
    if (VERSION == null) {
      VERSION = () -> {
        try {
          return new BufferedFilterableReader()
            .findOptionally(new FileReader("pom.xml"), line -> line.contains("<version>"))
            .map(line -> line
              .replace("<version>", "")
              .replace("</version>", "")
              .trim())
            .orElse("ignore");
        } catch (FileNotFoundException e) {
          log("Failed to read version from pom.xml: " + e.getMessage());
          return "ignore";
        }
      };
    }

    return VERSION.get();
  }

}
