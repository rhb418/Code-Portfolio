package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the set pen size command.
 */
public class SetPenSize extends SuperCommand {

  /**
   * @param args is the string [] of arguments. This constructor creates a new setpensize command
   *             object.
   */
  public SetPenSize(String[] args) {
    super(args, new ArrayList<>());
    setNumExpectedArguments(1);
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the pensize that is being set. This method sets the pen size of all active turtles to
   * a given value.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> args = processArgs();
    double index = args.get(0);

    for (Integer i : activeTurtles) {
      turtles.get(i).setPenSize(index);
    }

    return index;
  }
}
