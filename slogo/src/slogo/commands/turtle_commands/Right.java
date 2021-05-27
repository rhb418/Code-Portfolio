package slogo.commands.turtle_commands;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the right command.
 */
public class Right extends Command {
  /**
   * @param args is the string [] of command arguments. This constructor creates a right command
   *             object.
   */
  public Right(String[] args) {
    super(args);
    setNumExpectedArguments(1);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns the angle the turtle rotated. This method rotates a turtle a given number of degrees
   * to the right.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();

    double angle = arguments.get(0);
    turtle.rotate(-angle);

    return angle;
  }

}
