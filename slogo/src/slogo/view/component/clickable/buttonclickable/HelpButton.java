package slogo.view.component.clickable.buttonclickable;

import static slogo.Main.CSS_FILE;

import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The HelpButton class opens a new window to display information about each command.
 *
 * HelpButtons are made by the Workspace and require a Resource file where the keys are the
 * command and the values are the description to be shown about the command.
 *
 * @author Bill Guo, Tomas Esber
 */
public class HelpButton extends ButtonClickable {

  private final String INFO_PATH = "slogo/resources/toolbar/Info";
  private ResourceBundle helpBundle;
  private HBox helpMenu;

  /**
   * Constructor of a HelpButton.
   * @param image Icon for the Button
   */
  public HelpButton(Image image) {
    super(image);
    helpBundle = ResourceBundle.getBundle(INFO_PATH);
  }

  /**
   * Returns the EventHandler for a HelpButton. In this case, opens a new stage to show an HBox
   * that contains the information about each command
   * @return EventHandler to show info box
   */
  @Override
  protected EventHandler<ActionEvent> onClick() {
    return event -> handleHelpButton();
  }

  private void handleHelpButton() {
    HBox helpBox = makeHelpWindow();
    Scene scene = new Scene(helpBox);
    scene.getStylesheets().add(CSS_FILE);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.show();
  }

  private HBox makeHelpWindow() {
    HBox returnBox = new HBox();
    HBox descriptionBox = new HBox();
    descriptionBox.getStyleClass().add("toolbar-descriptionhbox");

    ObservableList commands = makeListOfCommands();
    ListView listOfCommands = new ListView(commands);
    listOfCommands.setId("HelpList");
    listOfCommands.getStyleClass().add("toolbar-listview");
    listOfCommands.setOnMouseClicked(e -> addDescription(listOfCommands, descriptionBox));
    returnBox.getChildren().addAll(listOfCommands, descriptionBox);
    helpMenu = returnBox;

    return returnBox;
  }

  private ObservableList makeListOfCommands() {
    ObservableList commands = FXCollections.observableArrayList();
    commands.addAll(helpBundle.keySet());
    Collections.sort(commands);
    return commands;
  }

  private void addDescription(ListView listOfCommands, HBox descriptionBox) {
    descriptionBox.getChildren().clear();
    ObservableList commandList = listOfCommands.getSelectionModel().getSelectedItems();
    for (Object item : commandList) {
      Text description = new Text(helpBundle.getString((String) item));
      description.setId("HelpDescription");
      description.getStyleClass().add("toolbar-text");
      descriptionBox.getChildren().add(description);
    }
  }
}
