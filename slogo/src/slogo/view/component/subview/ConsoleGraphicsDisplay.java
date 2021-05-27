package slogo.view.component.subview;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import slogo.controller.BackEndController;
import slogo.controller.FrontEndController;
import slogo.view.Observer;

/**
 * The ConsoleGraphicsDisplay class shows a console where errors are written and previous
 * commands can be seen.
 *
 * Has a mediator to show previous commands when other components are interacted with. Subscribes
 * to the SavePreferencesButton mediator to update the history of commands when a new command
 * gets run.
 *
 * @author Bill Guo, Billy Luqiu
 */
public class ConsoleGraphicsDisplay extends Subview implements Observer {

  private BackEndController backEndController;
  private Pane content;
  private HBox consoleBox;
  private TextArea consoleTextArea;

  /**
   * Constructor of the ConsoleGraphicsDisplay.
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labelBundle Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   */
  public ConsoleGraphicsDisplay(String labelKey, ResourceBundle labelBundle,
      BackEndController backEndController) {
    super(labelKey, labelBundle, backEndController);
    this.backEndController = backEndController;
    makeContent();
  }

  /**
   * Makes the content of this subview, which is a TextArea to show previous commands and errors.
   */
  @Override
  protected void makeContent() {
    content = getContent();
    content.getStyleClass().add("");

    consoleBox = new HBox();
    consoleBox.getStyleClass().add("consolegraphicsdisplay-hbox");

    consoleTextArea = new TextArea();
    consoleTextArea.setId("ConsoleTextBox");
    consoleTextArea.getStyleClass().add("consolegraphicsdisplay-textarea");

    consoleBox.getChildren().add(consoleTextArea);
    content.getChildren().add(consoleBox);
  }

  /**
   * Shows potential error messages every time a command gets run.
   */
  @Override
  public void update() {
    consoleTextArea.setText(backEndController.getErrorMessage());
    notifyMediator();
  }

  public List<String> getRanCommands() {
    return backEndController.getPastCommands();
  }

  /**
   * Shows the command history. Used by its mediator when other components want to show the
   * command history.
   */
  public void showCommandHistory() {
    StringBuilder commandHistory = new StringBuilder();
    for (String command : backEndController.getPastCommands()) {
      commandHistory.append(command + "\n");
    }
    consoleTextArea.setText(commandHistory.toString());
  }
}
