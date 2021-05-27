package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.Command;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the set pen color command.
 */
public class SetPenColor extends SuperCommand {

  /**
   * @param args is the string [] of arguments. This constructor creates a setpencolor command
   *             object.
   */
  public SetPenColor(String[] args) {
    super(args, new ArrayList<>());
    setNumExpectedArguments(1);
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the index of the color the pens are being set to. This method sets the pen color of
   * all active turtles to a given index.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> args = processArgs();
    double index = args.get(0);

    for (Integer i : activeTurtles) {
      turtles.get(i).setPenColor(index);
    }

    return index;
  }
}
