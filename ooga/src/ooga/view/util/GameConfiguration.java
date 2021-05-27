package ooga.view.util;

import com.squareup.moshi.Json;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;

public class GameConfiguration {
  public static final String DEFAULT_RESOURCES = "ooga.view.resources.languages.";

  @Json(name = "key_maps")
  ObservableMap<KeyCode, String> keyMaps;

  ObservableList<String> stats;

  // Moshi needs this
  public GameConfiguration() {}

  public ObservableMap<KeyCode, String> getKeyMap() {
    return this.keyMaps;
  }

  public ObservableList<String> getStats() {
    return this.stats;
  }
}
