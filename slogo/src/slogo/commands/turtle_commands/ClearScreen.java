package slogo.commands.turtle_commands;

import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class represents the clear screen command.
 */
public class ClearScreen extends Command {

  /**
   * @param args is the string [] of arguments for this command. This constructor creates a new
   *             clear screen command object.
   */
  public ClearScreen(String[] args) {
    super(args);
    setNumExpectedArguments(0);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns a double value representing the distance the turtle moved. This method executes the
   * clear screen command on a single turtle.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    double distance = turtle.setPosition(0, 0);
    turtle.clear();
    return distance;
  }

}
