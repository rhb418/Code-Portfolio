package slogo.commands.super_commands;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.Command;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the do times command.
 */
public class DoTimes extends SuperCommand {

  private HashMap<String, Double> myLocalVariables;

  /**
   * @param args     is the string [] of arguments.
   * @param commands is the list of subcommands. This constructor creates a dotimes command object.
   */
  public DoTimes(String[] args, List<Command> commands) {
    super(args, commands);
    setNumExpectedArguments(2);
    myLocalVariables = new HashMap<>();
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the result of the last command that is run. This method executes the do times command
   * on a list of turtles.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<String> args = getArguments();
    String localVar = args.get(0);
    double limit = Double.parseDouble(args.get(1));
    int intLimit = (int) limit;
    double ret = 0;

    myLocalVariables.putAll(vars);
    myLocalVariables.put(localVar, 1.0);
    updateVariables(myLocalVariables);

    if (intLimit < 1) {
      return ret;
    }

    for (int k = 1; k <= limit; k++) {
      myLocalVariables.put(localVar, (double) k);
      ret = executeSubCommands(turtles, activeTurtles, myLocalVariables);
    }

    return ret;
  }
}
