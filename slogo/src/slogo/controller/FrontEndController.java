package slogo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.model.Model;
import slogo.view.Observable;
import slogo.view.Observer;

public class FrontEndController extends Controller {

  private BackEndController backEndController;
  private String language;
  private List<String> commands;
  private Model myModel;

  public FrontEndController(BackEndController backEndController, String language,
      ResourceBundle colorBundle) {
    super(colorBundle);
    this.backEndController = backEndController;
    this.language = language;
    commands = new ArrayList<>();
    myModel = new Model(backEndController, startingColorPalette, language);
  }

  public void updateCommandHistory(String command) {
    commands.add(command);
    myModel.executeCommands(command);
  }

  public void updateLanguage(String language) {
    myModel.setLanguage(language);
  }
}
