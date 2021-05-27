package cellsociety.display;

import cellsociety.evolution.Evolution;
import javafx.scene.shape.Rectangle;

/**
 * Draws cells in Rectangular Shape
 *
 * Most comments are the same as its interface
 *
 *      Drawer myDrawer = new RectangleDrawer()
 *
 * Utilize the Displayer method paintShape for adding color and a handler to each cell shape
 *
 * @author Felix Jiang
 */
public class RectangleDrawer implements Drawer {

  /**
   * Display cells in rectangular shape
   *
   * @param myEvolution current Evolution that represents each cell's value
   * @param cellPaneWidth width of canvas to draw on
   * @param cellPaneHeight height of canvas to draw on
   * @param myDisplayer current window to display these drawings
   */
  @Override
  public void displayCurrentGeneration(Evolution myEvolution, double cellPaneWidth,
      double cellPaneHeight, Displayer myDisplayer) {
    int height = myEvolution.getCurrentGeneration().getHeight();
    int width = myEvolution.getCurrentGeneration().getWidth();
    double cellWidth = cellPaneWidth / width;
    double cellHeight = cellPaneHeight / height;

    for (int k = 0; k < height; k++) {
      for (int j = 0; j < width; j++) {
        Rectangle rect = new Rectangle(j * cellWidth, k * cellHeight, cellWidth, cellHeight);
        myDisplayer.paintShape(Math.min(cellWidth, cellHeight), k, j, rect);
      }
    }
  }
}
