package slogo.view.mediator;

import java.util.ResourceBundle;
import slogo.view.component.Component;
import slogo.view.component.clickable.buttonclickable.LoadPreferencesButton;
import slogo.view.component.subview.ShapePalette;

/**
 * The ShapePaletteMediator class handles all components that interact with the ShapePalette.
 *
 * Classes that use this mediator include the LoadPreferencesButton.
 *
 * @author Bill Guo
 */
public class ShapePaletteMediator extends Mediator {

  private static final String METHOD_FILE = "slogo/resources/mediators/ShapePalette";

  private ShapePalette target;

  /**
   * Constructor of the ShapePaletteMediator.
   * @param shapePalette ShapePalette to change the shape palette of
   */
  public ShapePaletteMediator(ShapePalette shapePalette) {
    target = shapePalette;
    setMethodBundle(ResourceBundle.getBundle(METHOD_FILE));
  }

  /**
   * Changes the shape palette of ShapePalette when a new shape palette gets loaded in from the
   * LoadPreferencesButton.
   * @param component
   */
  protected void handleLoadPreferencesButton(Component component) {
    LoadPreferencesButton data = (LoadPreferencesButton) component;
    target.changeBundle(data.getShapePalettePath());
  }

}
