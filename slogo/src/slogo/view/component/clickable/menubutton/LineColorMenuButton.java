package slogo.view.component.clickable.menubutton;

import javafx.scene.image.Image;
import slogo.controller.BackEndController;

/**
 * The LineColorMenuButton class can change the line color of the
 * TurtleGraphicsDisplay.
 *
 * Subscribes to the CodeEntryBox mediator in order to submit line color changing
 * commands.
 *
 * @author Bill Guo
 */
public class LineColorMenuButton extends ColorMenuButton {

  /**
   * Constructor of the LineColorMenuButton
   * @param image Icon for MenuButton
   * @param backEndController Needed for the dynamically updating list of color indices available
   */
  public LineColorMenuButton(Image image,
      BackEndController backEndController) {
    super(image, backEndController);
  }
}
