package slogo.model.parser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.commands.Command;
import slogo.model.parser.CommandGenerator.BracesParser;
import slogo.model.parser.CommandGenerator.SimpleCommandGenerator;
import slogo.model.parser.CommandGenerator.SuperCommandGenerator;
import slogo.model.parser.Files.ParserFiles;

/**
 * Goes through User Inputted String and does best it can to make Command objects with their
 * appropriate parameters. Will pass this "unfinished" Command List to CommandExecutor so that all
 * parameters are "resolved" in the CommandExecutor.
 *
 * Assumptions: assumes correct input, otherwise exception will be thrown
 *
 * Dependencies: depends on subclasses inside CommandGenerator
 *
 * Example on how to use, create parser2 object and call newParser to parse commands
 *
 * @author Tomas Esber (main) and Billy Luqiu (secondary)
 *
 *
 */

public class Parser2 {

  private static final String SPACE = " ";
  private static final String TAKEN = "TAKEN";
  private static final String DEFAULT_PARAM = "x";
  public static final String START_OF_GROUP = "(";
  public static final String END_OF_GROUP = ")";
  private static final List<String> SUPERCOMMANDS = Arrays.asList(
      "Repeat", "DoTimes", "For", "If", "IfElse", "MakeUserInstruction", "Tell", "Ask", "AskWith");
  private static final List<String> SUPERCOMMANDSWITHEXPR = Arrays.asList(
      "Repeat", "If", "IfElse");
  private String[] userInput;
  private final List<Object> listOfCommandsAndStrings;
  private ResourceBundle myResourceBundle;
  private final Map<String, List<String>> toCommandsParameters;
  private final Map<String, List<Command>> toCommandsCommandList;
  private final Map<String, String> toCommandStrings;
  private final String language;
  private String errorMessages;

  /**
   * Constructor for parser2 class
   *
   * @param userInput input array of the entire command
   * @param toCommandList map mapping toCommands to their commandValues
   * @param toCommandParams map mapping toCommandNames to their params
   * @param toCommandStrings map mapping toCommand Names to the string of values they include
   * @param language language that the parser is currently set to
   */
  public Parser2(String userInput, Map<String, List<Command>> toCommandList,
      Map<String, List<String>> toCommandParams,
      Map<String, String> toCommandStrings,
      String language) {
    errorMessages = "";
    userInput = cleanInput(userInput);
    toCommandsCommandList = toCommandList;
    toCommandsParameters = toCommandParams;
    this.toCommandStrings = toCommandStrings;
    convertLanguage(userInput, language);
    listOfCommandsAndStrings = new ArrayList<>();
    this.language = language;

  }

  private void convertLanguage(String userInput, String language) {
    try {
      DataHandler myDataHandler = new DataHandler(language);
      this.userInput = myDataHandler.languageConverter(userInput.split(SPACE));
    } catch (Exception e) {
      errorMessages += "Error parsing command with given language bundle.";
    }
  }

  private String cleanInput(String userInput) {
    userInput = userInput + "\n";
    userInput = userInput.replaceAll("#.*\n", " ");
    userInput = userInput.replace("\n", " ");
    return userInput.trim().replaceAll(" +", " ");
  }

  public List<Command> newParser() {
    if (noError()) {
      checkForGroupedCommands();
    }
    if (noError()) {
      checkForControlCommands();
    }
    List<List<Object>> commands = null;
    if (noError()) {
      commands = getStringsWithParams();
    }
    List<List<Object>> commandsWithArgs = null;
    if (noError()) {
      commandsWithArgs = assignArguments(commands);
    }
    if (!noError()) {
      return null;
    }
    return buildCommands(commandsWithArgs);

  }

  private List<Command> buildCommands(List<List<Object>> commandsWithArgs) {
    try {
      List<Command> commandList = new ArrayList<>();
      for (List<Object> command : commandsWithArgs) {
        if (command.size() != 0) {
          SimpleCommandGenerator simpleCommandGenerator = new SimpleCommandGenerator(command,
              toCommandsCommandList,
              toCommandsParameters);
          commandList.add(simpleCommandGenerator.buildCommand());
        }
      }
      return commandList;
    } catch (Exception e) {
      this.errorMessages += "Error building command messages";
      return null;
    }
  }


  private Class getCommandGeneratorClass(String cmd) {
    Class cls = null;
    try {
      ResourceBundle myResourceBundle = ParserFiles.GENERATOR_CLASS.returnBundle();
      cls = Class.forName(myResourceBundle.getString(cmd));
    } catch (Exception e) {
      return null;
    }
    return cls;
  }


  private void checkForGroupedCommands() {
    try {
      List<String> currentListOfCommands = new ArrayList<>();
      for (int index = 0; index < this.userInput.length; index++) {
        String input = this.userInput[index];
        if (input.equals(START_OF_GROUP)) {
          BracesParser bracesParser = new BracesParser(this.userInput, index,
              this.toCommandsCommandList,
              this.toCommandsParameters);
          currentListOfCommands.addAll(bracesParser.getCommandsInsideGroup());
          index = bracesParser.calculateLocOfLastBlock(index + 1);
        } else {
          currentListOfCommands.add(input);
        }
      }
      this.userInput = currentListOfCommands.toArray(new String[0]);
    } catch (Exception e) {
      errorMessages += "Error parsing group commands";
    }

  }

  private void checkForControlCommands() {
    try {
      for (int index = 0; index < this.userInput.length; index++) {
        String input = this.userInput[index];
        if (!SUPERCOMMANDS.contains(input)) {
          listOfCommandsAndStrings.add(input); //Object Array
        } else {
          SuperCommandGenerator superCommandGenerator = createInstanceOfCommandGenerator(
              index, input);
          Object command = superCommandGenerator.buildCommand();
          listOfCommandsAndStrings.add(command);
          if (SUPERCOMMANDSWITHEXPR.contains(input)) {
            List<String> expr = superCommandGenerator.getExprOfBlock(index);
            if (expr != null) {
              listOfCommandsAndStrings.addAll(expr);
            }
          }
          index = superCommandGenerator.getIndexOfEndOfBlock(input, index);
        }
      }
    } catch (Exception e) {
      errorMessages += "Error parsing control commands";
    }
  }

  private SuperCommandGenerator createInstanceOfCommandGenerator(int index, String input)
      throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
    Constructor[] constructors = getCommandGeneratorClass(input).getDeclaredConstructors();
    Object[] parameters = new Object[]{input,
        this.userInput, index,
        this.toCommandsCommandList, this.toCommandsParameters, this.toCommandStrings,
        this.language};
    SuperCommandGenerator superCommandGenerator = (SuperCommandGenerator)
        constructors[0].newInstance(parameters);
    return superCommandGenerator;
  }


  private boolean checkIfObjectIsString(Object obj) {
    return obj instanceof String;
  }


  private List<List<Object>> getStringsWithParams() {
    Object[] inputs = this.listOfCommandsAndStrings.toArray();
    List<List<Object>> commands = new ArrayList<>();

    for (int index = 0; index < inputs.length; index++) {
      Object input = inputs[index];
      if (!input.equals(TAKEN)) {
        List<Object> arguments = new ArrayList<>();
        if (checkIfObjectIsString(input)) {
          arguments = dealWithString(input, arguments, index, inputs);
        } else {
          arguments.add(input);
        }
        commands.add(arguments);
      }
    }
    return commands;
  }

  private List<Object> dealWithString(Object input, List<Object> arguments, int index,
      Object[] inputs) {
    if (isCommand((String) input) || toCommandsParameters.containsKey(input)) {
      arguments.add(input);
      int expectedArgs = getExpectedArgs(input);
      if (expectedArgs == 1) {
        arguments = dealWithOneArg(index, inputs, arguments);
      } else if (expectedArgs >= 2) {
        dealWithTwoArgs(index, expectedArgs, arguments, inputs);
      }
    } else {
      arguments
          .add(input); //if not a command, then just add the integer unassigned to a command
    }
    return arguments;
  }

  private void dealWithTwoArgs(int index, int expectedArgs, List<Object> arguments,
      Object[] inputs) {
    for (int subIndex = index + 1; subIndex <= index + expectedArgs; subIndex++) {
      if (subIndex == inputs.length) {
        break;
      }
      if (checkIfObjectIsString(inputs[subIndex])) {
        String nextArg = (String) inputs[subIndex];
        if (!isCommand(nextArg)) { //if I have a number next to me, take it as an arg
          arguments.add(nextArg);
          inputs[subIndex] = TAKEN;
        } else {
          arguments.add(DEFAULT_PARAM);
          break; //if a command is next to me, break, no args for me right now
        }
      }
    }
  }

  private List<Object> dealWithOneArg(int index, Object[] inputs, List<Object> arguments) {
    String nextArg = (String) inputs[index + 1];
    if (isNumericOrVariable(nextArg)) {
      arguments.add(nextArg);
      inputs[index + 1] = TAKEN;
    } else {
      arguments.add(DEFAULT_PARAM);
    }
    return arguments;
  }

  /*
  Will now see if there are any free args available to "complete" args for commands
   */

  private List<List<Object>> assignArguments(List<List<Object>> commands) {
    for (int index = commands.size() - 1; index >= 0; index--) {
      List<Object> command = commands.get(index);
      if (nextIndexIsSpaceOrInteger(command)) {
        continue;
      }
      String commandName = (String) command.get(0);
      if (isCommand(commandName)) {
        int expectedArgs = getExpectedArgs(commandName);
        int currentArgs = command.size() - 1;
        if (currentArgs == expectedArgs) {
          continue;
        }
        for (int subIndex = index + 1; subIndex < commands.size(); subIndex++) {
          List<Object> cmd = commands.get(subIndex);
          if (nextIndexIsValidArgument(cmd)) {
            command.add(cmd.get(0));
            cmd.clear();
            currentArgs = command.size() - 1;
          }
          if (currentArgs == expectedArgs) {
            break;
          }
        }
      }
    }
    addDefaultParameter(commands);
    return commands;
  }

  private boolean nextIndexIsSpaceOrInteger(List<Object> command) {
    Object next = command.get(0);
    return !checkIfObjectIsString(next);
  }

  private boolean nextIndexIsValidArgument(List<Object> cmd) {
    return cmd.size() == 1 && cmd.get(0) instanceof String && !isCommand((String) cmd.get(0));
  }

  private void addDefaultParameter(List<List<Object>> commands) {
    for (List<Object> command : commands) {
      if (command.size() > 0 && checkIfObjectIsString(command.get(0))) {
        String commandName = (String) command.get(0);
        int expectedArgs = getExpectedArgs(commandName);
        while (command.size() <= expectedArgs) {
          command.add(DEFAULT_PARAM);
        }
      }
    }
  }

  private int getExpectedArgs(Object input) {
    if (checkIfObjectIsString(input)) {
      String str = (String) input;
      if (toCommandsParameters.containsKey(str)) {
        return toCommandsParameters.get(str).size();
      }
      myResourceBundle = ParserFiles.ARGS_FILE.returnBundle();
      return Integer.parseInt(myResourceBundle.getString(str));
    }
    return 0;
  }

  private boolean isCommand(String input) {
    myResourceBundle = ParserFiles.COMMAND_INFO_FILE.returnBundle();
    for (String key : myResourceBundle.keySet()) {
      if (myResourceBundle.getString(key).contains(input) || key.equals(input)) {
        return true;
      }
    }
    return false;
  }

  /**
   * determins if string is numeric or a variable
   * @param strNum string to determine type
   * @return true if it is numeric or a variable
   */
  public static boolean isNumericOrVariable(String strNum) {
    if (strNum == null) {
      return false;
    }
    if (strNum.charAt(0) == ':') {
      return true;
    }
    try {
      double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  /**
   * Returns error message from the parser2
   * @return error message
   */
  public String getErrorMessages() {
    return this.errorMessages;
  }

  private boolean noError() {
    return this.errorMessages.equals("");
  }
}
