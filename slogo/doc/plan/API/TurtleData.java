//TurtleData: Data class that houses the information that should be drawn on the view. Each turtle has its own data to update and be able to send to the view through the controller without the view being able to know the turtle's implementation.

//Responsibilities: Keeping track if the turtle is shown, its location and orientation, and any lines that are newly drawn

//Dependencies: None


public interface TurtleData {
  // returns if the turtle is displayed
  public boolean turtleDisplayed();
  // returns the x and y coordinate of the turtle's location
  public Pair<Double, Double> getCurrentTurtleLocation();
  // returns the degree orientation of the turtle
  public double getOrientation();
  // returns the list of LineCoords so they can be drawn on the view
  public List<LineCoords> pathToDraw();
}
