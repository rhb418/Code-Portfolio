package slogo.commands.turtle_commands;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.commands.Command;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the towards command.
 */
public class Towards extends Command {

  /**
   * @param args is the string [] of command arguments. This constructor creates a towards command
   *             object.
   */
  public Towards(String[] args) {
    super(args);
    setNumExpectedArguments(2);
    setIsTurtleCommand(true);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns the angular distance the turtle moved. This method executes the towards command on a
   * single turtle.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();
    double newX = arguments.get(0);
    double newY = arguments.get(1);

    double xVec = newX - turtle.getXPosition();
    double yVec = newY - turtle.getYPosition();

    double angle = Math.toDegrees(Math.atan2(yVec, xVec));

    if (angle < 0) {
      angle += 360;
    }

    return turtle.setHeading(angle);
  }
}
