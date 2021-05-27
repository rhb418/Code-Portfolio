package slogo.commands;

import static slogo.commands.CommandConstants.AND;
import static slogo.commands.CommandConstants.EQUAL;
import static slogo.commands.CommandConstants.GREATER;
import static slogo.commands.CommandConstants.LESS;
import static slogo.commands.CommandConstants.NOT;
import static slogo.commands.CommandConstants.NOTEQUAL;
import static slogo.commands.CommandConstants.OR;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, this class implements the BoolOps command which executes boolean
 * operations.
 */
public class BoolOps extends Command {

  private int myOperation;
  private HashMap<Integer, Boolean> myBoolResults;

  /**
   * @param args   is the string [] of arguments.
   * @param opType is the type of boolean operation being conducted. This constructor creates a
   *               boolean operation command object with a given operation type.
   */
  public BoolOps(String[] args, int opType) {
    super(args);
    myOperation = opType;
    myBoolResults = new HashMap<>();
    setNumExpectedArguments(calculateNumArgs(opType));
  }

  private int calculateNumArgs(int opType) {
    if (opType == NOT) {
      return 1;
    }
    return 2;
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns a double value representing the result of the boolean operation. This method executes
   * a boolean operation command.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();

    doBoolOps(arguments);

    if (myBoolResults.get(myOperation)) {
      return 1;
    }
    return 0;
  }

  private void doBoolOps(ArrayList<Double> arguments) {
    int numArgs = arguments.size();
    double arg1 = 0;
    double arg2 = 0;

    if (numArgs == 1) {
      arg1 = arguments.get(0);
    } else if (numArgs == 2) {
      arg1 = arguments.get(0);
      arg2 = arguments.get(1);
    }
    calculateResults(arg1, arg2);
  }

  private void calculateResults(double arg1, double arg2) {
    boolean lessResult = arg1 < arg2;
    boolean greatResult = arg1 > arg2;
    boolean equalResult = arg1 == arg2;
    boolean notEqualResult = arg1 != arg2;
    boolean andResult = arg1 != 0.0 && arg2 != 0.0;
    boolean orResult = arg1 != 0.0 || arg2 != 0.0;
    boolean notResult = arg1 == 0.0;

    myBoolResults.put(LESS, lessResult);
    myBoolResults.put(GREATER, greatResult);
    myBoolResults.put(EQUAL, equalResult);
    myBoolResults.put(NOTEQUAL, notEqualResult);
    myBoolResults.put(AND, andResult);
    myBoolResults.put(OR, orResult);
    myBoolResults.put(NOT, notResult);
  }
}
