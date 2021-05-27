package slogo.commands.turtle_commands;

import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the hide turtle command.
 */
public class HideTurtle extends Command {

  /**
   * @param args is the string [] of command arguments. This constructor creates a hide turtle
   *             command object.
   */
  public HideTurtle(String[] args) {
    super(args);
    setNumExpectedArguments(0);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns 0. This method hides a particular turtle from view.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    turtle.setShowing(false);
    return 0;
  }

}
