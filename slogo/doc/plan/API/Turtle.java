//This class represents the state of the turtle

public interface Turtle{

  //Returns a list of LineCoord objects specifying the path of the turtle
  public List<LineCoords> getPath();  

  //move the turtle forward a certain distance
  public void moveForward(double distance); 

  //Rotates the turtle a certain number of degrees
  public void rotate(double degrees);

  //Returns the x position of the turtle 
  public double getXPosition();

  //Returns the y position of the turtle
  public double getYPosition();

  //Sets the position of the turtle to a particular spot
  public void setPosition(double xDelta, double yDelta);

  //Returns the direction the turtle is pointing
  public double getDirection();

  //Sets the turtle to draw mode
  public void setDrawn(boolean drawn);
}