package slogo.turtle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Robert Barnette, This class implements the turtle and tracks its state.
 */
public class Turtle {

  private List<LineCoords> myPath;
  private double myX;
  private double myY;
  private int myID;
  private double myHeading;
  private boolean myPenDown;
  private boolean myShown;

  //Display Data
  private double myBackground;
  private double myPenColor;
  private double myShape;
  private Map<Double, double[]> myPalettes;
  private double myPenSize;


  /**
   * @param id is the ID of the newly created turtle. This constructor creates a new turtle object.
   */
  public Turtle(int id) {
    myID = id;
    myX = 0;
    myY = 0;
    myHeading = 0;
    myShape = 0;
    myPenColor = 4;
    myBackground = 0;
    myPenDown = true;
    myShown = true;
    myPenSize = 1;
    myPath = new ArrayList<>();
    myPalettes = new HashMap<>();
  }


  /**
   * @param distance is the distance the turtle is being moved. This method moves the turtle forward
   *                 a given distance.
   */
  public void moveForward(double distance) {
    double newX = myX + distance * Math.cos(Math.toRadians(myHeading));
    double newY = myY + distance * Math.sin(Math.toRadians(myHeading));

    updatePath(newX, newY);

    myX = newX;
    myY = newY;
  }

  /**
   * @param degrees is the number of degrees the turtle is being rotated. This method rotates the
   *                turtle a given number of degrees.
   */
  public void rotate(double degrees) {
    double newHeading = (myHeading + degrees % 360) % 360;
    if (newHeading < 0) {
      newHeading += 360;
    }
    myHeading = newHeading;
  }

  /**
   * @param degrees is the heading the turtle is being set to.
   * @returns the number of degrees the turtle rotated. This method sets the turtle to an absolute
   * heading.
   */
  public double setHeading(double degrees) {
    degrees = degrees % 360;
    if (degrees < 0) {
      degrees += 360;
    }
    double degreesMoved = Math.abs(myHeading - degrees);
    myHeading = degrees;
    return degreesMoved;
  }

  /**
   * @returns the x position of the turtle
   */
  public double getXPosition() {
    return myX;
  }

  /**
   * @returns the y position of the turtle
   */
  public double getYPosition() {
    return myY;
  }


  /**
   * @param x is the x coordinate of the position the turtle is being set to.
   * @param y is the y coordinate of the position the turtle is being set to.
   * @returns the distance the turtle moved. This method sets the turtle to an absolute position.
   */
  public double setPosition(double x, double y) {
    updatePath(x, y);
    double dist = Math.sqrt(Math.pow(myX - x, 2) + Math.pow(myY - y, 2));

    myX = x;
    myY = y;

    return dist;
  }

  //Updates the path of the turtle when it is moved
  private void updatePath(double x, double y) {
    if (myPenDown) {
      LineCoords newLine = new LineCoords(myX, myY, x, y);
      myPath.add(newLine);
    }
  }

  /**
   * @returns the angular direction the turtle is pointing
   */
  public double getDirection() {
    return myHeading;
  }

  /**
   * @param drawn is the boolean determining if the turtle's pen is down This method sets the state
   *              of the turtle's pen to up or down.
   */
  public void setPenState(boolean drawn) {
    myPenDown = drawn;
  }

  /**
   * @returns the state of the turtles pen (1=down 0=up)
   */
  public double getPenState() {
    if (myPenDown) {
      return 1.0;
    }
    return 0;
  }

  /**
   * @returns whether or not the turtle is showing
   */
  public double getShowing() {
    if (myShown) {
      return 1.0;
    }
    return 0;
  }

  /**
   * @param val is the boolean determining if the turtle is visible or not. This method sets the
   *            turtle to visible or hidden.
   */
  public void setShowing(boolean val) {
    myShown = val;
  }

  public void clear() {
    myPath.clear();
    myX = 0;
    myY = 0;
    myHeading = 0;
  }

  /**
   * @returns a turtledata object representing the current state of the turtle
   */
  public TurtleData getTurtleData() {
    return new TurtleData(myPath, myID, myX, myY, myHeading, myShown, myPenDown, myBackground,
        myPenColor, myShape, myPalettes, myPenSize);
  }

  /**
   * @param td is the turtledata that the turtle is being set to. This method sets a turtle's state
   *           to the state given by the turtledata object td.
   */
  public void setTurtleData(TurtleData td) {
    myPath = td.pathToDraw();
    myX = td.getXEndPosition();
    myY = td.getYEndPosition();
    myHeading = td.getOrientation();
    myShown = td.isShown();
    myPenDown = td.isPenDown();
    myPalettes = td.getPalettes();
    myBackground = td.getBackground();
    myPenColor = td.getPenColor();
    myShape = td.getShape();
    myPenSize = td.getPenSize();

  }

  /**
   * @returns the background index of the turtle
   */
  public double getBackground() {
    return myBackground;
  }

  /**
   * @param background is the index of the background that the turtle is being set to.
   */
  public void setBackground(double background) {
    this.myBackground = background;
  }

  /**
   * @returns the pen color index of the turtle.
   */
  public double getPenColor() {
    return myPenColor;
  }

  /**
   * @param penColor is the color index that the pen color is being set to.
   */
  public void setPenColor(double penColor) {
    this.myPenColor = penColor;
  }

  /**
   * @returns the shape index of the turtle
   */
  public double getShape() {
    return myShape;
  }

  /**
   * @param shape is the shape index the turtle is being set to.
   */
  public void setShape(double shape) {
    this.myShape = shape;
  }

  /**
   * @param index is the index of the palette
   * @param r     is the red color value.
   * @param g     is the green color value.
   * @param b     is the blue color value. This method creates a new color palette and adds it to
   *              the map of palettes.
   */
  public void setPalette(double index, double r, double g, double b) {
    myPalettes.put(index, new double[]{r, g, b});
  }

  /**
   * @param palettes is the initial set of palettes that the turtle is given
   */
  public void initializePalettes(Map<Double, double[]> palettes) {
    myPalettes = palettes;
  }

  /**
   * @param penSize is the pen size the turtle is being set to
   */
  public void setPenSize(double penSize) {
    this.myPenSize = penSize;
  }

  /**
   * @returns the ID of the turtle
   */
  public int getID() {
    return myID;
  }

}
