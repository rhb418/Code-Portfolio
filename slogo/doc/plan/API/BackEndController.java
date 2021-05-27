///BackEndController: Responsible for communication between the front end and the back end by sending data to the front end when the slogo.model updates through command inputs

///Responsibilities: Returning the list of previous commands, returning the list of current variables, sending turtle data about the scene, returning the needed information to print to console

///Dependencies: Turtle, TurtleData, Command


public interface BackEndController{
  // returns a list of previous commands from the slogo.model to the view in string format
  public ImmutableList<String> getPreviousCommands();
  // returns a map of previous variables from the slogo.model to the view in string format
  public ImmutableMap<String, String> getCurrentVariables();
  // returns a map of previous user functions from the slogo.model to the view in string format
  public ImmutableMap<String, String> getPreviousFunctionsDefined();
  // returns a list of TurtleData to update the TurtleDisplay
  public List<TurtleData> getTurtleData(List<Turtle> turtles);
  // returns what should be outputted to console upon a command being run and resets so it is ready for the next console output
  public String getAndResetConsoleData();
  // returns the error to be display in the consoleDisplay
  public String throwError();
}
