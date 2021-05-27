package ooga.model.actions;

import java.util.Map;
import ooga.model.objects.GameObject;

/**
 * This class is meant to be passed to an action handler to perform the action specified by an action
 *
 * @author Oliver Rodas
 */
public class CollisionAction {

  private GameObject self;
  private GameObject hitter;
  private Map<String, String> payload;

  /**
   * Create a new collision action
   * @param self the object that was hit
   * @param hitter the object that hit
   * @param payload the payload of the action, if any
   */
  public CollisionAction(GameObject self, GameObject hitter, Map<String, String> payload) {
    this.self = self;
    this.hitter = hitter;
    this.payload = payload;
  }

  /**
   * Get the game object that was hit
   * @return the object that was hit
   */
  public GameObject getSelf() {
    return self;
  }

  /**
   * Get the game object that hit
   * @return get the game object that hit
   */
  public GameObject getHitter() {
    return hitter;
  }

  /**
   *  Get the payload
   * @return the payload
   */
  public Map<String, String> getPayload() {
    return payload;
  }
}
