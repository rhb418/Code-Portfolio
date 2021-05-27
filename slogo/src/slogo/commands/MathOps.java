package slogo.commands;

import static slogo.commands.CommandConstants.ATAN;
import static slogo.commands.CommandConstants.COS;
import static slogo.commands.CommandConstants.DIFFERENCE;
import static slogo.commands.CommandConstants.LOG;
import static slogo.commands.CommandConstants.MINUS;
import static slogo.commands.CommandConstants.PI;
import static slogo.commands.CommandConstants.POW;
import static slogo.commands.CommandConstants.PRODUCT;
import static slogo.commands.CommandConstants.QUOTIENT;
import static slogo.commands.CommandConstants.RANDOM;
import static slogo.commands.CommandConstants.REMAINDER;
import static slogo.commands.CommandConstants.SIN;
import static slogo.commands.CommandConstants.SUM;
import static slogo.commands.CommandConstants.TAN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import slogo.turtle.Turtle;

/**
 * @author Robert Barnette, this class implements all the math commands.
 */
public class MathOps extends Command {

  private int myOperation;
  private HashMap<Integer, Double> myMathResults;

  /**
   * @param args   is the string [] of args for this command.
   * @param opType is the type of math operation being executed. This constructor creates a mathops
   *               object.
   */
  public MathOps(String[] args, int opType) {
    super(args);
    myOperation = opType;
    myMathResults = new HashMap<>();
    setNumExpectedArguments(calculateNumArgs(opType));
  }

  private int calculateNumArgs(int opType) {
    if (opType >= SUM && opType <= POW) {
      return 2;
    } else if (opType >= MINUS && opType <= LOG) {
      return 1;
    }
    return 0;
  }

  /**
   * @param turtle is the turtle the command is being executed on.
   * @param vars   is the current list of active variables.
   * @returns a double value of the executed math command result. This method executes a math
   * command.
   */
  @Override
  public double executeCommand(Turtle turtle, HashMap<String, Double> vars) {
    updateVariables(vars);
    ArrayList<Double> arguments = processArgs();
    doMath(arguments);

    double result = myMathResults.get(myOperation);

    if (Double.isInfinite(result)) {
      result = 0;
      setErrorMessage("Divide by 0 Error");
    } else if (Double.isNaN(result)) {
      result = 0;
      setErrorMessage("Non-Real Answer Error");

    }

    return result;
  }

  private void doMath(ArrayList<Double> arguments) {
    int numArgs = arguments.size();
    double arg1 = 0;
    double arg2 = 1;

    if (numArgs == 1) {
      arg1 = arguments.get(0);
    } else if (numArgs == 2) {
      arg1 = arguments.get(0);
      arg2 = arguments.get(1);
    }

    calculateResults(arg1, arg2);

  }

  private void calculateResults(double arg1, double arg2) {
    Random rand = new Random();
    double sumResult = arg1 + arg2;
    double diffResult = arg1 - arg2;
    double prodResult = arg1 * arg2;
    double quotResult = arg1 / arg2;
    double remResult = arg1 % arg2;
    double minusResult = -arg1;
    double randResult = rand.nextDouble() * arg1;
    double sinResult = Math.sin(Math.toRadians(arg1));
    double cosResult = Math.cos(Math.toRadians(arg1));
    double tanResult = Math.tan(Math.toRadians(arg1));
    double atanResult = Math.toDegrees(Math.atan(arg1));
    double logResult = Math.log(arg1);
    double powResult = Math.pow(arg1, arg2);

    myMathResults.put(SUM, sumResult);
    myMathResults.put(DIFFERENCE, diffResult);
    myMathResults.put(PRODUCT, prodResult);
    myMathResults.put(QUOTIENT, quotResult);
    myMathResults.put(REMAINDER, remResult);
    myMathResults.put(MINUS, minusResult);
    myMathResults.put(RANDOM, randResult);
    myMathResults.put(SIN, sinResult);
    myMathResults.put(COS, cosResult);
    myMathResults.put(TAN, tanResult);
    myMathResults.put(ATAN, atanResult);
    myMathResults.put(LOG, logResult);
    myMathResults.put(POW, powResult);
    myMathResults.put(PI, Math.PI);

  }


}
