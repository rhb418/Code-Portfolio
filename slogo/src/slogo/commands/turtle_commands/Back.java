package slogo.commands.turtle_commands;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, this class represents the back turtle command.
 */
public class Back extends Command {

  /**
   * @param args is the string [] of arguments. This constructor creates a new back command object.
   */
  public Back(String[] args) {
    super(args);
    setNumExpectedArguments(1);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns the distance the turtle moved. This method executes the turtle command back on a
   * single turtle.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();
    double distance = arguments.get(0);

    turtle.moveForward(-distance);

    return distance;
  }


}
