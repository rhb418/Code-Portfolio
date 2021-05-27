package slogo.view.component.clickable;

import java.util.ResourceBundle;
import javafx.scene.image.Image;
import slogo.controller.BackEndController;
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

/**
 * The ClickableFactory class creates Clickables.
 * <p>
 * Used by the Workspace to create all clickables in the toolbar without knowing the implementation
 * of the Clickables.
 *
 * @author Bill Guo
 */
public class ClickableFactory {

  private static final String HELP_PNG = "slogo/resources/images/buttons/help.png";
  private static final String LOAD_PNG = "slogo/resources/images/buttons/load.png";
  private static final String SAVE_PNG = "slogo/resources/images/buttons/save.png";
  private static final String BACKGROUND_COLOR_PNG = "slogo/resources/images/buttons"
      + "/backgroundcolor.png";
  private static final String LINE_COLOR_PNG = "slogo/resources/images/buttons/linecolor.png";
  private static final String PEN_PNG = "slogo/resources/images/buttons/pen.png";
  private static final String TURTLE_PNG = "slogo/resources/images/buttons/turtle.png";
  private static final String TURTLE_SHAPE_PNG = "slogo/resources/images/buttons/shape.png";
  private static final String LANGUAGE_PNG = "slogo/resources/images/buttons/language.png";
  private static final String HISTORY_PNG = "slogo/resources/images/buttons/history.png";

  private static final String PEN_ROOT_BUNDLE = "slogo/resources/toolbar/menuButton/pen/";
  private static final String TURTLE_ROOT_BUNDLE = "slogo/resources/toolbar/menuButton/turtle/";
  private static final String TURTLE_SHAPE_ROOT_BUNDLE = "slogo/resources/toolbar/menuButton"
      + "/shapes/";
  private static final String LANGUAGE_BUNDLE = "slogo/resources/toolbar/menuButton/Language";

  private BackEndController backEndController;
  private String language;
  private ResourceBundle shapeBundle;

  /**
   * Constructor of the ClickableFactory.
   *
   * @param language          language of the workspace
   * @param backEndController BackEndController of the workspace
   * @param shapeBundle       Resource file of the shape palette
   */
  public ClickableFactory(String language, BackEndController backEndController,
      ResourceBundle shapeBundle) {
    this.language = language;
    this.backEndController = backEndController;
    this.shapeBundle = shapeBundle;
  }

  /**
   * Returns a HelpButton.
   *
   * @return HelpButton
   */
  public HelpButton makeHelpButton() {
    return new HelpButton(new Image(HELP_PNG));
  }

  /**
   * Returns a LoadPreferenceButton.
   *
   * @return LoadPreferenceButton
   */
  public LoadPreferencesButton makeLoadPreferencesButton() {
    return new LoadPreferencesButton(new Image(LOAD_PNG));
  }

  /**
   * Returns a SavePreferencesButton.
   *
   * @return SavePreferencesButton
   */
  public SavePreferencesButton makeSavePreferencesButton() {
    return new SavePreferencesButton(new Image(SAVE_PNG), language, shapeBundle);
  }

  /**
   * Returns a BackgroundColorMenuButton.
   *
   * @return BackgroundColorMenuButton
   */
  public BackgroundColorMenuButton makeBackgroundColorMenuButton() {
    return new BackgroundColorMenuButton(new Image(BACKGROUND_COLOR_PNG), backEndController);
  }

  /**
   * Returns a LineColorMenuButton.
   *
   * @return LineColorMenuButton
   */
  public LineColorMenuButton makeLineColorMenuButton() {
    return new LineColorMenuButton(new Image(LINE_COLOR_PNG), backEndController);
  }

  /**
   * Returns a PenMenuButton.
   *
   * @return PenMenuButton
   */
  public PenMenuButton makePenMenuButton() {
    ResourceBundle menuBundle = ResourceBundle.getBundle(PEN_ROOT_BUNDLE + language);
    return new PenMenuButton(new Image(PEN_PNG), menuBundle);
  }

  /**
   * Returns a TurtleMenuButton.
   *
   * @return TurtleMenuButton
   */
  public TurtleMenuButton makeTurtleMenuButton() {
    ResourceBundle menuBundle = ResourceBundle.getBundle(TURTLE_ROOT_BUNDLE + language);
    return new TurtleMenuButton(new Image(TURTLE_PNG), menuBundle);
  }

  /**
   * Returns a TurtleShapeMenuButton.
   *
   * @return TurtleShapeMenuButton
   */
  public TurtleShapeMenuButton makeTurtleShapeMenuButton() {
    ResourceBundle menuBundle = ResourceBundle.getBundle(TURTLE_SHAPE_ROOT_BUNDLE + language);
    return new TurtleShapeMenuButton(new Image(TURTLE_SHAPE_PNG), menuBundle);
  }

  /**
   * Returns a LanguageMenuButton.
   *
   * @return LanguageMenuButton
   */
  public LanguageMenuButton makeLanguageMenuButton() {
    ResourceBundle menuBundle = ResourceBundle.getBundle(LANGUAGE_BUNDLE);
    return new LanguageMenuButton(new Image(LANGUAGE_PNG), menuBundle);
  }

  /**
   * Returns a ShowCommandHistoryButton.
   *
   * @return ShowCommandHistoryButton
   */
  public ShowCommandHistoryButton makeShowCommandHistoryButton() {
    return new ShowCommandHistoryButton(new Image(HISTORY_PNG));
  }

}
