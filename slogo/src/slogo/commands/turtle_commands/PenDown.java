package slogo.commands.turtle_commands;

import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the pen down command.
 */
public class PenDown extends Command {

  /**
   * @param args is the string [] of command arguments. This constructor creates a pendown command
   *             object.
   */
  public PenDown(String[] args) {
    super(args);
    setNumExpectedArguments(0);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns 1. This class sets a turtles pen to be down.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    turtle.setPenState(true);
    return 1;
  }
}
