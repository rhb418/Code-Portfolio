package slogo.view.mediator;

import java.util.ResourceBundle;
import slogo.view.component.Component;
import slogo.view.component.clickable.buttonclickable.LoadPreferencesButton;
import slogo.view.component.clickable.menubutton.BackgroundColorMenuButton;
import slogo.view.component.clickable.menubutton.LineColorMenuButton;
import slogo.view.component.clickable.menubutton.PenMenuButton;
import slogo.view.component.clickable.menubutton.TurtleMenuButton;
import slogo.view.component.clickable.menubutton.TurtleShapeMenuButton;
import slogo.view.component.subview.CodeEntryBox;
import slogo.view.component.subview.UserCommands;
import slogo.view.component.subview.UserVariables;

/**
 * The CodeEntryBoxMediator class handles all components that can affect the CodeEntryBox.
 *
 * Classes that use the CodeEntryBoxMediator to send commands include UserVariables,
 * UserCommands, PenMenuButton, TurtleShapeMenuButton, TurtleMenuButton, LineColorMenuButton,
 * BackgroundColorMenuButton, and LoadPreferencesMenuButton.
 *
 * @author Bill Guo
 */
public class CodeEntryBoxMediator extends Mediator {

  private static final String METHOD_FILE = "slogo/resources/mediators/CodeEntryBox";
  private static final String LANGUAGE_FILE_ROOT = "slogo/resources/languages/";

  private CodeEntryBox target;
  private ResourceBundle languageBundle;

  /**
   * Constructor of the CodeEntryBoxMediator.
   * @param codeEntryBox codeEntryBox to run commands in
   * @param language language that the commands will be run in
   */
  public CodeEntryBoxMediator(CodeEntryBox codeEntryBox, String language) {
    target = codeEntryBox;
    setMethodBundle(ResourceBundle.getBundle(METHOD_FILE));
    languageBundle = ResourceBundle.getBundle(LANGUAGE_FILE_ROOT + language);
  }

  public void setLanguage(String language) {
    languageBundle = ResourceBundle.getBundle(LANGUAGE_FILE_ROOT + language);
  }

  /**
   * Enters a command to change the value of a variable.
   * @param component
   */
  protected void handleUserVariables(Component component) {
    UserVariables data = (UserVariables) component;
    StringBuilder command = new StringBuilder();
    String setVariable = languageBundle.getString("MakeVariable").split("\\|")[0];
    command.append(setVariable + " ");
    command.append(data.getNewVariableName() + " ");
    command.append(data.getNewVariableValue());
    target.submitCommand(command.toString());
  }

  /**
   * Enters a command to run a defined TO command.
   * @param component
   */
  protected void handleUserCommands(Component component) {
    UserCommands data = (UserCommands) component;
    StringBuilder command = new StringBuilder();
    command.append(data.getCommandName() + " ");
    command.append(data.getCommandParameters());
    target.submitCommand(command.toString());
  }

  /**
   * Enters a command to run a command related to changing the pen properties.
   * @param component
   */
  protected void handlePenMenuButton(Component component) {
    PenMenuButton button = (PenMenuButton) component;
    String rawCommand = button.getMyCommandString().split("\\|")[1];
    String[] splitCommand = rawCommand.split(" ");
    StringBuilder command = new StringBuilder();
    command.append(languageBundle.getString(splitCommand[0]).split("\\|")[0]);
    if (splitCommand.length > 1) {
      command.append(" " + splitCommand[1]);
    }
    target.submitCommand(command.toString());
  }

  /**
   * Enters a command to change the turtle shape.
   * @param component
   */
  protected void handleTurtleShapeMenuButton(Component component) {
    TurtleShapeMenuButton button = (TurtleShapeMenuButton) component;
    String index = button.getShapeIndex();
    target.submitCommand(languageBundle.getString("SetShape").split("\\|")[0] + " " + index);
  }

  /**
   * Enters a command to move or rotate the turtle.
   * @param component
   */
  protected void handleTurtleMenuButton(Component component) {
    TurtleMenuButton button = (TurtleMenuButton) component;
    String rawCommand = button.getMyTurtleCommand().split("\\|")[1];
    String[] splitCommand = rawCommand.split(" ");
    StringBuilder command = new StringBuilder();
    command.append(languageBundle.getString(splitCommand[0]).split("\\|")[0]);
    command.append(" " + splitCommand[1]);
    target.submitCommand(command.toString());
  }

  /**
   * Enters a command to change the color of a turtle's lines.
   * @param component
   */
  protected void handleLineColorMenuButton(Component component) {
    LineColorMenuButton button = (LineColorMenuButton) component;
    String index = button.getColor();
    target.submitCommand(languageBundle.getString("SetPenColor").split("\\|")[0] + " " + index);
  }

  /**
   * Enters a command to change the background color of the TurtleGraphicsDisplay.
   * @param component
   */
  protected void handleBackgroundColorMenuButton(Component component) {
    BackgroundColorMenuButton button = (BackgroundColorMenuButton) component;
    String index = button.getColor();
    target.submitCommand(languageBundle.getString("SetBackground").split("\\|")[0] + " " + index);
  }

  /**
   * Enters a command to run a preset block of code from the LoadPreference button.
   * @param component
   */
  protected void handleLoadPreferencesButton(Component component) {
    LoadPreferencesButton button = (LoadPreferencesButton) component;
    String[] splitCommand = button.getCode().split("\\|");
    StringBuilder command = new StringBuilder();
    for (int index = 0; index < splitCommand.length; index++) {
      command.append(splitCommand[index] + "\n");
    }
    target.submitCommand(command.toString());
  }
}
