package slogo.view.component.clickable.menubutton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import slogo.controller.BackEndController;

/**
 * The ColorMenuButton class allows for the selection of a color from the ColorPalette based on
 * the index of the color.
 *
 * @author Bill Guo
 */
public abstract class ColorMenuButton extends DynamicMenuButtonClickable {

  private String colorIndex;

  /**
   * Constructor of the ColorMenuButton.
   * @param image Icon for MenuButton
   * @param backEndController Needed for the dynamically updating list of color indices available
   */
  public ColorMenuButton(Image image, BackEndController backEndController) {
    super(image, backEndController);
    List<MenuItem> menuItems = makeMenuItems();
    getMenuButton().getItems().addAll(menuItems);
  }

  /**
   * Returns the list of MenuItems that the MenuButton is built off of. Uses the
   * BackEndController to get the current color palette, sorts the indices, and then sets each
   * MenuItem with an onClick action.
   * @return List of color options
   */
  @Override
  protected List<MenuItem> makeMenuItems() {
    List<MenuItem> returnList = new ArrayList<>();
    Map<Double, double[]> palettes = backEndController.getTurtleData().get(0).getPalettes();
    List<String> sortedList = new ArrayList<>();
    for (Double index : palettes.keySet()) {
      sortedList.add(index.intValue() + "");
    }
    Collections.sort(sortedList);
    for (String index : sortedList) {
      MenuItem menuItem = new MenuItem(index);
      menuItem.setOnAction(onClick());
      menuItem.setId("BackgroundColorMenuButton" + index);
      returnList.add(menuItem);
    }
    return returnList;
  }

  /**
   * Clears the current menuButton and updates it with the current palette.
   */
  @Override
  public void update() {
    getMenuButton().getItems().clear();
    getMenuButton().getItems().addAll(makeMenuItems());
  }

  /**
   * Returns the EventHandler of each MenuItem. In this case, colorIndex will get set to the
   * index of the chosen color.
   * @return EventHandler to change colorIndex to chosen value
   */
  @Override
  protected EventHandler<ActionEvent> onClick() {
    return event -> setColor(event);
  }

  private void setColor(ActionEvent event) {
    MenuItem item = (MenuItem) event.getSource();
    colorIndex = item.getText();
    notifyMediator();
  }

  /**
   * Returns the index of the chosen color as a string. This is used in the CodeEntryBoxMediator
   * to set the color to the right index using a command.
   * @return index of color as a String to use in the command
   */
  public String getColor() {
    return colorIndex;
  }
}
