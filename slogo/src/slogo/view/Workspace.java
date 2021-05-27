package slogo.view;

import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import slogo.controller.BackEndController;
import slogo.controller.FrontEndController;
import slogo.view.component.clickable.ClickableFactory;
import slogo.view.component.clickable.buttonclickable.HelpButton;
import slogo.view.component.clickable.buttonclickable.LoadPreferencesButton;
import slogo.view.component.clickable.buttonclickable.SavePreferencesButton;
import slogo.view.component.clickable.buttonclickable.ShowCommandHistoryButton;
import slogo.view.component.clickable.menubutton.BackgroundColorMenuButton;
import slogo.view.component.clickable.menubutton.LanguageMenuButton;
import slogo.view.component.clickable.menubutton.LineColorMenuButton;
import slogo.view.component.clickable.menubutton.PenMenuButton;
import slogo.view.component.clickable.menubutton.TurtleMenuButton;
import slogo.view.component.clickable.menubutton.TurtleShapeMenuButton;
import slogo.view.component.subview.CodeEntryBox;
import slogo.view.component.subview.ColorPalette;
import slogo.view.component.subview.ConsoleGraphicsDisplay;
import slogo.view.component.subview.ShapePalette;
import slogo.view.component.subview.TurtleGraphicsDisplay;
import slogo.view.component.subview.TurtleState;
import slogo.view.component.subview.UserCommands;
import slogo.view.component.subview.UserVariables;
import slogo.view.mediator.CodeEntryBoxMediator;
import slogo.view.mediator.ConsoleGraphicsDisplayMediator;
import slogo.view.mediator.SavePreferencesButtonMediator;
import slogo.view.mediator.ShapePaletteMediator;
import slogo.view.mediator.WorkspaceMediator;

/**
 * The Workspace class represents all the components that make up a single IDE.
 *
 * Handles the creation of all components, mediators, and adding observers. Also keeps track of
 * the language of the simulation and updates the Parser accordingly.
 *
 * @author Bill Guo
 */
public class Workspace {

  private GridPane masterLayout;
  private ResourceBundle labelBundle;
  private ResourceBundle shapeBundle;
  private ResourceBundle colorBundle;
  private String language;

  private BackEndController backEndController;
  private FrontEndController frontEndController;

  private TurtleGraphicsDisplay turtleGraphicsDisplay;
  private ConsoleGraphicsDisplay consoleGraphicsDisplay;
  private TurtleState turtleState;
  private ColorPalette colorPalette;
  private ShapePalette shapePalette;
  private UserVariables userVariables;
  private UserCommands userCommands;
  private CodeEntryBox codeEntryBox;

  private ClickableFactory clickableFactory;
  private HBox toolbar;
  private HelpButton helpButton;
  private LoadPreferencesButton loadPreferencesButton;
  private SavePreferencesButton savePreferencesButton;
  private BackgroundColorMenuButton backgroundColorMenuButton;
  private LanguageMenuButton languageMenuButton;
  private LineColorMenuButton lineColorMenuButton;
  private PenMenuButton penMenuButton;
  private TurtleMenuButton turtleMenuButton;
  private TurtleShapeMenuButton turtleShapeMenuButton;
  private ShowCommandHistoryButton showCommandHistoryButton;

  private CodeEntryBoxMediator codeEntryBoxMediator;
  private ConsoleGraphicsDisplayMediator consoleGraphicsDisplayMediator;
  private SavePreferencesButtonMediator savePreferencesButtonMediator;
  private WorkspaceMediator workspaceMediator;
  private ShapePaletteMediator shapePaletteMediator;

  /**
   * Constructor of the Workspace.
   * @param labelBundle default labels of a new Workspace
   * @param shapeBundle default shape palette of a new Workspace
   * @param colorBundle default color palette of a new Workspace
   * @param languageChosen default language of a new Workspace
   */
  public Workspace(ResourceBundle labelBundle, ResourceBundle shapeBundle,
      ResourceBundle colorBundle, String languageChosen) {
    this.labelBundle = labelBundle;
    this.shapeBundle = shapeBundle;
    this.colorBundle = colorBundle;
    language = languageChosen;
  }

  /**
   * Creates the GridPane that is shown on the stage, handling the creation of all components
   * within the GridPane.
   * @return GridPane representing the Workspace
   */
  public GridPane initialize() {
    masterLayout = makeMasterLayout();
    makeControllers();
    makeClickables();

    toolbar = makeToolbar();
    masterLayout.add(toolbar, 0, 0, 4, 1);

    turtleGraphicsDisplay = makeTurtleGraphicsDisplay();
    masterLayout.add(turtleGraphicsDisplay.getView(), 0, 1, 1, 2);

    consoleGraphicsDisplay = makeConsoleGraphicsDisplay();
    masterLayout.add(consoleGraphicsDisplay.getView(), 1, 3, 2, 1);

    codeEntryBox = makeCodeEntryBox();
    masterLayout.add(codeEntryBox.getView(), 0, 3);

    turtleState = makeTurtleState();
    masterLayout.add(turtleState.getView(), 1, 1);

    colorPalette = makeColorPalette();
    masterLayout.add(colorPalette.getView(), 2, 1);

    shapePalette = makeShapePalette();
    masterLayout.add(shapePalette.getView(), 3, 1);

    userVariables = makeUserVariables();
    masterLayout.add(userVariables.getView(), 2, 2);

    userCommands = makeUserCommands();
    masterLayout.add(userCommands.getView(), 1, 2);

    addObservers();
    makeMediators();
    addMediators();

    return masterLayout;
  }

  private GridPane makeMasterLayout() {
    GridPane returnPane = new GridPane();
    returnPane.getStyleClass().add("workspace-gridpane");
    return returnPane;
  }

  private void makeControllers() {
    backEndController = new BackEndController(colorBundle);
    frontEndController = new FrontEndController(backEndController, language, colorBundle);
  }

  private TurtleGraphicsDisplay makeTurtleGraphicsDisplay() {
    return new TurtleGraphicsDisplay("TurtleView", labelBundle, backEndController, shapeBundle);
  }

  private ConsoleGraphicsDisplay makeConsoleGraphicsDisplay() {
    return new ConsoleGraphicsDisplay("ConsoleView", labelBundle, backEndController);
  }

  private TurtleState makeTurtleState() {
    return new TurtleState("TurtleState", labelBundle, backEndController);
  }

  private ColorPalette makeColorPalette() {
    return new ColorPalette("ColorPalette", labelBundle, backEndController);
  }

  private ShapePalette makeShapePalette() {
    return new ShapePalette("ShapePalette", labelBundle, backEndController, shapeBundle);
  }

  private UserCommands makeUserCommands() {
    return new UserCommands("UserCommands", labelBundle, backEndController);
  }

  private UserVariables makeUserVariables() {
    return new UserVariables("UserVariables", labelBundle, backEndController);
  }

  private CodeEntryBox makeCodeEntryBox() {
    return new CodeEntryBox("CodeEntryBox", labelBundle, backEndController, frontEndController,
        language);
  }

  private void makeClickables() {
    clickableFactory = new ClickableFactory(language, backEndController, shapeBundle);
    savePreferencesButton = clickableFactory.makeSavePreferencesButton();
    loadPreferencesButton = clickableFactory.makeLoadPreferencesButton();
    turtleMenuButton = clickableFactory.makeTurtleMenuButton();
    penMenuButton = clickableFactory.makePenMenuButton();
    lineColorMenuButton = clickableFactory.makeLineColorMenuButton();
    backgroundColorMenuButton = clickableFactory.makeBackgroundColorMenuButton();
    turtleShapeMenuButton = clickableFactory.makeTurtleShapeMenuButton();
    helpButton = clickableFactory.makeHelpButton();
    languageMenuButton = clickableFactory.makeLanguageMenuButton();
    showCommandHistoryButton = clickableFactory.makeShowCommandHistoryButton();
    makeIDs();
  }

  private void makeIDs() {
    penMenuButton.getMenuButton().setId("PenMenuButton");
    languageMenuButton.getMenuButton().setId("LanguageMenuButton");
    turtleShapeMenuButton.getMenuButton().setId("TurtleShapeMenuButton");
    lineColorMenuButton.getMenuButton().setId("LineColorMenuButton");
    backgroundColorMenuButton.getMenuButton().setId("BackGroundColorMenuButton");
    turtleMenuButton.getMenuButton().setId("TurtleMenuButton");
    helpButton.getButton().setId("HelpButton");
    savePreferencesButton.getButton().setId("SavePreferencesButton");
    loadPreferencesButton.getButton().setId("LoadPreferencesButton");
    showCommandHistoryButton.getButton().setId("HistoryButton");
  }

  private HBox makeToolbar() {
    HBox returnHBox = new HBox();
    returnHBox.getStyleClass().add("workspace-toolbar");
    returnHBox.getChildren().addAll(savePreferencesButton.getButton(),
        loadPreferencesButton.getButton(), turtleMenuButton.getMenuButton(),
        penMenuButton.getMenuButton(), lineColorMenuButton.getMenuButton(),
        backgroundColorMenuButton.getMenuButton(), turtleShapeMenuButton.getMenuButton(),
        showCommandHistoryButton.getButton(), helpButton.getButton(),
        languageMenuButton.getMenuButton());
    return returnHBox;
  }

  private void addObservers() {
    codeEntryBox.attach(turtleGraphicsDisplay);
    codeEntryBox.attach(consoleGraphicsDisplay);
    codeEntryBox.attach(turtleState);
    codeEntryBox.attach(colorPalette);
    codeEntryBox.attach(userVariables);
    codeEntryBox.attach(userCommands);
    codeEntryBox.attach(lineColorMenuButton);
    codeEntryBox.attach(backgroundColorMenuButton);
  }

  private void makeMediators() {
    workspaceMediator = new WorkspaceMediator(this);
    codeEntryBoxMediator = new CodeEntryBoxMediator(codeEntryBox, language);
    savePreferencesButtonMediator = new SavePreferencesButtonMediator(savePreferencesButton);
    shapePaletteMediator = new ShapePaletteMediator(shapePalette);
    consoleGraphicsDisplayMediator = new ConsoleGraphicsDisplayMediator(consoleGraphicsDisplay);
  }

  // Order matters! Mediators assigned first will be notified first.
  private void addMediators() {
    backgroundColorMenuButton.addMediator(codeEntryBoxMediator);
    lineColorMenuButton.addMediator(codeEntryBoxMediator);
    penMenuButton.addMediator(codeEntryBoxMediator);
    turtleMenuButton.addMediator(codeEntryBoxMediator);
    turtleShapeMenuButton.addMediator(codeEntryBoxMediator);
    userVariables.addMediator(codeEntryBoxMediator);
    userCommands.addMediator(codeEntryBoxMediator);

    codeEntryBox.addMediator(savePreferencesButtonMediator);
    shapePalette.addMediator(savePreferencesButtonMediator);
    consoleGraphicsDisplay.addMediator(savePreferencesButtonMediator);

    languageMenuButton.addMediator(workspaceMediator);

    loadPreferencesButton.addMediator(workspaceMediator);
    loadPreferencesButton.addMediator(shapePaletteMediator);
    loadPreferencesButton.addMediator(codeEntryBoxMediator);

    showCommandHistoryButton.addMediator(consoleGraphicsDisplayMediator);
  }

  /**
   * Sets the language of the Workspace. Used by its mediator to change the language of other
   * components that depend on language as well.
   * @param language String representing new language
   */
  public void setLanguage(String language) {
    this.language = language;
    frontEndController.updateLanguage(language);
    codeEntryBox.setLanguage(language);
  }

}
