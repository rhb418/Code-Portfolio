package slogo.model.parser.CommandGenerator;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.commands.Command;
import slogo.model.parser.Files.ParserFiles;

/**
 * @author billyluqiu and Tomas Esbar
 *Class that extends CommandGenerator in order to parse simple commands without blocks tructures
 *
 * Assumptions: same as CommandGenerator Class
 *
 * Dependencies: same as CommandGenerator Class
 *
 * Example on how to use, call buildCommand to create command object
 *
 */
public class SimpleCommandGenerator extends CommandGenerator {

  private final Map<String, List<String>> toCommandsParameters;
  private final Map<String, List<Command>> toCommandsCommandList;
  private final List<Object> command;
  private final String TOCOMMAND = "MakeUserInstruction";
  private final String MAKE_VAR = "MakeVariable";
  private final String SET_PALETTE = "SetPalette";

  /**
   * Constructor for the simple command generator
   *
   * @param command List of command strings and other commands (such as super commands) that are the
   *                parameters for the simplecommand
   * @param toCommandList map mapping toCommands to their commandValues
   * @param toCommandParams map mapping toCommandNames to their params
   */


  public SimpleCommandGenerator(List<Object> command, Map<String, List<Command>> toCommandList,
      Map<String, List<String>> toCommandParams) {
    this.toCommandsCommandList = toCommandList;
    this.toCommandsParameters = toCommandParams;
    this.command = command;
  }

  /**
   * generates the command object based off the command type and the number of parameters
   * it has
   * @return command
   */
  public Command buildCommand() {
    if (command.size() == 1 && command.get(0) instanceof Command) {
      return (Command) command.get(0);
    } else {
      String commandType = (String) command.get(0);
      if (toCommandsParameters.containsKey(commandType)) {
        String input = convertCommand(TOCOMMAND);
        CommandBuilder temp = new CommandBuilder(getCommandType(input), "MakeUserInstruction");
        return temp.createInstanceOfCommand(getParametersForCustomCommand(command, commandType));
      }
      if (commandType.equals(MAKE_VAR)) {
        String input = convertCommand(commandType);
        CommandBuilder temp = new CommandBuilder(getCommandType(input), commandType);
        return temp.createInstanceOfCommand(getParametersForMakeCommand(command));
      }
      String input = convertCommand(commandType);
      CommandBuilder temp = new CommandBuilder(getCommandType(input), commandType);
      return temp.createInstanceOfCommand(getParametersMathTurtleBoolOps(command));
    }
  }

  private Object[] getParametersForMakeCommand(List<Object> command) {
    String[] params = getStringsParamsArray(command);
    Object[] args = new Object[1];
    args[0] = params;
    return args;
  }

  private Object[] getParametersForCustomCommand(List<Object> command, String input) {
    String[] params = getStringsParamsArray(command);
    Object[] args = new Object[4];
    args[0] = params;
    args[1] = toCommandsCommandList.get(input);
    args[2] = toCommandsParameters.get(input);
    args[3] = input;
    return args;
  }

  private Object[] getParametersMathTurtleBoolOps(List<Object> command) {
    //number of Parameters for YOUR Command being built
    //adds parameters to the parameters ArrayList<Integer>
    //Converts parameters ArrayList to String[]
    String[] params = getStringsParamsArray(command);
    String commandString = (String) command.get(0);
    int numParams = getNumConstructorParams(commandString);
    Object[] args = new Object[numParams];
    args[0] = params;
    //Finds CommandConstant and passes it to Object[] ONLY if Math or Bool Op
    if (numParams >= 2 && !commandString.equals(SET_PALETTE)) {
      ResourceBundle myResourceBundle = ParserFiles.CONSTANTS_FILE.returnBundle();
      int constant = Integer
          .parseInt(myResourceBundle.getString(commandString.toUpperCase(Locale.ROOT)));
      args[1] = constant;
    }
    //return Object[] with MathOps/BoolOps parameters (a String[] and an int)
    return args;
  }


  private String[] getStringsParamsArray(List<Object> command) {
    String[] params = new String[command.size() - 1];
    for (int i = 1; i < command.size(); i++) {
      if (command.get(i).equals("x")) {
        params[i - 1] = null;
      } else {
        params[i - 1] = (String) command.get(i);
      }
    }
    return params;
  }

  private int getNumConstructorParams(String input) {
    ResourceBundle myResourceBundle = ParserFiles.CONSTRUCTOR_INFO_FILE.returnBundle();
    return Integer.parseInt(myResourceBundle.getString(input));
  }

}
