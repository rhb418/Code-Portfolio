package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the turtles command.
 */
public class Turtles extends SuperCommand {

  /**
   * @param args is the string [] of command arguments. This constructor creates a new turtles
   *             command object.
   */
  public Turtles(String[] args) {
    super(args, new ArrayList<>());
    setNumExpectedArguments(0);
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the number of turtles. This method returns the number of turtles that exist.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    return turtles.size();
  }
}
