package slogo.model.parser.CommandGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.commands.BoolOps;
import slogo.commands.Command;
import slogo.commands.CommandConstants;
import slogo.model.parser.Files.ParserFiles;
import slogo.model.parser.Parser2;

/**
 * @author billyluqiu
 *
 * Class that extends CommandGenerator in order to parse commands
 * with command blocks correctly
 *
 * Assumptions: same as CommandGenerator Class
 *
 * Dependencies: same as CommandGenerator Class
 *
 * Example on how to use, call buildCommand to create command object
 *
 */

public class SuperCommandGenerator extends CommandGenerator {

  private final String input;
  private final String[] userInput;
  private final int index;
  private final Map<String, List<String>> toCommandsParameters;
  private final Map<String, List<Command>> toCommandsCommandList;
  private final Map<String, String> toCommandStrings;
  private CommandBuilder myCommandBuilder;
  private final BracketsParser bracketsParser;

  /**
   * Constructor for the superCommandGenerator
   *
   * @param input original input of the string
   * @param userInput input array of the entire command
   * @param index index of the current command
   * @param toCommandList map mapping toCommands to their commandValues
   * @param toCommandParams map mapping toCommandNames to their params
   * @param toCommandStrings map mapping toCommand Names to the string of values they include
   * @param language language that the parser is currently set to
   *
   */


  public SuperCommandGenerator(String input, String[] userInput, int index,
      Map<String, List<Command>> toCommandList,
      Map<String, List<String>> toCommandParams,
      Map<String, String> toCommandStrings,
      String language) {
    this.input = input;
    this.userInput = userInput;
    this.toCommandsCommandList = toCommandList;
    this.toCommandsParameters = toCommandParams;
    this.toCommandStrings = toCommandStrings;
    this.index = index;
    ParserFiles.NUM_BRACKETS.returnBundle();
    this.bracketsParser = new BracketsParser(this.userInput, this.toCommandsCommandList,
        this.toCommandsParameters, toCommandStrings, language);
  }

  /**
   * Method that will create a command representing the current command
   * @return Command object
   * @throws InvocationTargetException if command name not valid
   * @throws IllegalAccessException if permissions are wrong
   */

  public Command buildCommand() throws InvocationTargetException, IllegalAccessException {
    Method methodCalled = getStructureMethod(
        input); //calls method to build specific SuperCommand
    Object command = methodCalled.invoke(this, input, index);
    return (Command) command;
  }

  private Object buildToCommand(String input, int index) {
    try {
      int tempIndex = index;
      String commandName;
      while (userInput[tempIndex].equals(" ")) {
        tempIndex++;
      }

      int startIndexOfVariableBlock = bracketsParser.getLocOfStartOfExp(index);
      int endIndexOfVariableBlock = bracketsParser.getLocOfEndOfExp(startIndexOfVariableBlock + 1);
      commandName = userInput[tempIndex + 1];
      this.myCommandBuilder = new CommandBuilder(getCommandType(input), input);
      Object[] parameters = getParametersTo(commandName, startIndexOfVariableBlock,
          endIndexOfVariableBlock);

      addToCommandStringToMap(commandName, tempIndex,
          bracketsParser.getLocOfEndOfExprToCommand(tempIndex));
      return new BoolOps(new String[]{"0", "1"}, CommandConstants.SUM);
    } catch (Exception exception) {
      return new BoolOps(new String[]{"0", "0"}, CommandConstants.SUM);
    }
  }

  private void addToCommandStringToMap(String commandName, int startIndex, int endIndex) {
    String[] subarray = Arrays.copyOfRange(this.userInput, startIndex + 1, endIndex + 1);
    toCommandStrings.put(commandName, String.join(" ", subarray));
  }


  private Object[] getParametersTo(String commandName, int startIndexOfVariableBlock,
      int endIndexOfVariableBlock) {

    List<String> parameters = new ArrayList<>();
    for (int varIndex = startIndexOfVariableBlock + 1; varIndex < endIndexOfVariableBlock;
        varIndex++) {
      if (!userInput[varIndex].equals(" ")) {
        parameters.add(userInput[varIndex]);
      }
    }
    List<Command> commands = bracketsParser.getListOfCommandsInSecondBlock(index);
    Object[] args = new Object[4];
    args[0] = new String[]{null};
    args[1] = commands;
    args[2] = parameters;
    args[3] = commandName;
    toCommandsParameters.put(commandName, parameters);
    toCommandsCommandList.put(commandName, commands);
    return args;
  }

  /**
   * method that creates the command object itself for non ToCommands
   * @param input command name
   * @param index index of the start of the command
   * @return command object of current command
   * @throws InvocationTargetException  if command name not valid
   * @throws IllegalAccessException if permissions are wrong
   */

  protected Command buildNonToCommand(String input, int index)
      throws InvocationTargetException, IllegalAccessException {
    this.myCommandBuilder = new CommandBuilder(getCommandType(input), input);
    Method paramsMethod = getParameterMethod(
        input); //calls method to build specific SuperCommand
    Object[] params = (Object[]) paramsMethod.invoke(this, index);

    return myCommandBuilder.createInstanceOfCommand(params);
  }

  /**
   * method that returns the method object for the parameters method
   * @param input representing command
   * @return method that represents method which we can call to get the object method
   *
   */
  protected Method getParameterMethod(String input) {
    Method method = null;

    try {
      String className = String.valueOf(this.getClass()).replace(CLASS_FORMAT, EMPTY).trim();
      ResourceBundle myResourceBundle = ParserFiles.PARAMETERS_METHOD.returnBundle();
      String toCall = myResourceBundle.getString(input);
      method = Class.forName(className).getDeclaredMethod(toCall, int.class);
    } catch (Exception e) {
      return null;
    }
    return method;

  }

  private Object[] getParametersIfElse(int index) {
    int start = index;
    Object[] args = new Object[3];
    index = bracketsParser.advanceIndexToStartOfExpr(index);
    if (start + 1 == index - 1 && Parser2.isNumericOrVariable(userInput[start + 1])) {
      args[0] = new String[]{(userInput[start + 1])};
    } else {
      args[0] = new String[]{null};
    }
    List<Command> trueCommands = bracketsParser.getListOfCommandsInFirstBlock(index);
    List<Command> falseCommands = bracketsParser.getListOfCommandsInSecondBlock(index);

    args[1] = trueCommands;
    args[2] = falseCommands;

    return args;
  }

  private Object[] getParametersIfandRepeat(int index) {
    int start = index;
    Object[] args = new Object[2];
    index = bracketsParser.advanceIndexToStartOfExpr(index);

    if (start + 1 == index - 1 && Parser2.isNumericOrVariable(userInput[start + 1])) {
      args[0] = new String[]{(userInput[start + 1])};
    } else {
      args[0] = new String[]{null};
    }
    List<Command> trueCommands = bracketsParser.getListOfCommandsInFirstBlock(index);
    args[1] = trueCommands;

    return args;
  }


  private Object[] getParametersRepeatDoTimesFor(int index) {
    int start = index;
    Object[] args = new Object[2];
    int numofExpectedArgs = Integer.parseInt(ParserFiles.ARGS_FILE.returnBundle().getString(input));
    String[] loopArgs = new String[numofExpectedArgs];
    int argsPutInArray = 0;

    while (argsPutInArray != numofExpectedArgs) {
      if (Parser2.isNumericOrVariable(userInput[start])) {
        loopArgs[argsPutInArray] = (userInput[start]);
        argsPutInArray++;
      } else if (userInput[start].equals(BACK_BRACKET)) {
        break;
      }
      start++;
    }
    List<Command> commands = bracketsParser.getListOfCommandsInSecondBlock(index);
    args[0] = loopArgs;
    args[1] = commands;

    return args;
  }


  /***
   * helper method that returns the expr of the block (expr defined in project speficiations)
   * @param index representing start of expr
   * @return expr of block
   */
  public List<String> getExprOfBlock(int index) {
    return bracketsParser.getExprOfBlock(index);
  }

  /**
   * method that returns the index of the end of the supercommand inside original (this.userinput
   * @param commandType name of the command
   * @param index representing
   * @return index of the end of the block
   * note: could refactor so that this is unneeded
   */
  public int getIndexOfEndOfBlock(String commandType, int index) {
    return bracketsParser.getIndexOfEndOfBlock(commandType, index);
  }

  /**
   * helper method that gets the bracketsparser instance for the supercommand
   * @return bracketsparser object
   */
  protected BracketsParser getBracketsParser() {
    return this.bracketsParser;
  }

}
