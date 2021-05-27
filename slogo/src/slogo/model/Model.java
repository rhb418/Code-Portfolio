package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.commands.Command;
import slogo.controller.BackEndController;
import slogo.model.parser.Parser2;
import slogo.turtle.Turtle;
import slogo.turtle.TurtleData;

/**
 * @author Robert Barnette, This class is the main backend class where commands are parsed and
 * executed.
 */
public class Model {

  private BackEndController myBEController;
  private CommandExecutor myCE;
  private HashMap<String, Double> myVars;
  private List<Turtle> myTurtles;
  private List<Integer> myActiveTurtles;
  private HashMap<String, List<Command>> myToSubCommands;
  private HashMap<String, List<String>> myToCommandParameters;
  private Map<String, String> toCommandStrings;
  private String language;

  /**
   * @param be       is the backend controller that turtle data is passed to.
   * @param palettes is the map of starting palettes.
   * @param language is the language the commands are  in. This constructor creates a new model
   *                 object
   */
  public Model(BackEndController be, Map<Double, double[]> palettes, String language) {
    myBEController = be;
    myVars = new HashMap<>();
    myTurtles = new ArrayList<>();
    myActiveTurtles = new ArrayList<>();
    myTurtles.add(new Turtle(0));
    myTurtles.get(0).initializePalettes(palettes);
    myActiveTurtles.add(0);
    myToSubCommands = new HashMap<>();
    myToCommandParameters = new HashMap<>();
    toCommandStrings = new HashMap<>();
    be.setToCommandString(toCommandStrings);
    this.language = language;
  }

  /**
   * @param command is the string command being executed. This method parses a string command,
   *                executes it, and then passes the result to the backend controller.
   */
  public void executeCommands(String command) {
    Parser2 parser = new Parser2(command, myToSubCommands, myToCommandParameters, toCommandStrings,
        language);
    List<Command> commands = null;
    try {
      commands = parser.newParser();
    } catch (Exception e) {
      myBEController.setErrorMessage("Error parsing command");
      return;
    }
    if (!parser.getErrorMessages().equals("")) {
      myBEController.setErrorMessage(parser.getErrorMessages());
      return;
    }

    myCE = new CommandExecutor(commands, myTurtles, myActiveTurtles, myVars);
    myCE.executeCommands();

    List<TurtleData> data = new ArrayList<>();
    for (int k = 0; k < myTurtles.size(); k++) {
      data.add(myTurtles.get(k).getTurtleData());
    }

    myBEController.setTurtleData(data);
    myBEController.setErrorMessage(myCE.getErrorMessage());
    myBEController.setMapOfVariables(myVars);
    myBEController.setActiveTurtles(myActiveTurtles);
    if (myCE.getErrorMessage() == null) {
      myBEController.addExecutedCommand(command);
    }

  }

  /**
   * @param newLanguage is the language the model is being set to
   */
  public void setLanguage(String newLanguage) {
    language = newLanguage;
  }


}
