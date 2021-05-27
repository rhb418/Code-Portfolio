package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.Command;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the ask command.
 */
public class Ask extends SuperCommand {

  /**
   * @param args     is the string [] of arguments.
   * @param commands is the list of subcommands. This constructor creates an ask command object.
   */
  public Ask(String[] args, List<Command> commands) {
    super(args, commands);
    setNumExpectedArguments(args.length);
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the value of the last executed command. This method executes the ask command on a list
   * of turtles.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    List<Integer> localActiveTurtles = new ArrayList<>();
    List<Double> args = processArgs();
    List<Integer> integerArgs = new ArrayList<>();

    for (int k = 0; k < args.size(); k++) {
      integerArgs.add(args.get(k).intValue());
    }

    for (int i = 0; i < integerArgs.size(); i++) {
      if (integerArgs.get(i) >= turtles.size()) {
        continue;
      }
      localActiveTurtles.add(integerArgs.get(i));
    }

    return executeSubCommands(turtles, localActiveTurtles, vars);
  }
}
