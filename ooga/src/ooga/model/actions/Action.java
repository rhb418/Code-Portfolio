package ooga.model.actions;

import com.squareup.moshi.Json;
import java.util.Map;

/**
 * This class is a way for a game object to receive an action from the config file.
 * This class is meant to be used using reflection and a json format. It is irrespective of what
 * creates the object
 *
 * This current iteration uses the json tag from moshi to rename the variable in the config file
 *
 * By creating this item and adding it to a collision list for a game object, the action information
 * can be used to by the collision system to dictate what action to implement.
 *
 * @author Oliver Rodas
 */
public class Action {

  private Map<String, String> payload;

  @Json(name = "action")
  private String name;

  /**
   * Gets the payload for the action using a map so that the contents do not matter
   * @return the map of the contents for an action
   */
  public Map<String, String>  getPayload() {
    return payload;
  }

  /**
   * Get the action name in order to map it to its handler
   * @return the name of the action
   */
  public String getName() {
    return name;
  }
}
