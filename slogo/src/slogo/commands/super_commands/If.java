package slogo.commands.super_commands;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.Command;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the if command.
 */
public class If extends SuperCommand {

  /**
   * @param args     is a string [] of arguments.
   * @param commands is a list of subcommands. This constructor creates an if command object.
   */
  public If(String[] args, List<Command> commands) {
    super(args, commands);
    setNumExpectedArguments(1);
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the result of the last executed command. This method executes the if command on a list
   * of turtles.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();
    double arg = arguments.get(0);

    if (arg != 0) {
      return executeSubCommands(turtles, activeTurtles, vars);
    }

    return 0;
  }
}
