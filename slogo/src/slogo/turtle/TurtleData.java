package slogo.turtle;

import java.util.List;
import java.util.Map;

/**
 * @author Robert Barnette, This class is used to send data about the turtle to the front end
 * without actually sending the turtle itself.
 */
public class TurtleData {

  private List<LineCoords> linesToDraw;
  private double xPos;
  private double yPos;
  private double orientation;
  private int ID;
  private boolean isShown;
  private boolean penDown;

  private double background;
  private double penColor;
  private double shape;
  private Map<Double, double[]> palettes;
  private double penSize;

  /**
   * @param lines       is the path of the turtle.
   * @param id          is the id of the turtle.
   * @param xPosition   is the x position of the turtle.
   * @param yPosition   is the y position of the turtle.
   * @param orientation is the heading of the turtle.
   * @param shown       is the boolean determining if the turtle is visible.
   * @param pen         is the boolean determining if the turtle pen is down.
   * @param bg          is the background index of the turtle.
   * @param pc          is the pen color index of the turtle
   * @param s           is the shape index of the turtle.
   * @param pal         is the map of palettes of the turtle.
   * @param ps          is the pen size of the turtle. This constructor creates a turtledata
   *                    object.
   */
  public TurtleData(List<LineCoords> lines, int id, double xPosition, double yPosition,
      double orientation, boolean shown, boolean pen, double bg, double pc, double s,
      Map<Double, double[]> pal, double ps) {
    linesToDraw = lines;
    xPos = xPosition;
    yPos = yPosition;
    this.orientation = orientation;
    isShown = shown;
    penDown = pen;
    palettes = pal;
    background = bg;
    penColor = pc;
    shape = s;
    penSize = ps;
    ID = id;
  }

  /**
   * @returns the turtles path as a list of linecoords
   */
  public List<LineCoords> pathToDraw() {
    return linesToDraw;
  }

  /**
   * @returns the x position of the turtle
   */
  public double getXEndPosition() {
    return xPos;
  }

  /**
   * @returns the y position of the turtle
   */
  public double getYEndPosition() {
    return yPos;
  }

  /**
   * @returns the heading of the turtle
   */
  public double getOrientation() {
    return this.orientation;
  }

  /**
   * @returns a boolean determining if the turtle is visible
   */
  public boolean isShown() {
    return isShown;
  }

  /**
   * @returns a boolean determining if the pen is down
   */
  public boolean isPenDown() {
    return penDown;
  }

  /**
   * @returns the ID of the turtle
   */
  public int getID() {
    return ID;
  }

  /**
   * @returns the background index of the turtle
   */
  public double getBackground() {
    return background;
  }

  /**
   * @returns the pen color index of the turtle
   */
  public double getPenColor() {
    return penColor;
  }

  /**
   * @returns the shape index of the turtle
   */
  public double getShape() {
    return shape;
  }

  /**
   * @returns the turtle's available palettes
   */
  public Map<Double, double[]> getPalettes() {
    return palettes;
  }

  /**
   * @returns the turtle's pen size
   */
  public double getPenSize() {
    return penSize;
  }
}
