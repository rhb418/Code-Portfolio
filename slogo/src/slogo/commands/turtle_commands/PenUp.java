package slogo.commands.turtle_commands;

import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the pen up command.
 */
public class PenUp extends Command {

  /**
   * @param args is the string [] of command arguments. This constructor creates a penup command
   *             object.
   */
  public PenUp(String[] args) {
    super(args);
    setNumExpectedArguments(0);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns 0. This sets a turtle's pen to be up.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    turtle.setPenState(false);
    return 0;
  }
}
