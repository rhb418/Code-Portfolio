package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.Command;
import slogo.commands.SuperCommand;
import slogo.model.CommandExecutor;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the if else command.
 */
public class IfElse extends SuperCommand {

  private List<Command> myTrueCommands;
  private List<Command> myFalseCommands;
  private CommandExecutor myTrueCommandExecutor;
  private CommandExecutor myFalseCommandExecutor;
  private List<Integer> myActiveTurtles;

  /**
   * @param args          is the string [] of arguments.
   * @param trueCommands  is the list of commands that should be executed if the argument is true.
   * @param falseCommands is the list of commands that should be executed if the argument is false.
   *                      This constructor creates an if else command object.
   */
  public IfElse(String[] args, List<Command> trueCommands, List<Command> falseCommands) {
    super(args, trueCommands);
    setNumExpectedArguments(1);
    myTrueCommands = trueCommands;
    myFalseCommands = falseCommands;
    myActiveTurtles = new ArrayList<>();
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the result of the last executed command. This method executes the ifelse command on a
   * list of turtles.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();
    double arg = arguments.get(0);
    myActiveTurtles.addAll(activeTurtles);
    myTrueCommandExecutor = new CommandExecutor(myTrueCommands, turtles, myActiveTurtles, vars);
    myFalseCommandExecutor = new CommandExecutor(myFalseCommands, turtles, myActiveTurtles, vars);

    if (arg != 0) {
      double trueResult = myTrueCommandExecutor.executeCommands();
      setErrorMessage(myTrueCommandExecutor.getErrorMessage());
      return trueResult;
    }

    double falseResult = myFalseCommandExecutor.executeCommands();
    setErrorMessage(myFalseCommandExecutor.getErrorMessage());

    return falseResult;
  }
}
