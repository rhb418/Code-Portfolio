package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.Command;
import slogo.commands.SuperCommand;
import slogo.model.CommandExecutor;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the ask with command.
 */
public class AskWith extends SuperCommand {

  private List<Command> myCondition;

  /**
   * @param args      is the String [] of arguments.
   * @param condition is the condition that must be checked for each turtle.
   * @param commands  is the list of subcommands. This constructor creates an askwith command
   *                  object.
   */
  public AskWith(String[] args, List<Command> condition, List<Command> commands) {
    super(args, commands);
    myCondition = condition;
    setNumExpectedArguments(0);
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the result of the last executed command. This method executes the askwith command on a
   * list of turtles.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    List<Integer> turtleBeingChecked = new ArrayList<>();
    double result = 0;
    for (int k = 0; k < turtles.size(); k++) {
      turtleBeingChecked.add(k);
      if (checkCondition(turtles, k, vars)) {
        executeSubCommands(turtles, turtleBeingChecked, vars);
      }
      turtleBeingChecked.clear();

    }
    return result;
  }

  private boolean checkCondition(List<Turtle> turtles, int index, HashMap<String, Double> vars) {
    List<Integer> activeTurtle = new ArrayList<>();
    activeTurtle.add(index);
    CommandExecutor conditionCE = new CommandExecutor(myCondition, turtles, activeTurtle, vars);
    double result = conditionCE.executeCommands();
    return result != 0;
  }

}
