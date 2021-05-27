package slogo.model.parser.CommandGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import slogo.commands.Command;

/**
 * @author billyluqiu
 *
 * Class that extends CommandGenerator and SuperCommandGenerator in order to parse commands
 * with mulitple turtles correctly
 *
 * Assumptions: same as CommandGenerator Class
 *
 * Dependencies: same as CommandGenerator Class
 *
 * Example on how to use, call buildCommand to create command object
 *
 */

public class MultipleTurtleCommandGenerator extends SuperCommandGenerator {

  private final String[] userInput;

  /**
   * Constructor for the MultipleTurtleCommands Generator
   * @param input original input of the string
   * @param userInput input array of the entire command
   * @param index index of the current command
   * @param toCommandList map mapping toCommands to their commandValues
   * @param toCommandParams map mapping toCommandNames to their params
   * @param toCommandStrings map mapping toCommand Names to the string of values they include
   * @param language language that the parser is currently set to
   *
   */

  public MultipleTurtleCommandGenerator(String input, String[] userInput, int index,
      Map<String, List<Command>> toCommandList,
      Map<String, List<String>> toCommandParams,
      Map<String, String> toCommandStrings,
      String language) {
    super(input, userInput, index, toCommandList, toCommandParams, toCommandStrings, language);
    this.userInput = userInput;
  }

  /**
   * method that creates the command object itself
   * @param input command name
   * @param index index of the start of the command
   * @return command object of current command
   * @throws InvocationTargetException  if command name not valid
   * @throws IllegalAccessException if permissions are wrong
   */


  protected Command buildNonToCommand(String input, int index)
      throws InvocationTargetException, IllegalAccessException {
    CommandBuilder myCommandBuilder = new CommandBuilder(getCommandType(input), input);
    Method paramsMethod = getParameterMethod(
        input); //calls method to build specific SuperCommand
    Object[] params = (Object[]) paramsMethod.invoke(this, index);
    return myCommandBuilder.createInstanceOfCommand(params);
  }

  private Object[] getParametersTell(int index) {
    Object[] params = new Object[1];
    params[0] = getTurtleList(index);
    return params;
  }


  private String[] getTurtleList(int index) {
    int startOfExpr = super.getBracketsParser().getLocOfStartOfExp(index);
    int endOfExpr = super.getBracketsParser().getLocOfEndOfExp(startOfExpr + 1);
    List<String> turtleList = new ArrayList<>(
        Arrays.asList(this.userInput).subList(startOfExpr + 1, endOfExpr));
    return turtleList.toArray(new String[0]);
  }

  private Object[] getParametersAsk(int index) {
    Object[] args = new Object[2];
    args[0] = getTurtleList(index);
    List<Command> commands = super.getBracketsParser().getListOfCommandsInSecondBlock(index);
    args[1] = commands;
    return args;
  }

  private Object[] getParametersAskWith(int index) {
    List<Command> exprCommands = super.getBracketsParser().getListOfCommandsInFirstBlock(index);
    List<Command> commands = super.getBracketsParser().getListOfCommandsInSecondBlock(index);
    Object[] args = new Object[3];
    args[0] = new String[]{};
    args[1] = exprCommands;
    args[2] = commands;

    return args;
  }

}
