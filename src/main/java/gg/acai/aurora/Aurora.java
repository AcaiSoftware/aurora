package gg.acai.aurora;

import gg.acai.acava.io.logging.Logger;
import gg.acai.acava.io.logging.StandardLogger;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

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
      MavenXpp3Reader reader = new MavenXpp3Reader();
      Model model;
      try {
        model = reader.read(new FileReader("pom.xml"));
      } catch (IOException | XmlPullParserException e) {
        throw new RuntimeException(e);
      }
      VERSION = model::getVersion;
    }

    return VERSION.get();
  }

}
