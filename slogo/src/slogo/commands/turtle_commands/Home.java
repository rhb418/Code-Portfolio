package slogo.commands.turtle_commands;

import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the home command.
 */
public class Home extends Command {

  /**
   * @param args is the string [] of command arguments. This constructor creates a home command
   *             object.
   */
  public Home(String[] args) {
    super(args);
    setNumExpectedArguments(0);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns the distance the turtle moved. This method moves a turtle to the home position.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    return turtle.setPosition(0, 0);
  }

}
