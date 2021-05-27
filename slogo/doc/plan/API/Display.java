//display class displays the turtle and simulation data

public interface Display()  {
  //updates the turtle view and the console view
  protected void updateAllViews(List<TurtleData> turtles, String command, String consoleOutput);

}