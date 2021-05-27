package slogo.turtle;

/**
 * @author Robert Barnette, This class keeps track of lines using their start and end points.
 */
public class LineCoords {

  double myStartX;
  double myEndX;
  double myStartY;
  double myEndY;

  /**
   * @param startX is the starting x coordinate of the line.
   * @param startY is the starting y coordinate of the line.
   * @param endX   is the ending x coordinate of the line.
   * @param endY   is the ending y coordinate of the line. This constructor creates a linecoords
   *               object.
   */
  public LineCoords(double startX, double startY, double endX, double endY) {
    myStartX = startX;
    myStartY = startY;
    myEndX = endX;
    myEndY = endY;

  }

  /**
   * @returns the starting x coordinate
   */
  public double getStartX() {
    return myStartX;
  }

  /**
   * @returns the ending x coordinate
   */
  public double getEndX() {
    return myEndX;
  }

  /**
   * @returns the starting y coordinate
   */
  public double getStartY() {
    return myStartY;
  }

  /**
   * @returns the ending y coordinate
   */
  public double getEndY() {
    return myEndY;
  }


}
