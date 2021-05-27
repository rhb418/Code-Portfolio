package ooga.model;

import java.util.List;

public interface ModelController {
  void setLevel(String levelName);

  void handleKeyPress(String code);

  void handleKeyRelease(String code);

  /**
   * Returns a list of the strings which can trigger certain actions within the model.
   *
   * @return The strokes that can be used to trigger actions.
   */
  List<String> getAvailbleActions();

  /**
   * Set the stroke mapping for a certain action
   *
   * @param stroke - The key that will be pressed
   * @param actions - The action that will result from the pressed key
   */
  void setStrokeMapping(String code, String action);
}
