package ooga.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyCode;

public interface ModelController {

  void setCurrentLevel(String levelName) throws FileNotFoundException;

  void handleKeyPress(KeyCode code);

  void handleKeyRelease(KeyCode code);

  void setKeyMap(Map<KeyCode, String> map);

  /**
   * Returns a list of the strings which can trigger certain actions within the model.
   *
   * @return The strokes that can be used to trigger actions.
   */
  List<String> getAvailableActions();

  /**
   * Set the stroke mapping for a certain action
   *
   * @param stroke  - The key that will be pressed
   * @param actions - The action that will result from the pressed key
   */
  void setStrokeMapping(KeyCode code, String action);

  List<String> getLevels();

  void step(double delta);

  void setGame(File gameDirectory) throws FileNotFoundException;
}
