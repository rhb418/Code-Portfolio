package slogo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import slogo.commands.Command;
import slogo.turtle.Turtle;
import slogo.turtle.TurtleData;

/**
 * @author Robert Barnette, This class takes in a list of commands, turtles, active turtles, and a
 * map of variables as arguments in its constructor. When its executeCommands method is called, it
 * then executes all of the commands given in its list of commands, altering or creating turtles as
 * needed. It then returns the double result of the last executed command.
 */
public class CommandExecutor {

  private String myErrorMessage;
  private Boolean[] myExecutedCommands;
  private List<Command> myCommands;
  private HashMap<String, Double> myVars;
  private HashSet<Integer> myUnexecutedTQs;
  private List<Turtle> myTurtleList;
  private List<Integer> myActiveTurtles;
  private List<Integer> commandsToReset;
  private double myResult;
  private boolean myError;
  private boolean executingTQ;

  /**
   * @param commands      is the list of commands that the command executor executes.
   * @param turtles       is the list of turtles the command executor acts on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of variables to their double values. This constructor creates a
   *                      CommandExecutor object which takes in a list of commands and uses them to
   *                      update the state of the turtles.
   */
  public CommandExecutor(List<Command> commands, List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    myErrorMessage = null;
    myResult = 0;
    myCommands = commands;
    myTurtleList = turtles;
    myActiveTurtles = activeTurtles;
    myVars = vars;
    myError = false;
    myUnexecutedTQs = new HashSet<>();
    commandsToReset = new ArrayList<>();
    resetExecutedCommands(commands.size());
  }

  //This method resets the boolean array keeping track of which commands have been executed.
  private void resetExecutedCommands(int size) {
    myExecutedCommands = new Boolean[size];
    for (int k = 0; k < size; k++) {
      myExecutedCommands[k] = false;
    }
  }

  /**
   * @returns a double value representing the result of the final executed command in myCommands.
   * This method iterates through the list of commands and intelligently executes them in the
   * correct order, using them to update the state of the turtles as needed. It also backs up the
   * data of all turtles prior to execution and resets them to their initial state if an error is
   * detected. Additionally it also detects for errors and updates the error message if errors are
   * found.
   */
  public double executeCommands() {
    List<TurtleData> preExecutionState = backUpTurtleData();
    List<Turtle> preExecutionTurtles = new ArrayList<>(myTurtleList);

    for (int k = 0; k < myCommands.size(); k++) {
      Command command = myCommands.get(k);
      if (command.getNumCurrentArguments() == command.getNumExpectedArguments()) {
        executeCommand(command, k);
        if (checkError(command)) {
          break;
        }
        tryToExecuteAnyTQs();
      }

    }
    checkUnexecutedCommands();
    resetStateOnError(preExecutionTurtles, preExecutionState);
    resetExecutor();
    return myResult;
  }

  //This method restates the state of the turtles to that of their pre-execution states in event of an error.
  private void resetStateOnError(List<Turtle> preExecutionTurtles,
      List<TurtleData> preExecutionState) {
    if (!myError) {
      return;
    }
    for (int k = 0; k < preExecutionTurtles.size(); k++) {
      preExecutionTurtles.get(k).setTurtleData(preExecutionState.get(k));
    }
  }

  //This method attempts to execute and previously unexecuted turtle query commands.
  private void tryToExecuteAnyTQs() {
    List<Integer> unexTqs = new ArrayList<>(myUnexecutedTQs);
    Collections.sort(unexTqs);
    if (unexTqs.size() == 0) {
      return;
    }
    for (int k = 0; k < unexTqs.size(); k++) {
      if (checkIfExecutable(unexTqs.get(k))) {
        executeTurtleQuery(myCommands.get(unexTqs.get(k)), unexTqs.get(k));
        myUnexecutedTQs.clear();
        break;
      }
    }
  }

  //This method determines the type of command that is being executed and then calls a particular method to handle it.
  private void executeCommand(Command command, int index) {
    commandsToReset.add(index);
    if (command.getName().equals("TQ")) {
      executeTurtleQuery(command, index);

    } else if (command.isTurtleCommand()) {
      executeTurtleCommand(command, index);

    } else if (command.isSuperCommand()) {
      executeSuperCommand(command, index);

    } else {
      executeNormalCommand(command, index);
    }

  }

  //Executes normal commands (commands that are not SuperCommands, TurtleCommands, or Turtle Queries)
  //These commands are math operations that do not affect the state of any turtles
  private void executeNormalCommand(Command command, int index) {
    myResult = command.executeCommand(myTurtleList.get(0), myVars);
    myExecutedCommands[index] = true;
    executePreviousCommands(index);
  }

  //Executes SuperCommands on a list of turtles
  private void executeSuperCommand(Command command, int index) {
    myResult = command.executeSuperCommand(myTurtleList, myActiveTurtles, myVars);
    myExecutedCommands[index] = true;
    executePreviousCommands(index);
  }

  //Executes a turtle command on all active turtles
  private void executeTurtleCommand(Command command, int index) {
    for (Integer active : myActiveTurtles) {
      myResult = command.executeCommand(myTurtleList.get(active), myVars);
    }
    myExecutedCommands[index] = true;
    executePreviousCommands(index);
  }

  //Executes a turtle query on all active turtles, cascading the result of this query to all previous unexectued commands for all turtles
  private void executeTurtleQuery(Command command, int index) {
    if (!checkIfExecutable(index)) {
      myUnexecutedTQs.add(index);
      return;
    }
    if (!executingTQ) {
      executeTQonAll(command, index);
    } else {
      myResult = command.executeCommand(myTurtleList.get(myActiveTurtles.get(0)), myVars);
      myExecutedCommands[index] = true;
      executePreviousCommands(index);
    }

  }

  //Executes the turtle query on all active turtles
  private void executeTQonAll(Command command, int index) {
    executingTQ = true;
    List<Integer> activeTurtles = new ArrayList<>(myActiveTurtles);

    for (int k = 0; k < activeTurtles.size(); k++) {
      myActiveTurtles.clear();
      myActiveTurtles.add(activeTurtles.get(k));

      commandsToReset.clear();
      executeCommand(command, index);

      if (k == activeTurtles.size() - 1) {
        break;
      }
      resetCommands();
    }
    myActiveTurtles.clear();
    myActiveTurtles.addAll(activeTurtles);
    executingTQ = false;
  }

  //Resets all commands that were executed in a turtle query cascade so they can be run again for a different turtle
  private void resetCommands() {
    for (Integer i : commandsToReset) {
      myCommands.get(i).reset();
      myExecutedCommands[i] = false;
    }
  }

  //Searches for previously unexecuted commands following execution, provides arguments to these commands and executes them if possible.
  private void executePreviousCommands(int index) {
    int numAvailableArgs = 1;
    while (true) {

      int prevIndex = getPreviousUnexecutedIndex(index);
      index = prevIndex;

      if (index == -1) {
        return;
      }

      Command prevCommand = myCommands.get(index);
      int numNeededArgs =
          prevCommand.getNumExpectedArguments() - prevCommand.getNumCurrentArguments();
      if (numNeededArgs >= numAvailableArgs) {
        prevCommand.addArgAtPosition(Double.toString(myResult), numAvailableArgs);

        if (prevCommand.getNumCurrentArguments() == prevCommand.getNumExpectedArguments()) {
          executeCommand(prevCommand, index);
        }

        return;
      }

      numAvailableArgs++;
    }
  }

  //Checks to see if a turtle query is able to fully cascade to completion to determine if it should be executed
  private boolean checkIfExecutable(int index) {
    int numAvailableArgs = 1;
    while (true) {
      index = getPreviousUnexecutedIndex(index);

      if (index == -1 && numAvailableArgs == 1) {
        return true;
      } else if (index == -1 && numAvailableArgs != 1) {
        return false;
      }

      Command prevCommand = myCommands.get(index);
      int numNeededArgs =
          prevCommand.getNumExpectedArguments() - prevCommand.getNumCurrentArguments();

      numAvailableArgs = numAvailableArgs + 1 - numNeededArgs;

    }

  }

  //Backs up the state of all turtles into a list of turtledata
  private List<TurtleData> backUpTurtleData() {
    List<TurtleData> data = new ArrayList<>();
    for (Turtle turtle : myTurtleList) {
      data.add(turtle.getTurtleData());
    }
    return data;
  }

  //Checks to see if some commands were not executed after attempting to execute all commands
  //Flags an error and writes an error message if this is the case
  private void checkUnexecutedCommands() {

    for (int k = 0; k < myExecutedCommands.length; k++) {
      if (!myExecutedCommands[k] && !myError) {
        String commandName = myCommands.get(k).getName();
        int expectedArgs = myCommands.get(k).getNumExpectedArguments();
        int actualArgs = myCommands.get(k).getNumCurrentArguments();
        myErrorMessage = "Command " + commandName + " failed to execute with " + expectedArgs
            + " expected arguments and only " + actualArgs + " given arguments.";
        myError = true;
        myResult = 0;
        return;
      }
    }

  }

  //Returns the index of the closest previous unexecuted command to the given index
  private int getPreviousUnexecutedIndex(int index) {
    if (index == 0) {
      return -1;
    }
    for (int k = index - 1; k >= 0; k--) {
      if (!myExecutedCommands[k]) {
        return k;
      }
    }

    return -1;
  }

  //Resets all commands in myCommands and resets the boolean array of executed commands to false
  private void resetExecutor() {
    for (Command command : myCommands) {
      command.reset();
    }
    resetExecutedCommands(myCommands.size());
  }

  //Checks to see if a command threw an error when it executed
  private boolean checkError(Command command) {
    String error = command.getErrorMessage();
    if (error != null) {
      myErrorMessage = error;
      myError = true;
      myResult = 0;
      return true;
    }
    return false;
  }

  /**
   * @returns the error message if an error occurred or null if an error did not occur.
   */
  public String getErrorMessage() {
    return myErrorMessage;
  }

}
