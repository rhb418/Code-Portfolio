package slogo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.turtle.LineCoords;
import slogo.turtle.TurtleData;

public class BackEndController extends Controller {

  private List<TurtleData> listOfTurtleData;
  private Map<String, Double> mapOfVariables;
  private List<String> listOfUserFunctions;
  private String errorMessage;
  private Map<String, String> toCommandString;
  private List<String> pastCommands;
  private List<Integer> activeTurtles;

  public BackEndController(ResourceBundle colorBundle) {
    super(colorBundle);
    mapOfVariables = new HashMap<>();
    listOfUserFunctions = new ArrayList<>();
    listOfTurtleData = new ArrayList<>();
    toCommandString = new HashMap<>();
    errorMessage = "";
    pastCommands = new ArrayList<>();
    activeTurtles = new ArrayList<>();
    activeTurtles.add(0);
    setStartupGraphics();
  }

  private void setStartupGraphics() {
    List<LineCoords> startingLines = new ArrayList<>();
    Map<Double, double[]> startingPalette = startingColorPalette;
    TurtleData initialData = new TurtleData(startingLines, 0, 0, 0, 0, true, true, 0, 0, 0,
        startingPalette, 1);
    listOfTurtleData.add(initialData);
  }

  public void setErrorMessage(String error) {
    errorMessage = error;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setMapOfVariables(HashMap<String, Double> vars) {
    mapOfVariables = vars;
  }

  public Map<String, Double> getCurrentVariables() {
    return Collections.unmodifiableMap(mapOfVariables);
  }

  public void setTurtleData(List<TurtleData> newData) {
    listOfTurtleData = newData;
  }

  public List<TurtleData> getTurtleData() {
    return listOfTurtleData;
  }

  public void setToCommandString(Map<String, String> toCommandString) {
    this.toCommandString = toCommandString;
  }

  public Map<String, String> getToCommandString() {
    return Collections.unmodifiableMap(toCommandString);
  }

  public void addExecutedCommand(String command) {
    pastCommands.add(command);
  }

  public List<String> getPastCommands() {
    return Collections.unmodifiableList(pastCommands);
  }

  public void setActiveTurtles(List<Integer> AT) {
    activeTurtles = AT;
  }

  public List<Integer> getActiveTurtles() {
    return activeTurtles;
  }
}
