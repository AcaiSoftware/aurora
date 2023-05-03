package gg.acai.aurora;

import gg.acai.acava.io.filter.reader.BufferedFilterableReader;
import gg.acai.acava.io.logging.Logger;
import gg.acai.acava.io.logging.StandardLogger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.function.Supplier;

/**
 * @author Clouke
 * @since 12.02.2023 21:52
 * © Acava - All Rights Reserved
 */
public class Aurora {

  private static final Logger LOGGER = new StandardLogger("Aurora");
  private static Supplier<String> VERSION;

  public static void log(String message) {
    LOGGER.log(message);
  }

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

  public static boolean supportsUI() {
    return System.getProperty("aurora.ui") != null;
  }

}
