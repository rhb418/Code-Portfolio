package slogo.model.parser.CommandGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.commands.Command;
import slogo.model.parser.Files.ParserFiles;
import slogo.model.parser.Parser2;

/**
 * @author billyluqiu
 *
 * This is the class that handles grouping inputs and unravels them into a list of commands
 * successfully. This is for the unlimited parameters feature.
 *
 * Assumptions: Assumes all inputs in the brace will be either numerical or another brace expr Will
 * only work for the methods specified in GroupMethod.properties. Parses them in the same order as
 * they came in.
 *
 * Dependencies: Depends on Parser2 for the constants and the parserfiles for grouping methods
 *
 * Example on how to use it, is to call it with a userInput[] which is the string representing inputs,
 * and to callGetCommandsInsideGroup() to create the methods inside the group
 *
 * Assumes proper format with the proper userInput
 *
 *
 */


public class BracesParser {

  private final String[] userInput;
  private int index;
  private final int locOfEndOfBlock;
  private String commandType;
  private final Map<String, List<String>> toCommandsParameters;
  private final Map<String, List<Command>> toCommandsCommandList;
  private final LinkedList<String> commandListAfterParsing;
  private final ResourceBundle resourceBundle;

  /**
   * Constructor for BracerParser that creates a new object that is able to parse paranthesies
   * @param userInput input with the strings from the original command input
   * @param index starting index of the expr, with the first group
   * @param toCommandList map mapping toCommands to their commandValues
   * @param toCommandParams map mapping toCommandNames to their params
   */
  public BracesParser(String[] userInput, int index, Map<String, List<Command>> toCommandList,
      Map<String, List<String>> toCommandParams) {
    this.userInput = userInput;
    this.index = index;
    this.resourceBundle = ParserFiles.GROUPING_METHOD.returnBundle();
    this.toCommandsCommandList = toCommandList;
    this.toCommandsParameters = toCommandParams;
    this.locOfEndOfBlock = calculateLocOfLastBlock(this.index + 1);
    this.commandType = getCommandType();
    this.commandListAfterParsing = new LinkedList<>();
  }

  /**
   * Method to parse through the command group and return the list of strings representing the
   * method and parameters.
   *
   * Will throw exception if method not formatted correctly
   *
   * @return list of strings representing the method inside the group and the parameters
   * @throws InvocationTargetException in case it's unable to find proper class to invoke
   * @throws IllegalAccessException in case there's a permissions error
   */
  public List<String> getCommandsInsideGroup()
      throws InvocationTargetException, IllegalAccessException {
    Method method = getStructureMethod(this.commandType);
    index++;
    method.invoke(this);
    return this.commandListAfterParsing;
  }

  private String getCommandType() {

    int tempIndex = index + 1;
    while (tempIndex < userInput.length) {
      if (resourceBundle.containsKey(userInput[index])) {
        return userInput[index];
      }
      index++;
    }
    return null;
  }

  private Method getStructureMethod(String input) {
    Method method = null;
    try {
      method = this.getClass().getDeclaredMethod(resourceBundle.getString(input));
    } catch (Exception e) {
      return null;
    }
    return method;
  }


  private void handleMathOps() throws InvocationTargetException, IllegalAccessException {
    int numOfParams = 0;
    while (index < this.locOfEndOfBlock) {
      if (this.userInput[index].equals(Parser2.START_OF_GROUP)) {
        handleNestedGroup(index);
      } else {
        this.commandListAfterParsing.add(this.userInput[index]);
      }
      numOfParams++;
      index++;
    }
    for (int i = 1; i < numOfParams; i++) {
      this.commandListAfterParsing.addFirst(this.commandType);
    }
  }

  private void handleTurtleMovement() throws InvocationTargetException, IllegalAccessException {
    String originalCommand = this.commandType;
    this.commandType = "Sum";
    handleMathOps();
    this.commandListAfterParsing.addFirst(originalCommand);
  }

  private void handleSetParamsCommands() {
    int numOfArguments = Integer.parseInt(ParserFiles.ARGS_FILE.returnBundle()
        .getString(this.commandType));
    while (index < this.locOfEndOfBlock) {
      ArrayList<String> tempParams = new ArrayList<>();
      tempParams.add(this.commandType);
      for (int i = 0; i < numOfArguments; i++) {
        if (this.userInput[index].equals(Parser2.START_OF_GROUP)) {
          handleNestedGroupSetParams(tempParams);
        }
        tempParams.add(this.userInput[index]);
        index++;
      }
      this.commandListAfterParsing.addAll(tempParams);
    }
  }

  private void handleNestedGroupSetParams(ArrayList<String> tempParams) {
    BracesParser nestedParser = new BracesParser(this.userInput, index, this.toCommandsCommandList,
        this.toCommandsParameters);
    tempParams.addAll(nestedParser.commandListAfterParsing);
    index = nestedParser.locOfEndOfBlock + 1;
  }

  private void handleNestedGroup(int newIndex)
      throws InvocationTargetException, IllegalAccessException {
    BracesParser nestedParser = new BracesParser(this.userInput, newIndex,
        this.toCommandsCommandList,
        this.toCommandsParameters);

    this.commandListAfterParsing.addAll(nestedParser.getCommandsInsideGroup());
    index = nestedParser.locOfEndOfBlock + 1;
  }

  /**
   * calculate location of last index so that the parser2 instance can advance to the proper place
   *
   * @param temp_index index of the start of the block
   * @return the index of the last paranthesis
   */

  public int calculateLocOfLastBlock(int temp_index) {
    int numOfLeftBraces = 1;
    int numOfRightBraces = 0;
    while (numOfLeftBraces != numOfRightBraces) {
      if (userInput[temp_index].trim().equals(Parser2.START_OF_GROUP)) {
        numOfLeftBraces++;
      } else if (userInput[temp_index].trim().equals(Parser2.END_OF_GROUP)) {
        numOfRightBraces++;
      }
      temp_index++;
    }
    return temp_index - 1;
  }


}
