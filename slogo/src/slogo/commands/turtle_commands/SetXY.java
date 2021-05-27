package slogo.commands.turtle_commands;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the setxy command.
 */
public class SetXY extends Command {

  /**
   * @param args is the string [] of command arguments. This constructor creates a setxy command
   *             object.
   */
  public SetXY(String[] args) {
    super(args);
    setNumExpectedArguments(2);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns the distance the turtle moved. This method sets the turtle to an absolute position.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();
    double x = arguments.get(0);
    double y = arguments.get(1);

    return turtle.setPosition(x, y);
  }

}
