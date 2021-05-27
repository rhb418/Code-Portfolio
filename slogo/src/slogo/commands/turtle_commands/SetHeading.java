package slogo.commands.turtle_commands;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the set heading command.
 */
public class SetHeading extends Command {

  /**
   * @param args is the string [] of command arguments. This constructor creates a setheading
   *             command object.
   */
  public SetHeading(String[] args) {
    super(args);
    setNumExpectedArguments(1);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns the distance the turtle rotated. This method sets the turtle's direction to an
   * absolute heading.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();
    double heading = arguments.get(0);
    return turtle.setHeading(heading);
  }

}
