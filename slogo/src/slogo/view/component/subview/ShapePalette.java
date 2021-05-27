package slogo.view.component.subview;

import java.util.ResourceBundle;
import javafx.scene.layout.Pane;
import slogo.controller.BackEndController;

/**
 * The ShapePalette class represents a view of the available shapes that can be chosen for the
 * turtles.
 *
 * Can be updated by loading in a new preference file to change the shape palette through its
 * mediator.
 *
 * @author Bill Guo
 */
public class ShapePalette extends ListSubview {

  private ResourceBundle shapeBundle;
  private Pane content;

  /**
   * Constructor of the ShapePalette.
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labelBundle Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   * @param shapeBundle Resource file that contains the shape palette
   */
  public ShapePalette(String labelKey, ResourceBundle labelBundle,
      BackEndController backEndController, ResourceBundle shapeBundle) {
    super(labelKey, labelBundle, backEndController);
    this.shapeBundle = shapeBundle;
    makeContent();
  }

  /**
   * Makes the content of the Subview, which is a ListView that shows the available shapes.
   */
  @Override
  protected void makeContent() {
    content = getContent();
    content.getStyleClass().add("shapepalette-content");
    content.getChildren().add(listView);
    setContent();
  }

  private void setContent() {
    observableList.clear();
    String[] orderedList = new String[shapeBundle.keySet().size()];
    for (String key : shapeBundle.keySet()) {
      // value is in the form [index, info]
      String[] value = shapeBundle.getString(key).split("|");
      orderedList[Integer.parseInt(value[0])] = key;
    }
    for (int index = 0; index < orderedList.length; index++) {
      StringBuilder shapeInfo = new StringBuilder();
      shapeInfo.append(labelBundle.getString("Index") + " " + index + ": ");
      shapeInfo.append(orderedList[index]);
      observableList.add(shapeInfo.toString());
    }
  }

  /**
   * Returns the path to the current Resource file that represents the shapes. Used by the
   * SavePreferencesButton mediator.
   * @return String that represents the file path to the current shape palette
   */
  public String getPropertiesFile() {
    return shapeBundle.getBaseBundleName();
  }

  /**
   * Changes the shape palette. Used by its mediator to change the shape palette when other
   * components change the palette.
   * @param pathToBundle String that represents the filepath to the new bundle
   */
  public void changeBundle(String pathToBundle) {
    shapeBundle = ResourceBundle.getBundle(pathToBundle);
    setContent();
    notifyMediator();
  }
}
