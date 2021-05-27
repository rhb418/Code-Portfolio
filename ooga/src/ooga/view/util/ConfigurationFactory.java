package ooga.view.util;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ConfigurationFactory {

  private static final Logger logger = LogManager.getLogger(MetaGame.class);
  private static JsonAdapter<GameConfiguration> adapter =
      new Moshi.Builder().add(new ConfigurationAdapter()).build().adapter(GameConfiguration.class);
  private static JsonAdapter<ViewConfiguration> viewAdapter =
      new Moshi.Builder().add(new ConfigurationAdapter()).build().adapter(ViewConfiguration.class);

  public static GameConfiguration createConfiguration(String filePath) {
    if (filePath == null || filePath.isBlank()) {
      return createConfiguration();
    }
    try {
      String string = Files.readString(Path.of(filePath));
      GameConfiguration config = adapter.fromJson(string);
      logger.debug("Game Configuration Created: {}", config);
      return config;
    } catch (IOException e) {
      logger.debug("Unable to create Game Configuration from directory {}", filePath);
      return null;
    }
  }

  public static GameConfiguration createConfiguration() {
    return new GameConfiguration();
  }

  public static ViewConfiguration createViewConfig(String filePath) {
    if (filePath == null || filePath.isBlank()) {
      return null;
    }
    try {
      String string = Files.readString(Path.of(filePath));
      ViewConfiguration config = viewAdapter.fromJson(string);
      logger.debug("View Configuration Created: {}", config);
      return config;
    } catch (IOException e) {
      logger.debug("Unable to create Configuration from directory {}", filePath);
      return null;
    }
  }
}
