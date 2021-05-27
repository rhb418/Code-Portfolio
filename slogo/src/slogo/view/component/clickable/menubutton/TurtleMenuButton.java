package slogo.view.component.clickable.menubutton;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;

/**
 * The TurtleMenuButton class allows commands that change the state of the turtle to be sent
 * through a button press.
 *
 * Needs a resource file to get the button labels as well as the command to be run. Subscribes to
 * the CodeEntryBox mediator in order to alert the subview that a command needs to be run.
 *
 * @author Bill Guo, Tomas Esber
 */
public class TurtleMenuButton extends StaticMenuButtonClickable {

  private String myTurtleCommand;

  /**
   * Constructor of the TurtleMenuButton
   * @param image Icon for MenuButton
   * @param menuBundle Resource file whose keys are the MenuButton labels and whose values are
   *                   the commands to be sent to the CodeEntryBox
   */
  public TurtleMenuButton(Image image, ResourceBundle menuBundle) {
    super(image, menuBundle);
  }

  /**
   * Returns the EventHandler for each MenuItem. In this case, sets the command to be read by the
   * CodeEntryBox.
   * @return EventHandler for each MenuItem.
   */
  @Override
  protected EventHandler<ActionEvent> onClick() {
    return event -> turtleCommand(event);
  }

  private void turtleCommand(ActionEvent event) {
    MenuItem item = (MenuItem) event.getSource();
    String command = item.getText();
    myTurtleCommand = menuBundle.getString(command);
    notifyMediator();
  }

  /**
   * Returns the command as a String. Used by CodeEntryBoxMediator to get the chosen command to
   * be sent.
   * @return String that represents command
   */
  public String getMyTurtleCommand() {
    return myTurtleCommand;
  }
}
