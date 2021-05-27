package slogo.view.component.clickable.buttonclickable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import slogo.view.component.subview.Subview;

/**
 * An OpenButton shows the content of a Subview.
 *
 * OpenButtons can only be created by a Subview, as OpenButtons need a reference to the Subview
 * that they are opening.
 *
 * @author Bill Guo
 */
public class OpenButton extends ButtonClickable {

  private Subview subview;

  /**
   * Constructor of a CloseButton.
   * @param image Icon for the Button
   * @param subviewToOpen Subview whose content will be shown when pressed
   */
  public OpenButton(Image image, Subview subviewToOpen) {
    super(image);
    subview = subviewToOpen;
  }

  /**
   * Returns the EventHandler of a CloseButton. In this case, the button will show the content of
   * a Subview.
   * @return EventHandler to show the content of a Subview
   */
  @Override
  protected EventHandler<ActionEvent> onClick() {
    return event -> {
      subview.getContent().setVisible(true);
      subview.getContent().setManaged(true);
    };
  }

}
