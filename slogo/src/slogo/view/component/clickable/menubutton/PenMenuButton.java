package slogo.view.component.clickable.menubutton;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import slogo.view.component.clickable.menubutton.MenuButtonClickable;

/**
 * The PenMenuButton class allows commands that change the state of the pen to be sent through a
 * button press.
 *
 * Needs a resource file to get the button labels as well as the command to be run. Subscribes to
 * the CodeEntryBox mediator in order to alert the subview that a command needs to be run.
 *
 * @author Bill Guo, Tomas Esber
 */
public class PenMenuButton extends StaticMenuButtonClickable {

  private String myCommandString;

  /**
   * Constructor of the PenMenuButton
   * @param image Icon for MenuButton
   * @param menuBundle Resource file whose keys are the MenuButton labels and whose values are
   *                   the commands to be sent to the CodeEntryBox
   */
  public PenMenuButton(Image image, ResourceBundle menuBundle) {
    super(image, menuBundle);
  }

  /**
   * Returns the EventHandler for each MenuItem. In this case, sets the command to be read by the
   * CodeEntryBox.
   * @return EventHandler for each MenuItem.
   */
  @Override
  protected EventHandler<ActionEvent> onClick() {
    return event -> doPenAction(event);
  }

  private void doPenAction(ActionEvent event) {
    MenuItem item = (MenuItem) event.getSource();
    myCommandString = menuBundle.getString(item.getText());
    notifyMediator();
  }

  /**
   * Returns the command as a String. Used by CodeEntryBoxMediator to get the chosen command to
   * be sent.
   * @return String that represents command
   */
  public String getMyCommandString() {
    return myCommandString;
  }
}
