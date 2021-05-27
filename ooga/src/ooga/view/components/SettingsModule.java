package ooga.view.components;

import com.jfoenix.controls.JFXComboBox;

import com.jfoenix.controls.JFXTextField;
import java.util.List;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class SettingsModule extends StackPane {

  private FlowPane area;
  private StringBinding moduleName;

  public SettingsModule(StringBinding moduleName) {
    getStyleClass().addAll("settings-module");
    this.moduleName = moduleName;
    area = new FlowPane();
    area.getStyleClass().addAll("settings-area");
    getChildren().addAll(area);
  }

  public StringBinding moduleBinding() {
    return this.moduleName;
  }

  /**
   * Add a setting to the settings pane and return a binding to track its updates.
   *
   * @param label - The label lol
   */
  public StringBinding addFieldSettings(StringBinding text) {
    Label label = new Label();
    label.textProperty().bind(text);
    return null;
  }

  public ReadOnlyObjectProperty<String> addListSetting(
      StringBinding text, ObservableList<String> list) {
    JFXComboBox<String> comboBox = new JFXComboBox<>(list);
    Label label = new Label();
    label.textProperty().bind(text);
    createPair(label, comboBox);
    return comboBox.getSelectionModel().selectedItemProperty();
  }

  public void addKeysOption(ObservableMap<KeyCode, String> keyMap, List<String> codes) {
    JFXComboBox<String> availableKeys = new JFXComboBox<>(FXCollections.observableList(codes));
    availableKeys.setPromptText("...");
    JFXTextField field = new JFXTextField();
    field.setOnKeyPressed(
        e -> {
          field.setText(e.getCode().toString());
          if (availableKeys.getValue() != null) {
            keyMap.remove(e.getCode());
            keyMap.put(e.getCode(), availableKeys.getValue());
          }
        });
    availableKeys.setOnAction(
        e -> {
          String code = availableKeys.getValue();
          keyMap.entrySet().stream()
              .filter(entry -> entry.getValue().equals(code))
              .findFirst()
              .ifPresent(pair -> field.setText(pair.getKey().toString()));
        });
    createPair(availableKeys, field);
  }

  private void createPair(Node label, Node value) {
    HBox box = new HBox();
    box.getStyleClass().addAll("settings-pair");
    box.getChildren().addAll(label, value);
    area.getChildren().addAll(box);
  }
}
