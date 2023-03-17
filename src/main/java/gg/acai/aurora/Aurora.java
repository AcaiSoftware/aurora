package gg.acai.aurora;

import gg.acai.acava.io.logging.Logger;
import gg.acai.acava.io.logging.StandardLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Supplier;

/**
 * @author Clouke
 * @since 12.02.2023 21:52
 * © Acava - All Rights Reserved
 */
public class Aurora {

  public static final String ANSI_BOLD = "\033[1m";
  public static final String RESET = "\033[0m";
  public static final String BULLET = "• ";

  private static final Logger LOGGER = new StandardLogger("Aurora");
  private static Supplier<String> VERSION;

  public static void log(String message) {
    LOGGER.log(message);
  }

  public static String version() {
    if (VERSION == null) {
      String version = null;
      try (BufferedReader reader = new BufferedReader(new FileReader("pom.xml"))) {
        String line;
        while ((line = reader.readLine()) != null) {
          if (line.contains("<version>")) {
            version = line
              .replace("<version>", "")
              .replace("</version>", "")
              .trim();
              break;
          }
        }
      } catch (IOException e) {
        log("Failed to read version from pom.xml: " + e.getMessage());
      }

      final String result = version;
      VERSION = version == null ? () -> "N/A" : () -> result;
    }

    return VERSION.get();
  }

}
