package slogo.view.component.clickable.menubutton;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;

/**
 * The TurtleShapeMenuButton class changes the shape of the active turtles.
 *
 * Subscribes to the CodeEntryBox mediator in order to notify it when a new command that changes
 * the shape has been selected.
 *
 * @author Bill Guo, Tomas Esber
 */
public class TurtleShapeMenuButton extends StaticMenuButtonClickable {

  private String shapeIndex;

  /**
   * Constructor of the TurtleShapeMenuButton.
   * @param image Icon for MenuButton
   * @param menuBundle Resource file whose keys are the MenuButton labels and whose values are
   *                   the indices for each shape
   */
  public TurtleShapeMenuButton(Image image, ResourceBundle menuBundle) {
    super(image, menuBundle);
  }

  /**
   * Returns the EventHandler of each MenuItem. In this case, shapeIndex will get set to the
   * index of the chosen shape.
   * @return EventHandler to change shapeIndex to chosen value
   */
  @Override
  protected EventHandler<ActionEvent> onClick() { //How to apply this shape change to all turtles?
    return event -> setShape(event);
  }

  private void setShape(ActionEvent event) {
    MenuItem item = (MenuItem) event.getSource();
    shapeIndex = menuBundle.getString(item.getText()).split("\\|")[0];
    notifyMediator();
  }

  /**
   * Returns the index of the chosen shape as a string. This is used in the CodeEntryBoxMediator
   * to set the shape to the right index using a command.
   * @return index of shape as a String to use in the command
   */
  public String getShapeIndex() {
    return shapeIndex;
  }
}
