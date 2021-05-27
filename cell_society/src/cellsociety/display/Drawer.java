package cellsociety.display;

import cellsociety.evolution.Evolution;

/**
 * Template for anything that draws how the cells look on a window
 *
 * Assumes the passed in values are all correct and are what the user wants to see
 *
 * This interface itself doesn't depend on specifics, besides all of the passed in arguments being
 * initialized correctly
 *
 *      Drawer myDrawer = new ... // anything that implements this interface
 *
 * Utilize the Displayer method paintShape for adding color and a handler to each cell shape
 *
 * @author Felix Jiang
 */
public interface Drawer {

  /**
   * Displays the current generation of the simulation in whatever way the implementing class desires
   *
   * Assumes all given values are correct and a canvas large enough based on the height and width
   * exist for the shapes
   *
   * Drawing of shapes resizes to canvas size
   *
   *
   * @param myEvolution current Evolution that represents each cell's value
   * @param cellPaneWidth width of canvas to draw on
   * @param cellPaneHeight height of canvas to draw on
   * @param myDisplayer current window to display these drawings
   */
  void displayCurrentGeneration(Evolution myEvolution, double cellPaneWidth,
      double cellPaneHeight, Displayer myDisplayer);
}
