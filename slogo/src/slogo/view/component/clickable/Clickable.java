package slogo.view.component.clickable;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import slogo.view.component.Component;
import slogo.view.mediator.Mediator;

/**
 * The Clickable class is an extension of the component class can change other components based
 * on a mouse click.
 *
 * Each clickable also has an icon to use as its image for either a Button or MenuButton.
 *
 * @author Bill Guo
 */
public abstract class Clickable extends Component {

  protected ImageView icon;
  protected List<Mediator> mediators;

  /**
   * Constructor of the Clickable
   * @param image Icon for the clickable
   */
  public Clickable(Image image) {
    icon = new ImageView(image);
    icon.getStyleClass().add("clickable-image");
    mediators = new ArrayList<>();
  }

  /**
   * Returns the EventHandler that is called when the Clickable is clicked. Subclasses need to
   * implement this method in order to update another component through its mediator when the
   * clickable is clicked.
   * @return EventHandler to call when clicked
   */
  protected abstract EventHandler<ActionEvent> onClick();

}
