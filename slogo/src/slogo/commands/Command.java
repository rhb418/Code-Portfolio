package slogo.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, This is the abstract command class which all slogo commands extend. This
 * class provides a number of different methods that allow commands to add to their arguments or
 * execute the command on a turtle or list of turtles. This class is well designed because it
 * provides a large number of useful methods to its subclasses and is at the top of a clear and
 * effective inheritance hierarchy.
 */
public abstract class Command {

  private HashMap<String, Double> myVariables;
  private ArrayList<String> myArguments;
  private int myNumExpectedArguments;
  private int myNumCurrentArguments;
  private int myNumInitialArguments;
  private String myErrorMessage;
  private String myName;
  private String[] myInitialArgs;
  private boolean isSuperCommand;
  private boolean isTurtleCommand;


  /**
   * @param initialArgs is the String [] of initial command arguments. This constructor makes a
   *                    command object by taking in an array of arguments and adjusting instance
   *                    variables accordingly
   */
  public Command(String[] initialArgs) {
    myArguments = new ArrayList<>(Arrays.asList(initialArgs));
    myInitialArgs = initialArgs;
    myNumInitialArguments = calculateNumInitialArguments(initialArgs);
    myNumCurrentArguments = myNumInitialArguments;
    myErrorMessage = null;
    myName = this.getClass().getSimpleName();
    isSuperCommand = false;
    isTurtleCommand = false;
  }


  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns a double that is the result of the command execution. This method is used to execute
   * commands on a particular turtle which is how turtle commands, turtle queries, and math commands
   * function. These classes override this method for a specific implementation however
   * superCommands do not use this method and thus do not override it.
   */
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    return 0;
  }

  /**
   * @param turtles       is the list of turtles that the SuperCommand will be executed on.
   * @param activeTurtles is the list of the indexes of the currently active turtles.
   * @param vars          is the map of current variables to their values.
   * @returns a double value of the last executed command. This method is overridden by
   * SuperCommands which execute commands on a list of turtles and use a list of active turtles to
   * determine which turtles the command should be executed on.
   */
  public double executeSuperCommand(List<Turtle> turtles, List<Integer> activeTurtles,
      HashMap<String, Double> vars) {
    return 0;
  }


  /**
   * @returns an ArrayList of doubles containing all arguments for the command. This method
   * processes the string args and converts them into doubles substituting actual values for any
   * variables.
   */
  protected ArrayList<Double> processArgs() {
    ArrayList<Double> doubleArgs = new ArrayList<>();

    for (int k = 0; k < myArguments.size(); k++) {
      String arg = myArguments.get(k);
      if (arg.charAt(0) == ':') {
        doubleArgs.add(getVariable(arg));
      } else {
        doubleArgs.add(Double.parseDouble(arg));
      }
    }

    return doubleArgs;
  }

  /**
   * @param arg      is the argument being added.
   * @param position is the position in the arguments list that the argument is being added to. This
   *                 method adds a string argument into a particular location in the list of
   *                 arguments.
   */
  public void addArgAtPosition(String arg, int position) {
    int counter = 0;
    for (int k = 0; k < myArguments.size(); k++) {
      if (myArguments.get(k) == null) {
        counter++;
        if (counter == position) {
          myArguments.set(k, arg);
          myNumCurrentArguments++;
          return;
        }
      }
    }

  }

  /**
   * This method resets a command's arguments to their value when the command was created.
   */
  public void reset() {
    myNumCurrentArguments = myNumInitialArguments;
    myArguments = new ArrayList<>(Arrays.asList(myInitialArgs));

  }

  /**
   * @param arg is the value of the variable being retrieved.
   * @returns a double value for a given variable. This method returns the value of a given variable
   * or 0 if that variable has not been created.
   */
  private double getVariable(String arg) {
    if (myVariables.get(arg) == null) {
      return 0;
    }
    return myVariables.get(arg);
  }

  /**
   * @param args is the String [] of initial arguments passed to the command constructor.
   * @returns the number of arguments the command has been given. This method is used to calculate
   * the number of arguments initially given to the command.
   */
  private int calculateNumInitialArguments(String[] args) {
    int counter = 0;
    for (int k = 0; k < args.length; k++) {
      if (args[k] != null) {
        counter++;
      }
    }
    return counter;
  }

  /**
   * @param vars is the list of variables being updated. This method allows subclasses to update
   *             their current list of variables when needed.
   */
  protected void updateVariables(HashMap<String, Double> vars) {
    myVariables = vars;
  }

  /**
   * @returns an ArrayList of String arguments to a subclass. Allows a subclass to access its
   * arguments as a list of strings.
   */
  protected ArrayList<String> getArguments() {
    return myArguments;
  }

  /**
   * @returns the number of expected arguments of the command.
   */
  public int getNumExpectedArguments() {
    return myNumExpectedArguments;
  }

  /**
   * @returns the number of current arguments the command has.
   */
  public int getNumCurrentArguments() {
    return myNumCurrentArguments;
  }

  /**
   * @param numArgs is the number of arguments that the command is given. This method allows
   *                subclasses to set their number of expected arguments.
   */
  protected void setNumExpectedArguments(int numArgs) {
    myNumExpectedArguments = numArgs;
  }

  /**
   * @returns an String error message if the command did not execute properly or null if it did.
   */
  public String getErrorMessage() {
    return myErrorMessage;
  }

  /**
   * @param error is the error message being set. This method allows a subclass to set an error
   *              message if it did not execute properly.
   */
  protected void setErrorMessage(String error) {
    myErrorMessage = error;
  }

  /**
   * @returns the String name of the command.
   */
  public String getName() {
    return myName;
  }

  /**
   * @param name is the name the command name is being set to. Allows subclasses to set their
   *             command names.
   */
  protected void setName(String name) {
    myName = name;
  }

  /**
   * @param s is a boolean determining if this command is a SuperCommand. Allows subclasses to set
   *          whether or not they are SuperCommands.
   */
  protected void setIsSuperCommand(boolean s) {
    isSuperCommand = s;
  }

  /**
   * @param s is a boolean determining if this command is a TurtleCommand. Allows subclasses to set
   *          whether or not they are a TurtleCommand.
   */
  protected void setIsTurtleCommand(boolean s) {
    isTurtleCommand = s;
  }

  /**
   * @returns a boolean indicating if this command is a SuperCommand.
   */
  public boolean isSuperCommand() {
    return isSuperCommand;
  }

  /**
   * @returns a boolean indicating if this command is a TurtleCommand.
   */
  public boolean isTurtleCommand() {
    return isTurtleCommand;
  }

}
