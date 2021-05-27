package slogo.view.component.subview;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import slogo.controller.BackEndController;
import slogo.view.Observer;

/**
 * The UserCommands class represents the current TO commands created in the workspace.
 *
 * Updates each time a TO command gets sent in the CodeEntryBox. Can run TO commands created
 * directly from this object by opening a new window and entering the necessary parameters.
 *
 * @author Bill Guo
 */
public class UserCommands extends UserSubview implements Observer {

  private Pane content;
  private String commandName;
  private String commandParameters;

  /**
   * Constructor of the UserCommands.
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labelBundle Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   */
  public UserCommands(String labelKey, ResourceBundle labelBundle,
      BackEndController backEndController) {
    super(labelKey, labelBundle, backEndController);
    makeContent();
  }

  /**
   * Creates the content of the subview, which is a ListView and a button to view more
   * information about the contents of the ListView.
   */
  @Override
  protected void makeContent() {
    content = getContent();
    content.getStyleClass().add("usercommands-content");
    HBox commandsHBox = new HBox();
    commandsHBox.getStyleClass().add("usercommands-hbox");
    Button sendToCommandButton = makeButton("SendToCommandButton", "usercommands-button", e ->
        handleOpenWindowButton(backEndController.getToCommandString().keySet()));
    commandsHBox.getChildren().addAll(listView, sendToCommandButton);
    content.getChildren().add(commandsHBox);
    update();
  }

  /**
   * Updates the list of TO commands available to be run from this object.
   */
  @Override
  public void update() {
    observableList.clear();
    Map<String, String> userCommands = backEndController.getToCommandString();
    for (String command : userCommands.keySet()) {
      observableList.add(command);
    }
  }

  /**
   * Returns an EventHandler that performs an action when a button in the detailed info about the
   * TO commands is pressed. In this case, it will send the chosen TO command with the entered
   * parameters in a TextField to the CodeEntryBox mediator.
   * @return EventHandler to send TO commands
   */
  @Override
  protected EventHandler listViewEventHandler() {
    return event -> sendToCommand(itemListInNewWindow, infoVBoxInNewWindow);
  }

  private void sendToCommand(ListView listOfCommands, VBox sendInfoVBox) {
    sendInfoVBox.getChildren().clear();
    ObservableList commandList = listOfCommands.getSelectionModel().getSelectedItems();
    for (Object item : commandList) {
      Text commandBody = new Text(backEndController.getToCommandString().get(item));
      TextField newParameterTextField = makeTextField();
      Button submitParameterButton = makeButton("Submit", "usercommands-button",
          e -> handleSubmitParameterButton((String) item, newParameterTextField.getText()));
      sendInfoVBox.getChildren().addAll(commandBody, newParameterTextField, submitParameterButton);
    }
  }

  private void handleSubmitParameterButton(String item, String text) {
    commandName = item;
    try {
      commandParameters = text;
      notifyMediator();
      stage.close();
    } catch (Exception e) {
      makeAlert(ERROR_MESSAGE);
    }
  }

  /**
   * Returns the name of the TO command. Used by the CodeEntryBox mediator to get the name of the
   * TO command to be sent.
   * @return String that represents name of the TO command
   */
  public String getCommandName() {
    return commandName;
  }

  /**
   * Returns the parameters to be sent along with the name of the TO command. Used by the
   * CodeEntryBox mediator to send the parameters of the TO command.
   * @return String that represents the entered parameters of the TO command
   */
  public String getCommandParameters() {
    return commandParameters;
  }
}
