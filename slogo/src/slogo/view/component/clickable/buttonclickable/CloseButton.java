package slogo.view.component.clickable.buttonclickable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import slogo.view.component.subview.Subview;

/**
 * The CloseButton class hides the content of a chosen Subview.
 *
 * CloseButtons can only be created by a Subview, as CloseButtons need a reference to the Subview
 * that they are closing.
 *
 * @author Bill Guo
 */
public class CloseButton extends ButtonClickable {

  private Subview subview;

  /**
   * Constructor of a CloseButton.
   * @param image Icon for the Button
   * @param subviewToClose Subview whose content will be hidden when pressed
   */
  public CloseButton(Image image, Subview subviewToClose) {
    super(image);
    subview = subviewToClose;
  }

  /**
   * Returns the EventHandler of a CloseButton. In this case, the button will hide the content of
   * a Subview.
   * @return EventHandler to hide the content of a Subview
   */
  @Override
  protected EventHandler<ActionEvent> onClick() {
    return event -> {
      subview.getContent().setVisible(false);
      subview.getContent().setManaged(false);
    };
  }
}
