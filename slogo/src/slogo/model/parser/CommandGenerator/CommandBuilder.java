package slogo.model.parser.CommandGenerator;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;
import slogo.commands.Command;

/**
 * Helps Parser construct Command Objects from Strings using Java Reflection API
 */
public class CommandBuilder {

  private final Class myCommandClass;
  private final String myCommandSubClass;
  private final ResourceBundle myResourceBundle;

  private static final String PARAMS_FILE = "slogo.resources.classInfo.ClassParams";

  public CommandBuilder(Class commandClass, String commandSubClass) {
    this.myCommandClass = commandClass;
    this.myResourceBundle = ResourceBundle.getBundle(PARAMS_FILE);
    this.myCommandSubClass = commandSubClass;
  }

  public Command createInstanceOfCommand(Object[] parameters) {
    try {
      Constructor[] constructors = myCommandClass.getDeclaredConstructors();
      int numParameters = getNumberConstructorParameters();

      for (Constructor constructor : constructors) {
        if (constructor.getParameterCount() == numParameters) {
          return (Command) constructor.newInstance(parameters);
        }
      }
    } catch (Exception e) {
      return null;
    }
    return null;
  }

  private int getNumberConstructorParameters() {
    return Integer.parseInt(myResourceBundle.getString(myCommandSubClass));
  }


}
