package slogo.view.mediator;

import java.util.ResourceBundle;
import slogo.view.component.Component;
import slogo.view.component.subview.ConsoleGraphicsDisplay;

/**
 * The ConsoleGraphicsDisplayMediator class handles all components that can interact with the
 * ConsoleGraphicsDisplay.
 *
 * Classes that use this mediator are the ShowCommandHistoryButton.
 *
 * @author Bill Guo
 */
public class ConsoleGraphicsDisplayMediator extends Mediator {

  private static final String METHOD_FILE = "slogo/resources/mediators/ConsoleDisplay";

  private ConsoleGraphicsDisplay target;

  /**
   * Constructor of the ConsoleGraphicsDisplayMediator.
   * @param consoleGraphicsDisplay consoleGraphicsDisplay to show past commands in
   */
  public ConsoleGraphicsDisplayMediator(ConsoleGraphicsDisplay consoleGraphicsDisplay) {
    target = consoleGraphicsDisplay;
    setMethodBundle(ResourceBundle.getBundle(METHOD_FILE));
  }

  /**
   * Shows past commands in response to the ShowCommandHistoryButton being clicked.
   * @param component
   */
  protected void handleShowCommandHistoryButton(Component component) {
    target.showCommandHistory();
  }

}
