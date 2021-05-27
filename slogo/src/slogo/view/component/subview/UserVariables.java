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
 * The UserVariables class represents the list of current variables in the workspace.
 *
 * Updates each time a new variable is created through a command by observing the CodeEntryBox.
 * Can directly change the value of variables from this class.
 *
 * @author Bill Guo
 */
public class UserVariables extends UserSubview implements Observer {

  private Pane content;
  private String newVariableName;
  private double newVariableValue;

  /**
   * Constructor of the UserVariables.
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labelBundle Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   */
  public UserVariables(String labelKey, ResourceBundle labelBundle,
      BackEndController backEndController) {
    super(labelKey, labelBundle, backEndController);
    makeContent();
  }

  /**
   * Creates the content of the Subview, which is a ListView of the current variables and a
   * button that opens a new window with detailed information about each variable.
   */
  @Override
  protected void makeContent() {
    content = getContent();
    content.getStyleClass().add("uservariables-content");
    HBox variablesHBox = new HBox();
    variablesHBox.getStyleClass().add("uservariables-hbox");
    Button editVariableButton = makeButton("EditVariableButton",
        "uservariables-button",
        e -> handleOpenWindowButton(backEndController.getCurrentVariables().keySet()));
    variablesHBox.getChildren().addAll(listView, editVariableButton);
    content.getChildren().add(variablesHBox);
    update();
  }

  /**
   * Updates the list of variables based on information from the BackEndController. Is run
   * whenever a new command gets sent.
   */
  @Override
  public void update() {
    observableList.clear();
    Map<String, Double> userVariables = backEndController.getCurrentVariables();
    for (String variable : userVariables.keySet()) {
      observableList.add(variable);
    }
  }

  /**
   * Returns an EventHandler that performs an action when a button in the detailed info about the
   * variables is pressed. In this case, it will send a command to set the value of the selected
   * variable to the value that is written in the TextField in the new window.
   * @return EventHandler to change variable values
   */
  @Override
  protected EventHandler listViewEventHandler() {
    return event -> changeVariable(itemListInNewWindow, infoVBoxInNewWindow);
  }

  private void changeVariable(ListView listOfVariables, VBox sendInfoVBox) {
    sendInfoVBox.getChildren().clear();
    ObservableList variableList = listOfVariables.getSelectionModel().getSelectedItems();
    for (Object item : variableList) {
      Text variableName = new Text(labelBundle.getString("Name") + ": " + item);
      Text variableValue =
          new Text(
              labelBundle.getString("Value") + ": " + backEndController.getCurrentVariables().get(
                  item));
      Text changeVariablePrompt = new Text(labelBundle.getString("ChangeVariable"));
      TextField newVariableTextField = makeTextField();
      Button submitVariableButton = makeButton("SubmitVariableButton", "uservariables-button",
          e -> handleSubmitVariableButton((String) item, newVariableTextField.getText()));

      sendInfoVBox.getChildren().addAll(variableName, variableValue, changeVariablePrompt,
          newVariableTextField, submitVariableButton);
    }
  }


  private void handleSubmitVariableButton(String name, String value) {
    newVariableName = name;
    try {
      newVariableValue = Double.valueOf(value);
      notifyMediator();
      stage.close();
    } catch (Exception e) {
      makeAlert(ERROR_MESSAGE);
    }
  }

  /**
   * Returns the name of the variable. Used by the CodeEntryBox mediator to get the name of the
   * variable to be sent.
   * @return String that represents name of variable
   */
  public String getNewVariableName() {
    return newVariableName;
  }

  /**
   * Returns the value that the variable will be set to. The CodeEntryBox mediator uses this to
   * create the command that will change the variable value.
   * @return String that represents the new value of the variable
   */
  public double getNewVariableValue() {
    return newVariableValue;
  }
}
