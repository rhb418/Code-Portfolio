package slogo.view.component.clickable.buttonclickable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * The LoadPreferencesButton class allows a Resource file to be read in order to change the
 * language, ShapePalette, and starting code of a simulation.
 *
 * Intended to be used when a program starts up in order to load a previous Workspace. Mediators
 * for a LoadPreferencesButton are CodeEntryBox to send starting code,  Workspace to change the
 * language being read, and ShapePalette to change the shape palette.
 *
 * @author Bill Guo, Tomas Esber
 */
public class LoadPreferencesButton extends ButtonClickable {

  private static final String TITLE = "Choose Saved Preferences";
  private static final String FILTER_NAME = "Properties File";
  private static final String FILTER = "*.properties";

  private String language;
  private String shapePalettePath;
  private String code;

  /**
   * Constructor for a LoadPreferencesButton.
   * @param image Icon for Button
   */
  public LoadPreferencesButton(Image image) {
    super(image);
  }

  /**
   * Returns the EventHandler associated with the button. In this case, opens a FileChooser when
   * a resource file can be chosen to load preferences.
   * @return EventHandler to choose preference file
   */
  @Override
  protected EventHandler<ActionEvent> onClick() {
    return event -> loadFile();
  }

  private void loadFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(TITLE);
    fileChooser.getExtensionFilters().add(new ExtensionFilter(FILTER_NAME, FILTER));
    try {
      String filePath = fileChooser.showOpenDialog(getButton().getScene().getWindow()).getPath();
      int startIndex = filePath.indexOf("slogo");
      filePath = filePath.substring(startIndex + 1);
      startIndex = filePath.indexOf("slogo");
      String adjustedFilePath = filePath.substring(startIndex, filePath.lastIndexOf('.'));
      goThroughFile(adjustedFilePath);
      checkFields();
      notifyMediator();
    } catch (NullPointerException e) {
      makeAlert("Bad File Read");
    }
  }

  private void goThroughFile(String path) {
    ResourceBundle bundle = ResourceBundle.getBundle(path);
    for (String key : bundle.keySet()) {
      assignValues(key, bundle.getString(key));
    }
  }

  private void assignValues(String key, String value) {
    try {
      Class<?> cls = this.getClass();
      Field field = cls.getDeclaredField(key);
      field.set(this, value);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      makeAlert("No Such Field");
    }
  }

  private void checkFields() {
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field field : fields) {
      try {
        if (field.get(this) == null) {
          makeAlert("WARNING: Not all fields were assigned");
          break;
        }
      } catch (IllegalAccessException e) {
        makeAlert("Illegal access of fields");
      }
    }
  }

  /**
   * Returns the language of the resource file. Needed by the Workspace mediator.
   * @return String that represents language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Returns the starting code of the resource file. Code must be written in the same language as
   * the chosen new language. Code must also be in the format "command|command|command|..." with
   * "|" acting as delimiters for the commands.
   * @return String that represents code
   */
  public String getCode() {
    return code;
  }

  /**
   * Returns the path to the shape palette of the resource file. The file must be in the
   * resources package.
   * @return String that represents file path to shape palette
   */
  public String getShapePalettePath() {
    return shapePalettePath;
  }
}
