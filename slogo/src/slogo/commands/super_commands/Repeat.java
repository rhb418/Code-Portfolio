package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.Command;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the repeat command. This class is well designed
 * because it takes advantage of its superclass methods and is very concise with a clear single
 * responsiblity.
 */
public class Repeat extends SuperCommand {

  /**
   * @param args     is the list of arguments.
   * @param commands is the list of subcommands. This constructor creates a repeat command object.
   */
  public Repeat(String[] args, List<Command> commands) {
    super(args, commands);
    setNumExpectedArguments(1);
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the result of the last executed command. This method executes the repeat command on a
   * list of turtles.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();
    double arg = arguments.get(0);
    int numRuns = (int) arg;
    double ret = 0;

    for (int k = 0; k < numRuns; k++) {
      ret = executeSubCommands(turtles, activeTurtles, vars);
    }

    return ret;
  }
}
