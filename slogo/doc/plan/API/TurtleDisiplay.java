/**
 * this class stores attributes of the turtle display
 */

public interface TurtleDisplay{

  /**
   * updates turtle display from backend
   */
  protected void updateTurtleDisplay()

  /**
   * sets line color
   * @param color of line
   */
  protected void setLineColor(Color color)

  /**
   * sets background color
   * @param color of background
   */
  protected void setBackgroundColor(Color color)

  /**
   * sets turtle color
   * @param color of turtle
   */
  protected void setTurtleColor(Color color)

  /**
   * gets the gridpane of the simulation
   * @return gridpane
   */
  public GridPane getGridPane()

  /**
   * gets image of turtle
   */
  protected void getImageOfTurtle()
}