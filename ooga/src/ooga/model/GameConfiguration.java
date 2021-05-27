package ooga.model;

import ooga.model.exceptions.InvalidDataFileException;

public class GameConfiguration {

  public static final String CONFIG_JSON = "config.json";
  private String startLevel;

  public String getStartLevel() {
    if (startLevel == null) {
      throw new InvalidDataFileException(CONFIG_JSON);
    }
    return startLevel;
  }
}

