package slogo.view.component.clickable.buttonclickable;

import java.io.File;
import java.io.FileOutputStream;
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
 * The SavePreferencesButton class allows a Resource file to be written that documents the
 * language, shape palette, and ran commands of the current Workspace.
 *
 * Intended to be used when a user is done with the Workspace and wants to come back to it later.
 * Uses a mediator to:
 * Get the language from CodeEntryBox when the language gets changed.
 * Get the path to the shapePalette from ShapePalette when shape palette gets changed.
 * Get the previous successful commands from the ConsoleGraphicsDisplay when a new command is run.
 *
 * @author Bill Guo, Tomas Esber
 */
public class SavePreferencesButton extends ButtonClickable {

  private String language;
  private String shapePalette;
  private String code;

  /**
   * Constructor of SavePreferencesButton.
   * @param image Icon for Button
   * @param workspaceLanguage Starting language of simulation
   * @param shapeBundle Path to starting ShapeBundle
   */
  public SavePreferencesButton(Image image, String workspaceLanguage, ResourceBundle shapeBundle) {
    super(image);
    language = workspaceLanguage;
    shapePalette = shapeBundle.getBaseBundleName();
    code = "";
  }

  /**
   * Returns the EventHandler of a SavePreferencesButton. In this case, the button will save the
   * current language, shape palette, and commands ran to a Resource file.
   * @return EventHandler to save current workspace
   */
  @Override
  protected EventHandler<ActionEvent> onClick() {
    return event -> savePreferences();
  }

  private void savePreferences() {
    File propertiesFile = getFile();
    if (propertiesFile == null) {
      makeAlert("File not selected");
    } else {
      Properties properties = assignPreferences();
      try {
        FileOutputStream fr = new FileOutputStream(propertiesFile);
        properties.store(fr, "Properties");
        fr.close();
      } catch (IOException ioException) {
        makeAlert("Invalid file");
      }
    }
  }

  private Properties assignPreferences() {
    Properties properties = new Properties();
    try {
      Field[] fields = this.getClass().getDeclaredFields();
      for (Field field : fields) {
        String key = field.getName();
        String value = (String) field.get(this);
        if (properties.contains(key)) {
          properties.setProperty(key, value);
        } else {
          properties.put(key, value);
        }
      }
    } catch (IllegalAccessException e) {
      makeAlert("Can't access class");
    }
    return properties;
  }

  private File getFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Properties Files", "*.properties"));
    File propertiesFile = fileChooser.showOpenDialog(getButton().getScene().getWindow());
    return propertiesFile;
  }

  /**
   * Sets the language. Used by SavePreferencesButtonMediator to change the language whenever the
   * language changes in the Workspace.
   * @param language String that represents current language
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * Sets the file path to ShapePalette. Used by SavePreferencesButtonMediator to set the new
   * file path when the shape palette gets changed.
   * @param shapePalette String that represents file path to shape palette
   */
  public void setShapePalette(String shapePalette) {
    this.shapePalette = shapePalette;
  }

  /**
   * Sets the block of code that has ran in the simulation. Used by SavePreferencesButtonMediator
   * to update the code ran when a new command gets inputted.
   * @param code String that represents code
   */
  public void setCode(String code) {
    this.code = code;
  }
}
