package slogo.commands;

import static slogo.commands.CommandConstants.HEADING;
import static slogo.commands.CommandConstants.PENCOLOR;
import static slogo.commands.CommandConstants.PENDOWN;
import static slogo.commands.CommandConstants.SHAPE;
import static slogo.commands.CommandConstants.SHOWING;
import static slogo.commands.CommandConstants.TURTLEID;
import static slogo.commands.CommandConstants.XCOR;
import static slogo.commands.CommandConstants.YCOR;

import java.util.HashMap;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, this class implements all of the turtle query commands.
 */
public class TurtleQueries extends Command {

  private int myOperation;
  private HashMap<Integer, Double> myTurtleQueries;

  /**
   * @param args   is the string [] of arguments.
   * @param opType is the type of the turtle query being conducted. This constructor creates a
   *               turtle query command object.
   */
  public TurtleQueries(String[] args, int opType) {
    super(args);
    setNumExpectedArguments(0);
    setIsTurtleCommand(true);
    myOperation = opType;
    myTurtleQueries = new HashMap<>();
    setName("TQ");
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns the double value of the executed turtle query. This method executes a particular
   * turtle query and returns the result.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    updateVariables(vars);
    queryTurtle(turtle);

    return myTurtleQueries.get(myOperation);
  }

  private void queryTurtle(Turtle turtle) {
    double xcorResult = turtle.getXPosition();
    double ycorResult = turtle.getYPosition();
    double headingResult = turtle.getDirection();
    double penDownResult = turtle.getPenState();
    double showingResult = turtle.getShowing();
    double shapeResult = turtle.getShape();
    double penColorResult = turtle.getPenColor();
    double IDResult = turtle.getID();

    myTurtleQueries.put(XCOR, xcorResult);
    myTurtleQueries.put(YCOR, ycorResult);
    myTurtleQueries.put(HEADING, headingResult);
    myTurtleQueries.put(PENDOWN, penDownResult);
    myTurtleQueries.put(SHOWING, showingResult);
    myTurtleQueries.put(SHAPE, shapeResult);
    myTurtleQueries.put(PENCOLOR, penColorResult);
    myTurtleQueries.put(TURTLEID, IDResult);
  }
}
