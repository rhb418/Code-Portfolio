package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.Command;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the set background command.
 */
public class SetBackground extends SuperCommand {

  /**
   * @param args is the string [] of arguments. This constructor creates a setbackground command
   *             object.
   */
  public SetBackground(String[] args) {
    super(args, new ArrayList<>());
    setNumExpectedArguments(1);
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the index of the background being set. This method sets the background of all turtles
   * to a certain index.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> args = processArgs();
    double index = args.get(0);

    for (Turtle turtle : turtles) {
      turtle.setBackground(index);
    }

    return index;
  }
}
