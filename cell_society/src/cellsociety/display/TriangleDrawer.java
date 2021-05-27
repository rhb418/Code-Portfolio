package cellsociety.display;

import cellsociety.evolution.Evolution;
import javafx.scene.shape.Polygon;

/**
 * Draws cells in Triangle Shape
 *
 * Most comments are the same as its interface
 *
 *      Drawer myDrawer = new TriangleDrawer()
 *
 * Utilize the Displayer method paintShape for adding color and a handler to each cell shape
 *
 * @author Felix Jiang
 */
public class TriangleDrawer implements Drawer {

  /**
   * Displays cells in Triangular shape
   *
   * Must coordinate with Evolution to see how the cells are organized, because the orientation
   * of a triangle being up or down effects where it's neighbors are
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
    double cellWidth = cellPaneWidth / (width / 2.0 + 0.5);
    double cellHeight = cellPaneHeight / (height);
    double xChange = cellWidth / 2;
    double yChange = cellHeight / 2;

    for (int k = 0; k < height; k++) {
      for (int j = 0; j < width; j++) {
        double currY = yChange + cellHeight * k;
        double currX = xChange + xChange * j;
        Polygon triangle = createTriangle(currX, currY, cellWidth, cellHeight, (j + k) % 2 == 0);
        myDisplayer.paintShape(Math.min(cellWidth, cellHeight), k, j, triangle);
      }
    }
  }

  private Polygon createTriangle(double centerX, double centerY, double width, double height,
      boolean up) {
    double xChange = width / 2;
    double yChange = height / 2;
    int[] xc = {-1, 0, 1};
    int[] yc = {-1, 1, -1};
    if (up) {
      int[] temp = {1, -1, 1};
      yc = temp;
    }
    Polygon triangle = new Polygon();
    for (int i = 0; i < 3; i++) {
      triangle.getPoints().add(centerX + xc[i] * xChange);
      triangle.getPoints().add(centerY + yc[i] * yChange);
    }
    return triangle;
  }
}
