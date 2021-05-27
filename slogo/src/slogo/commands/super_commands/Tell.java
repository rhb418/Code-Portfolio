package slogo.commands.super_commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import slogo.commands.SuperCommand;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This class implements the tell command.
 */
public class Tell extends SuperCommand {

  public Tell(String[] args) {
    super(args, new ArrayList<>());
    setNumExpectedArguments(args.length);
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns the highest ID value of active turtles. This method executes the tell command on a
   * list of turtles.
   */
  @Override
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    updateVariables(vars);
    List<Double> args = processArgs();
    List<Integer> integerArgs = new ArrayList<>();

    for (int k = 0; k < args.size(); k++) {
      integerArgs.add(args.get(k).intValue());
    }
    int max = Collections.max(integerArgs);

    if (max >= turtles.size()) {
      for (int k = turtles.size(); k <= max; k++) {
        Turtle newTurtle = new Turtle(k);
        newTurtle.setTurtleData(turtles.get(0).getTurtleData());
        newTurtle.clear();
        turtles.add(newTurtle);

      }
    }
    activeTurtles.clear();
    activeTurtles.addAll(integerArgs);

    return (double) activeTurtles.get(activeTurtles.size() - 1);
  }
}
