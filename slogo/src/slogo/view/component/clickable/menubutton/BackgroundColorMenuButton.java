package slogo.view.component.clickable.menubutton;

import javafx.scene.image.Image;
import slogo.controller.BackEndController;

/**
 * The BackgroundColorMenuButton class can change the background color of the
 * TurtleGraphicsDisplay.
 *
 * Subscribes to the CodeEntryBox mediator in order to submit background color changing
 * commands.
 *
 * @author Bill Guo
 */
public class BackgroundColorMenuButton extends ColorMenuButton {

  /**
   * Constructor of the BackgroundColorMenuButton
   * @param image Icon for MenuButton
   * @param backEndController Needed for the dynamically updating list of color indices available
   */
  public BackgroundColorMenuButton(Image image,
      BackEndController backEndController) {
    super(image, backEndController);
  }
}
