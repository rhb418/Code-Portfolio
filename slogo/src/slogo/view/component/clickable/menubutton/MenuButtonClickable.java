package slogo.view.component.clickable.menubutton;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import slogo.controller.BackEndController;
import slogo.view.component.clickable.Clickable;

/**
 * The MenuButtonClickable class is an extension of the Clickable class that uses a JavaFX
 * MenuButton.
 *
 * Needs to implement the onClick() method in order to give the each menuItem an action to do when
 * clicked. MenuButtonClickables are used to interact with other components of the Workspace
 * without having to have a reference to them.
 *
 * @author Bill Guo
 */
public abstract class MenuButtonClickable extends Clickable {

  private MenuButton menuButton;

  /**
   * Constructor of the MenuButtonClickable
   * @param image Icon for MenuButton
   */
  public MenuButtonClickable(Image image) {
    super(image);

    menuButton = new MenuButton();
    menuButton.setGraphic(icon);
    menuButton.getStyleClass().add("buttonclickable-menubutton");
  }

  /**
   * Returns the a List of MenuItems that the MenuButton is built off of. Implementations of this
   * method can either use a Resource file to get the list of items or a list from the
   * BackEndController to build the MenuButton depending on if the list needs to be dynamically
   * changed as the Workspace progresses.
   * @return List of MenuItems that represent the MenuButton
   */
  protected abstract List<MenuItem> makeMenuItems();

  /**
   * Returns the JavaFX MenuButton associated with a MenuButtonClickable.
   * @return JavaFX MenuButton associated with a MenuButtonClickable
   */
  public MenuButton getMenuButton() {
    return menuButton;
  }
}
