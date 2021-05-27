package slogo.commands.turtle_commands;

import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the show turtle command.
 */
public class ShowTurtle extends Command {

  /**
   * @param args is the string [] of command arguments. This constructor creates a showturtle
   *             command object.
   */
  public ShowTurtle(String[] args) {
    super(args);
    setNumExpectedArguments(0);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns 1. This method sets the turtle's view state to showing.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    turtle.setShowing(true);
    return 1;
  }
}
