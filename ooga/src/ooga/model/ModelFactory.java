package ooga.model;

import java.io.File;
import ooga.model.observables.ObservableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ModelFactory {

  // Make private once moved to abstract class
  static final Logger logger = LogManager.getLogger(ModelFactory.class);

  public static ObservableModel createObservableModel() {
    return null;
  }

  //TODO: Change to throw exceptions like "Invalid Configuration", No game detected etc.
  static boolean verifyGameDirectory(File file) {
    if (!file.exists()) {
      logger.info("{} does not exist", file.getAbsolutePath());
      return false;
    }

    File configFile = new File(file.getAbsolutePath() + "/config.json");
    if (!configFile.exists()) {
      logger.info("{} does not exist", configFile.getAbsolutePath());
      return false;
    }
    // other checks
    logger.info("{} is correct", file.getAbsolutePath());
    return true;
  }
}
