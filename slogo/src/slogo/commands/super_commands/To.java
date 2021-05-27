package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.Command;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class is used to implement to command.s
 */
public class To extends SuperCommand {

  private HashMap<String, Double> myLocalVariables;
  private List<String> myParameters;

  /**
   * @param args       is string [] of command arguments.
   * @param commands   is the list of subcommands.
   * @param parameters is the list of command parameters.
   * @param name       is the name of the to command. This constructor creates a to command object.
   */
  public To(String[] args, List<Command> commands, List<String> parameters, String name) {
    super(args, commands);
    setNumExpectedArguments(parameters.size());
    myParameters = parameters;
    setName(name);
    myLocalVariables = new HashMap<>();
  }


  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the result of the last command executed. This method executes a to command on a list
   * of turtles.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();
    myLocalVariables.putAll(vars);

    for (int k = 0; k < arguments.size(); k++) {
      myLocalVariables.put(myParameters.get(k), arguments.get(k));
    }

    return executeSubCommands(turtles, activeTurtles, myLocalVariables);

  }
}
