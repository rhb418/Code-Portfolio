package slogo.model.parser.CommandGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import slogo.commands.Command;
import slogo.model.parser.Files.ParserFiles;

/**
 * @author billyluqiu
 *
 * Abstract Class that represents a commandGenerator that will generate a command object with
 * all the requisite parameters filled in
 *
 * Assumptions:
 * Parameters will be inside the command_converter_file
 * All resource files will include the proper command properties
 * Commands will be valid command names
 *
 * Dependencies: Depends on parserfiles to specify command names/class names/parameters
 *
 * Example on how to use, call buildCommand to create command object
 *
 */

public abstract class CommandGenerator {

  public static final String FRONT_BRACKET = "[";
  public static final String BACK_BRACKET = "]";
  public static final String CLASS_FORMAT = "class";
  public static final String EMPTY = "";

  /**
   * Method that will create a command representing the current command
   * @return Command object
   * @throws InvocationTargetException if command name not valid
   * @throws IllegalAccessException if permissions are wrong
   */

  public abstract Command buildCommand() throws InvocationTargetException, IllegalAccessException;

  public String convertCommand(String command) {
    ResourceBundle myResourceBundle = ParserFiles.COMMAND_CONVERTER_FILE.returnBundle();
    for (String key : myResourceBundle.keySet()) {
      if (command.equals(key)) {
        command = myResourceBundle.getString(key);
      }
    }
    return command;
  }

  /**
   * get Class that represents the Class of the currentCommand
   * @param cmd string represnting the command
   * @return Class Object that is the class of the current command
   */
  protected Class getCommandType(String cmd) {
    Class cls = null;
    String className = EMPTY;
    try {
      className = getClassName(cmd, className);
      String format = getFormat(className);
      cls = Class.forName(format + className);
    } catch (Exception e) {
      return null;
    }
    return cls;
  }

  private String getClassName(String cmd, String className) {
    ResourceBundle myResourceBundle = ParserFiles.CLASS_NAMES_FILE.returnBundle();
    for (String key : myResourceBundle.keySet()) {
      if (myResourceBundle.getString(key).contains(cmd) || key.equals(cmd)) {
        className = key;
        break;
      }
    }
    return className;
  }

  private String getFormat(String className) {
    ResourceBundle myResourceBundle;
    myResourceBundle = ParserFiles.FORMAT_FILE.returnBundle();
    String format = EMPTY;
    for (String key : myResourceBundle.keySet()) {
      String[] curValueArray = myResourceBundle.getString(key).split("\\|");
      for (String s : curValueArray) {
        s = s.replace("\\", "");
        if (s.equalsIgnoreCase(className)) {
          format = key;
          break;
        }
      }
    }
    return format;
  }

  /**
   * Method that returns method for the correct method
   * @param input method name
   * @return method that will create the correct command object
   */
  protected Method getStructureMethod(String input) {
    Method method = null;
    try {
      String className = String.valueOf(this.getClass()).replace(CLASS_FORMAT, EMPTY).trim();
      ResourceBundle myResourceBundle = ParserFiles.TOKENIZER_INFO_FILE.returnBundle();
      String toCall = myResourceBundle.getString(input);
      method = Class.forName(className).getDeclaredMethod(toCall, String.class, int.class);
    } catch (Exception e) {
      return null;
    }
    return method;
  }

}
