package ooga.model.observables;

import java.util.List;
import java.util.function.Consumer;
import ooga.model.StatsInfo;

/**
 * A Level that can be observed by the view
 * @author Oliver Rodas
 */
public interface ObservableLevel {

  /**
   * @return the background ID of the level
   */
  String getBackgroundID();

  /**
   * @return the height of the level area
   */
  int getHeight();

  /**
   * @return the width of the level
   */
  int getWidth();

  /**
   * Set the callback that is called, when the game object that's being focused on by main camera is
   * changed
   *
   * @param callback The integer represents the object id
   */
  void setOnFocusUpdate(Consumer<ObservableObject> callback);

  /**
   * Set the callback that is called, when a certain statistics is updated, such as "score", or
   * "position"
   */
  void setOnStatsUpdate(String statsName, Consumer<List<StatsInfo>> callback);

  /**
   * Retrieve the names of all statistics that's available in this game
   *
   * <p>The returned strings can be used for the first parameter in {@link
   * ObservableLevel#setOnStatsUpdate(String, Consumer)}
   */
  List<String> getAvailableStats();

  /**
   * Get the names of all collision action available in this game level
   */
  List<String> getAvailableActions();

  /**
   * Get the names of all input action available in this game level
   */
  List<String> getAvailableInputs();

  /**
   * Set a callback for when a new object is made
   * @param callback the callback to use
   */
  void setOnNewObject(Consumer<ObservableObject> callback);

  /**
   * Set a callback for when an object is destoryed
   * @param callback the callback to use
   */
  void setOnObjectDestroy(Consumer<ObservableObject> callback);

  /**
   * @return the game objects in a level
   */
  List<? extends ObservableObject> getAvailableGameObjects();
}
