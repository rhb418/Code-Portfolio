package slogo.view.component.subview;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.layout.Pane;
import slogo.controller.BackEndController;
import slogo.view.Observer;

/**
 * The ColorPalette class represents a view of the available colors that can be chosen for the
 * line color and background color.
 *
 * Updates after each command by reading the BackEndController for the new color palette. Knows
 * when to update as it is an observer of the CodeEntryBox.
 *
 * @author Bill Guo
 */
public class ColorPalette extends ListSubview implements Observer {

  private Pane content;

  /**
   * Constructor of the ColorPalette
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labelBundle Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   */
  public ColorPalette(String labelKey, ResourceBundle labelBundle,
      BackEndController backEndController) {
    super(labelKey, labelBundle, backEndController);
    makeContent();
  }

  /**
   * Creates the content of the Subview, which is a ListView of the available colors.
   */
  @Override
  protected void makeContent() {
    content = getContent();
    content.getStyleClass().add("colorpalette-content");
    content.getChildren().add(listView);
    update();
  }

  /**
   * Updates the color palette by reading the BackEndController after each command.
   */
  @Override
  public void update() {
    observableList.clear();
    Map<Double, double[]> palette = backEndController.getTurtleData().get(0).getPalettes();
    for (Double index : palette.keySet()) {
      StringBuilder colorInfo = new StringBuilder();
      colorInfo.append(labelBundle.getString("Index") + " " + index.intValue() + ": ");
      colorInfo.append("R=" + (int) palette.get(index)[0] + " ");
      colorInfo.append("G=" + (int) palette.get(index)[1] + " ");
      colorInfo.append("B=" + (int) palette.get(index)[2]);
      observableList.add(colorInfo.toString());
    }
    Collections.sort(observableList);
  }
}
