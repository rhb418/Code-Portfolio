package ooga.view.components;

import com.jfoenix.controls.JFXButton;
import java.util.Arrays;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ooga.view.util.ObservableResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

public class GenericMenu extends StackPane {
  private static final Logger logger = LogManager.getLogger(GenericMenu.class);


  private VBox contents;

  public GenericMenu(StringBinding title) {
    super();
    getStyleClass().add("pause-dialog");
    this.contents = new VBox();
    Label pauseLabel = new Label();
    pauseLabel.textProperty().bind(title);
    contents.getChildren().addAll(pauseLabel);
    getChildren().addAll(contents);
  }

  public void addOption(StringBinding text, Runnable action, String... classes) {
    JFXButton button = new JFXButton();
    button.textProperty().bind(text);
    button.setGraphic(new FontIcon());
    button.setOnAction(e -> action.run());
    logger.info("{} has {} classes", text.get(), Arrays.toString(classes));
    contents.getChildren().addAll(button);
    Arrays.stream(classes).forEach(button.getStyleClass()::add); // sexy :D
  }
}
