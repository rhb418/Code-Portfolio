package ooga.view.util;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;

public class ConfigurationAdapter {

  @FromJson
  ObservableMap<KeyCode, String> objectFromJson(Map<String, String> keyMaps) {
    Map<KeyCode, String> convertedMap = new HashMap<>();
    keyMaps.forEach((key, val) -> convertedMap.put(KeyCode.getKeyCode(val), key));
    return FXCollections.observableMap(convertedMap);
  }

  @ToJson
  Map<String, String> objectToJson(ObservableMap<KeyCode, String> keyMaps) {
    Map<String, String> newMap = new HashMap<>();
    keyMaps.forEach((key, value) -> newMap.put(key.getName(), value));
    return newMap;
  }

  @FromJson
  ObservableList<String> objectFromJson(List<String> stats) {
    return FXCollections.observableList(stats);
  }

  @ToJson
  List<String> objectFromJson(ObservableList<String> stats) {
    return stats;
  }

  @FromJson
  ObservableResource objectFromJson(String language) {
    ObservableResource resources = new ObservableResource();
    ResourceBundle res = ResourceBundle.getBundle(ViewConfiguration.DEFAULT_RESOURCES + language);
    resources.setResources(res);
    return resources;
  }

  @ToJson
  String objectFromJson(ObservableResource resources) {
    return resources.getLanguage();
  }
}
