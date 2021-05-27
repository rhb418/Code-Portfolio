/**
 * @author Tinglong Zhu
 */
package ooga.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyCode;
import ooga.model.Model;

public class Controller implements ModelController {

  // code -> actions
  private Map<KeyCode, String> code2action = Map.of(
      KeyCode.A, "left", KeyCode.D, "right",
      KeyCode.SPACE, "jump", KeyCode.ESCAPE, "pause"
  );
  private Model model;

  public Controller(Model model) {
    this.model = model;
  }

  /**
   * Proxy Model
   * @param levelName
   * @throws FileNotFoundException
   */
  @Override
  public void setCurrentLevel(String levelName) throws FileNotFoundException {
    model.setCurrentLevel(levelName);
  }


  @Override
  public void handleKeyPress(KeyCode code) {
    System.out.println(code);
    model.handleCode(code2action.get(code), true);
  }

  @Override
  public void handleKeyRelease(KeyCode code) {
    model.handleCode(code2action.get(code), false);
  }

  @Override
  public List<String> getAvailableActions() {
    return null;
  }

  /**
   * Set key map for the level.
   * @param map keymap for actions
   */
  public void setKeyMap(Map<KeyCode, String> map) {
    this.code2action = map;
  }

  @Override
  public void setStrokeMapping(KeyCode code, String action) {
    code2action.put(code, action);
  }

  @Override
  public List<String> getLevels() {
    return null;
  }

  @Override
  public void step(double delta) {
    model.step(delta);
  }

  @Override
  public void setGame(File gameDirectory) throws FileNotFoundException {
    model.setGame(gameDirectory);
  }
}
