package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the set palette command.
 */
public class SetPalette extends SuperCommand {

  /**
   * @param args is the string [] of arguments. This constructor creates a new setpalette command
   *             object.
   */
  public SetPalette(String[] args) {
    super(args, new ArrayList<>());
    setNumExpectedArguments(4);
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the index of the palette being set. This method adds an additional color palette with
   * a given index that can be used by the turtles to change their colors.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> args = processArgs();
    double index = args.get(0);

    for (Turtle turtle : turtles) {
      turtle.setPalette(index, args.get(1), args.get(2), args.get(3));
    }

    return index;
  }
}
