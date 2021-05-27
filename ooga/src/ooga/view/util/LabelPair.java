package ooga.view.util;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class LabelPair extends HBox {

  private Label value;

  public LabelPair(StringBinding labelTitle) {
    Label labelName = new Label();
    this.value = new Label();
    labelName.setLabelFor(value);
    labelName.textProperty().bind(labelTitle);
    value.getStyleClass().add("value-label");
    value.setWrapText(true);
    HBox.setHgrow(value, Priority.NEVER);
    HBox.setHgrow(labelName, Priority.ALWAYS);
    getChildren().addAll(labelName, value);
  }

  public void setValue(String labelValue) {
    value.setText(labelValue);
  }
}
