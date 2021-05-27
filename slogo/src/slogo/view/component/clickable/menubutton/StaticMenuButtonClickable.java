package slogo.view.component.clickable.menubutton;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;

/**
 * The StaticMenuButtonClickable class creates MenuButtonClickables that are based on a Resource
 * file that does not change.
 *
 * @author Bill Guo
 */
public abstract class StaticMenuButtonClickable extends MenuButtonClickable {

  protected ResourceBundle menuBundle;

  /**
   * Constructor of the StaticMenuButtonClickable.
   * @param image Icon for MenuButton
   * @param menuBundle Resource file that contains the labels and pertinent information for each
   *                   MenuItem
   */
  public StaticMenuButtonClickable(Image image, ResourceBundle menuBundle) {
    super(image);
    this.menuBundle = menuBundle;
    List<MenuItem> menuItems = makeMenuItems();
    getMenuButton().getItems().addAll(menuItems);
  }

  /**
   * Returns the List of MenuItems that the MenuButton is built off of. The list is made from the
   * menuBundle where the keys are the labels of the MenuButton and the values are important
   * information for the MenuButton to function.
   * @return
   */
  @Override
  protected List<MenuItem> makeMenuItems() {
    List<MenuItem> returnList = new ArrayList<>();
    String[] orderedArray = new String[menuBundle.keySet().size()];
    for (String key : menuBundle.keySet()) {
      // value is in the form [index, info]
      String[] value = menuBundle.getString(key).split("|");
      orderedArray[Integer.parseInt(value[0])] = key;
    }
    for (int index = 0; index < orderedArray.length; index++) {
      MenuItem menuItem = new MenuItem(orderedArray[index]);
      menuItem.setOnAction(onClick());
      menuItem.setId(index + "");
      returnList.add(menuItem);
    }
    return returnList;
  }
}
