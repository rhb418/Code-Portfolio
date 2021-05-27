package slogo.model.parser.CommandGenerator;
//uses delegation

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.commands.Command;
import slogo.model.parser.Files.ParserFiles;
import slogo.model.parser.Parser2;

/**
 * @author billyluqiu
 *
 * This is the class that handles blocked inputs such as IfElse commands.
 * It gives helper methods to parse brackets correctly
 *
 * Assumptions: Assumes correct input in that string[] will be well formatted.
 *
 *
 * Dependencies: Depends on Parser2 for the constants and the parserfiles for grouping methods
 * Depends on Commands classes to create instances of the right commands
 *
 * Example on how to use it, is to call it with a userInput[] which is the string representing inputs,
 * and to callGetCommandsInsideGroup() to create the methods inside the group
 *
 * Assumes proper format with the proper userInput
 *
 *
 */

public class BracketsParser {

  private final String[] userInput;
  private final Map<String, List<String>> toCommandsParameters;
  private final Map<String, List<Command>> toCommandsCommandList;
  private final ResourceBundle numOfBlocks;
  private final Map<String, String> toCommandStrings;
  private final String language;

  /**
   * Constructor for BracketsParser that creates a new object that is able to parse objects and
   * brackets correctly
   *
   * @param toCommandList map mapping toCommands to their commandValues
   * @param toCommandParams map mapping toCommandNames to their params
   * @param toCommandStrings map mapping toCommand Names to the string of values they include
   * @param language language that the parser is currently set to
   */
  public BracketsParser(String[] userInput, Map<String, List<Command>> toCommandList,
      Map<String, List<String>> toCommandParams, Map<String, String> toCommandStrings,
      String language) {
    this.userInput = userInput;
    this.toCommandsCommandList = toCommandList;
    this.toCommandsParameters = toCommandParams;
    this.toCommandStrings = toCommandStrings;
    this.numOfBlocks = ParserFiles.NUM_BRACKETS.returnBundle();
    this.language = language;

  }


  /**
   * advances index to start of the commadn expr
   * @param index representing the current index
   * @return index representing the start of the expr
   */
  protected int advanceIndexToStartOfExpr(int index) {
    int numofRightBracketsNeedToSee = 0;
    index++;
    while (index != userInput.length && (numofRightBracketsNeedToSee != 0 ||
        !userInput[index].trim().equals(CommandGenerator.FRONT_BRACKET))) {

      if (ParserFiles.NUM_BRACKETS.returnBundle().containsKey(userInput[index].trim())) {
        numofRightBracketsNeedToSee += Integer
            .parseInt(ParserFiles.NUM_BRACKETS.returnBundle().getString(userInput[index]));
      }
      if (userInput[index].trim().equals(CommandGenerator.BACK_BRACKET)) {
        numofRightBracketsNeedToSee--;
      }
      index++;
    }
    return index;
  }


  /**
   * Method that gives a list of the strings for the "expr" per specifications on coursewebsite
   * @return List<String> representing the expr of a command structure
   */

  public List<String> getExprOfBlock(int index) {
    index++;
    List<String> expr = new ArrayList<>();
    int numofRightBracketsNeedToSee = 0;
    while (index != userInput.length && (numofRightBracketsNeedToSee != 0 ||
        !userInput[index].trim().equals(CommandGenerator.FRONT_BRACKET))) {
      if (ParserFiles.NUM_BRACKETS.returnBundle().containsKey(userInput[index].trim())) {
        numofRightBracketsNeedToSee += Integer
            .parseInt(ParserFiles.NUM_BRACKETS.returnBundle().getString(userInput[index]));
      }
      if (userInput[index].trim().equals(CommandGenerator.BACK_BRACKET)) {
        numofRightBracketsNeedToSee--;
      }
      expr.add(userInput[index]);
      index++;
    }
    if (expr.size() == 1 && Parser2.isNumericOrVariable(expr.get(0))) {
      return null;
    }
    return expr;
  }

  /**
   * Method to calculate location of the end of the entire control structure.
   *
   * @param commandType representing the commandType of the current Block
   * @param index current index representing the start of the expr
   * @return index of the end of the entire block
   */

  public int getIndexOfEndOfBlock(String commandType, int index) {
    int numOfBlocks = Integer.parseInt(this.numOfBlocks.getString(commandType));
    int firstStart = getLocOfStartOfExp(index);
    int firstEnd = getLocOfEndOfExp(firstStart + 1);

    if (numOfBlocks == 1) {
      return firstEnd;
    }
    int secondStart = getLocOfStartOfExp(firstEnd + 1);
    return getLocOfEndOfExp(secondStart + 1);
  }

  /**
   * Method to get commands inside the first block
   * @param index of the start of the block
   * @return list of commands of the things inside the first command block
   */
  protected List<Command> getListOfCommandsInFirstBlock(int index) {
    int startIndex = getLocOfStartOfExp(index);
    int endIndex = getLocOfEndOfExp(startIndex + 1);
    return getCommandsWithinRange(startIndex + 1, endIndex - 1);
  }

  private List<Command> getCommandsWithinRange(int startIndex, int endIndex) {
    StringBuilder trueExp = new StringBuilder();
    for (int i = startIndex; i <= endIndex; i++) {
      trueExp.append(userInput[i].trim()).append(" ");
    }
    trueExp = new StringBuilder(trueExp.toString().trim());
    Parser2 trueCommandParser = new Parser2(trueExp.toString(), this.toCommandsCommandList,
        this.toCommandsParameters,
        toCommandStrings, this.language);

    return trueCommandParser.newParser();
  }
  /**
   * Method to get commands inside the second block
   * @param index of the start of the command
   * @return list of commands of the things inside the second command block
   */

  protected List<Command> getListOfCommandsInSecondBlock(int index) {
    int startIndexOfTrue = getLocOfStartOfExp(index);
    int endIndexOfTrue = getLocOfEndOfExp(startIndexOfTrue + 1);
    int startIndexOfFalse = getLocOfStartOfExp(endIndexOfTrue);
    int endIndexOfFalse = getLocOfEndOfExp(startIndexOfFalse + 1);
    return getCommandsWithinRange(startIndexOfFalse + 1, endIndexOfFalse - 1);

  }

  /**
   * get location of the start of the expr
   * @param index of the first supercommand block itself
   * @return index representing the first "["
   */

  protected int getLocOfStartOfExp(int index) {
    while (index != userInput.length &&
        !userInput[index].trim().equals(CommandGenerator.FRONT_BRACKET)) {
      index++;
    }
    return index;
  }

  /**
   * get location of the end of the current block
   * @param index representing the "["
   * @return index representing the "]" that completes the block
   */
  protected int getLocOfEndOfExp(int index) {
    int numOfLeftBrackets = 1;
    int numOfRightBrackets = 0;
    while (numOfLeftBrackets != numOfRightBrackets) {
      if (userInput[index].trim().equals(CommandGenerator.FRONT_BRACKET)) {
        numOfLeftBrackets++;
      } else if (userInput[index].trim().equals(CommandGenerator.BACK_BRACKET)) {
        numOfRightBrackets++;
      }
      index++;
    }
    return index - 1;
  }

  /**
   * Method that gets location of the end of the expression of the to commands
   * @param index of the start of the expr
   * @return index representing the end of the block for to command
   */
  protected int getLocOfEndOfExprToCommand(int index) {
    int startIndexOfTrue = getLocOfStartOfExp(index);
    int endIndexOfTrue = getLocOfEndOfExp(startIndexOfTrue + 1);
    int startIndexOfFalse = getLocOfStartOfExp(endIndexOfTrue);
    return getLocOfEndOfExp(startIndexOfFalse + 1);
  }
}
