package slogo.view.component.clickable.menubutton;

import javafx.scene.image.Image;
import slogo.controller.BackEndController;
import slogo.view.Observer;

/**
 * The DynamicMenuButtonClickable class creates MenuButtons that can change dynamically through
 * use of the BackEndController and Observer design pattern.
 *
 * DynamicMenuButtonClickables can be updated when a command gets sent through, as they can
 * observe the CodeEntryBox. They can then check their BackEndController to see what has changed.
 * These clickables can also cause changes, as they are also components.
 *
 * @author Bill Guo
 */
public abstract class DynamicMenuButtonClickable extends MenuButtonClickable implements Observer {

  protected BackEndController backEndController;

  /**
   * Constructor of the DynamicMenuButtonClickable
   * @param image Icon for MenuButton
   * @param backEndController BackEndController to read new data from
   */
  public DynamicMenuButtonClickable(Image image, BackEndController backEndController) {
    super(image);
    this.backEndController = backEndController;
  }
}
