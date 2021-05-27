package slogo.commands;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, this class implements the make/set command
 */
public class Make extends Command {

  /**
   * @param args is the string [] of command arguments. This constructor creates a make command
   *             object.
   */
  public Make(String[] args) {
    super(args);
    setNumExpectedArguments(2);
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns a double value representing the result of the make command. This command creates a new
   * variable or assigns an existing variable to a new value.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<String> arguments = getArguments();

    String var = arguments.get(0);
    double value = Double.parseDouble(arguments.get(1));

    vars.put(var, value);
    return value;
  }
}
