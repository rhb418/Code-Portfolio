package slogo.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.model.CommandExecutor;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class is the abstract SuperCommand class which extends command and
 * defines behaviors for commands that execute on a list of turtles. Some of these commands such as
 * loops and conditionals also contain lists of subcommands which this superclass helps to execute
 * using a CommandExecutor. This class is well designed because it effectively extends its
 * superclass and provides some additional methods that are very helpful to SuperCommand subclasses.
 * .
 */
public abstract class SuperCommand extends Command {

  private List<Command> myCommands;
  private CommandExecutor myCommandExecutor;
  private List<Integer> myActiveTurtles;

  /**
   * @param args     is the String [] of arguments for the SuperCommand.
   * @param commands is the list of sub commands that the SuperCommand executes. This constructor
   *                 creates a SuperCommand object by assigning values to instance variables and
   *                 calling super on the list of arguments.
   */
  public SuperCommand(String[] args, List<Command> commands) {
    super(args);
    myCommands = commands;
    setIsSuperCommand(true);
    myActiveTurtles = new ArrayList<>();
  }

  /**
   * @param turtles       is the list of current turtles.
   * @param activeTurtles is the list of the indexes of all active turtles.
   * @param vars          is the map of variables to values.
   * @returns a double value of the last executed command. This method uses a CommandExecutor object
   * to execute all the sub commands within the list of given sub commands.
   */
  protected double executeSubCommands(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    myActiveTurtles.clear();
    myActiveTurtles.addAll(activeTurtles);
    myCommandExecutor = new CommandExecutor(myCommands, turtles, myActiveTurtles, vars);

    double result = myCommandExecutor.executeCommands();
    setErrorMessage(myCommandExecutor.getErrorMessage());

    return result;
  }


}
