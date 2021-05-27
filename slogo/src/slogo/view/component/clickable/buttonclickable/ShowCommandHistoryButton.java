package slogo.view.component.clickable.buttonclickable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

/**
 * The ShowCommandHistoryButton class prints the previously ran commands to the
 * ConsoleGraphicsDisplay.
 *
 * Subscribes to the ConsoleGraphicsDisplayMediator to show the previously ran commands when the
 * button gets pressed.
 *
 * @author Bill Guo
 */
public class ShowCommandHistoryButton extends ButtonClickable {

  /**
   * Constructor of a ShowCommandHistoryButton.
   * @param image Icon for Button
   */
  public ShowCommandHistoryButton(Image image) {
    super(image);
  }

  /**
   * Returns the EventHandler of a ShowCommandHistoryButton. In this case, the button will update
   * its mediators to show the history of commands.
   * @return EventHandler to request mediator to show commands
   */
  @Override
  protected EventHandler<ActionEvent> onClick() {
    return event -> showHistory();
  }

  private void showHistory() {
    notifyMediator();
  }
}
