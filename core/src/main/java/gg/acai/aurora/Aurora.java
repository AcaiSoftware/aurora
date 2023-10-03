package gg.acai.aurora;

import gg.acai.acava.io.filter.reader.BufferedFilterableReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * A class for Aurora specific information.
 *
 * @author Clouke
 * @since 12.02.2023 21:52
 * © Acava - All Rights Reserved
 */
public class Aurora {

  private static final Logger LOGGER = Logger.getLogger("Aurora");
  private static Supplier<String> VERSION;

  /**
   * Gets the version of Aurora.
   *
   * @return Returns the version of Aurora
   */
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
          LOGGER.warning("Failed to read version from pom.xml: " + e.getMessage());
          return "ignore";
        }
      };
    }

    return VERSION.get();
  }

  public static Logger logger() {
    return LOGGER;
  }

  /**
   * Checks if Aurora is running in a UI environment.
   *
   * @return Returns true if Aurora is running in a UI environment
   */
  public static boolean supportsUI() {
    return System.getProperty("aurora.ui") != null;
  }

}
