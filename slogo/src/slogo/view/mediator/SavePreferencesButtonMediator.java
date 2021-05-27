package slogo.view.mediator;

import java.util.ResourceBundle;
import slogo.view.component.Component;
import slogo.view.component.clickable.buttonclickable.SavePreferencesButton;
import slogo.view.component.subview.CodeEntryBox;
import slogo.view.component.subview.ConsoleGraphicsDisplay;
import slogo.view.component.subview.ShapePalette;

/**
 * The SavePreferencesButtonMediator handles all components that interact with the
 * SavePreferencesButton.
 *
 * Classes that use this mediator include CodeEntryBox, ShapePalette, and ConsoleGraphicsDisplay.
 *
 * @author Bill Guo
 */
public class SavePreferencesButtonMediator extends Mediator {

  private static final String METHOD_FILE = "slogo/resources/mediators/SavePreferencesButton";

  private SavePreferencesButton target;

  /**
   * Constructor of the SavePreferencesButtonMediator.
   * @param savePreferencesButton
   */
  public SavePreferencesButtonMediator(SavePreferencesButton savePreferencesButton) {
    target = savePreferencesButton;
    setMethodBundle(ResourceBundle.getBundle(METHOD_FILE));
  }

  /**
   * Sets the language of the SavePreferencesButton to the one of the CodeEntryBox when the
   * language gets changed.
   * @param component
   */
  protected void handleCodeEntryBox(Component component) {
    CodeEntryBox data = (CodeEntryBox) component;
    target.setLanguage(data.getLanguage());
  }

  /**
   * Sets the shape palette path of SavePreferencesButton to the one of the ShapePalette when the
   * shape palette gets changed.
   * @param component
   */
  protected void handleShapePalette(Component component) {
    ShapePalette data = (ShapePalette) component;
    target.setShapePalette(data.getPropertiesFile());
  }

  /**
   * Sets the block of code ran of SavePreferencesButton to the most updated block of code ran
   * whenever a new command gets run successfully.
   * @param component
   */
  protected void handleConsoleGraphicsDisplay(Component component) {
    ConsoleGraphicsDisplay data = (ConsoleGraphicsDisplay) component;
    StringBuilder commands = new StringBuilder();
    for (String command : data.getRanCommands()) {
      commands.append(command + "|");
    }
    try {
      target.setCode(commands.substring(0, commands.length() - 1));
    } catch (StringIndexOutOfBoundsException e) {
      makeAlert("failed command, not added to history");
    }
  }

}
