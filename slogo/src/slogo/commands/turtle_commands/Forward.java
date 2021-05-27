package slogo.commands.turtle_commands;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, this class represents the forward command.
 */
public class Forward extends Command {

  /**
   * @param args is the string [] of command arguments. This constructor creates a forward command
   *             object.
   */
  public Forward(String[] args) {
    super(args);
    setNumExpectedArguments(1);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns the distance the turtle moved. This method moves a turtle forward a certain distance.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();
    double distance = arguments.get(0);

    turtle.moveForward(distance);

    return distance;
  }

}
