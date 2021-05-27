package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.Command;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the for command.
 */
public class For extends SuperCommand {

  private HashMap<String, Double> myLocalVariables;

  /**
   * @param args     is the string [] of arguments.
   * @param commands is the list of subcommands. This constructor creates a for command object.
   */
  public For(String[] args, List<Command> commands) {
    super(args, commands);
    setNumExpectedArguments(4);
    myLocalVariables = new HashMap<>();
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the result of the last executed command. This method executes the for command on a
   * list of turtles.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<String> args = getArguments();
    String localVar = args.get(0);

    double start = Double.parseDouble(args.get(1));
    double end = Double.parseDouble(args.get(2));
    double increment = Double.parseDouble(args.get(3));
    int intStart = (int) start;
    int intEnd = (int) end;
    int intIncrement = (int) increment;

    double ret = 0;

    myLocalVariables.putAll(vars);
    myLocalVariables.put(localVar, (double) intStart);
    updateVariables(myLocalVariables);

    for (int k = intStart; k <= intEnd; k += intIncrement) {
      myLocalVariables.put(localVar, (double) k);
      ret = executeSubCommands(turtles, activeTurtles, myLocalVariables);
    }

    return ret;
  }
}
