package slogo.view.component.clickable.buttonclickable;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import slogo.view.component.clickable.Clickable;

/**
 * The ButtonClickable class is an extension of the Clickable class that allows the creation of a
 * JavaFX Button.
 *
 * Needs to implement the onClick() method in order to give the Button an action to do when clicked.
 * ButtonClickables are used to interact with other components of the Workspace without having
 * to have a reference to them.
 *
 * @author Bill Guo
 */
public abstract class ButtonClickable extends Clickable {

  private Button button;

  /**
   * Constructor for ButtonClickable. Stylesheet is found in mainConfig.css
   * @param image Icon for the button
   */
  public ButtonClickable(Image image) {
    super(image);

    button = new Button();
    button.setGraphic(icon);
    button.getStyleClass().add("buttonclickable-button");
    button.setOnAction(onClick());
  }

  /**
   * Returns the JavaFX Button associated with this object
   * @return JavaFX Button associated with this object
   *
   */
  public Button getButton() {
    return button;
  }
}
