package cellsociety.display;

import static cellsociety.Constants.HEXAGONS_PER_WIDTH;
import static cellsociety.Constants.HEXAGON_HALF;
import static cellsociety.Constants.HEXAGON_HEIGHT_MUL;
import static cellsociety.Constants.HEXAGON_WIDTH_MUL;
import static cellsociety.Constants.ODD;

import cellsociety.evolution.Evolution;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Polygon;


/**
 * Draws cells in Hexagon Shape
 *
 * Most comments are the same as its interface
 *
 *      Drawer myDrawer = new HexagonDrawer()
 *
 * Utilize the Displayer method paintShape for adding color and a handler to each cell shape
 *
 * @author Felix Jiang
 */
public class HexagonDrawer implements Drawer {

  /**
   * Displays cells in Hexagonal shape
   *
   * Must calculate height and weight strategically based on some math and handling the hexagons to
   * be displayed right on the edge
   *
   * Depends on how Evolution groups the cells in their grid
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
    double cellWidth = cellPaneWidth / (HEXAGONS_PER_WIDTH * width + HEXAGON_HALF);
    double cellHeight = cellPaneHeight / (height + HEXAGON_HALF);
    double xChange = cellWidth / 2.0;
    double yChange = cellHeight / 2.0;
    for (int k = 0; k < height; k++) {
      for (int j = 0; j < width; j++) {
        double currY = HEXAGON_HEIGHT_MUL * yChange * (k + 1);
        int evenOrOdd = j % 2;
        currY = evenOrOdd == ODD ? currY - yChange : currY;
        double currX = cellWidth + HEXAGON_WIDTH_MUL * xChange * j;
        Polygon hexagon = createHexagon(currX, currY, cellWidth, cellHeight);
        myDisplayer.paintShape(cellWidth, k, j, hexagon);
      }
    }
  }

  private Polygon createHexagon(double centerX, double centerY, double width, double height) {
    double xChange = width / 2.0;
    double yChange = height / 2.0;
    Polygon hexagon = new Polygon();
    List<Double> points = new ArrayList<>();
    int[] xc = {-2, -1, 1, 2, 1, -1};
    int[] yc = {0, -1, -1, 0, 1, 1};
    for (int i = 0; i < 6; i++) {
      points.add(centerX + xc[i] * xChange);
      points.add(centerY + yc[i] * yChange);
    }
    hexagon.getPoints().addAll(points);
    return hexagon;
  }
}
