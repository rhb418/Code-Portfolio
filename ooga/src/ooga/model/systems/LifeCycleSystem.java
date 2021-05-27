package ooga.model.systems;

import java.util.Map;
import ooga.model.Vector;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import ooga.model.objects.ObjectInstance;

/**
 * This system is intended to provide actions that can be used to spawn or destroy objects. Two
 * collision actions are provided:
 *
 * <ol>
 *   <li>
 *     'spawn_object': spawn an object at current position. MUST specify payload with the format
 *     'name,vx,vy'
 *   </li>
 *   <li>'destroy_object': will destroy itself</li>
 * </ol>
 */
public class LifeCycleSystem extends GameObjectBasedSystem {

  private static final String SPAWN_OBJECT_ACTION_NAME = "spawn_object";
  private static final String DESTROY_OBJECT_ACTION_NAME = "destroy_object";
  private static final String WHICH_PAYLOAD_KEY = "which";
  private static final String OFFSET_X_PAYLOAD_KEY = "offsetX";
  private static final String OFFSET_Y_PAYLOAD_KEY = "offsetY";
  private static final String VELOCITY_X_PAYLOAD_KEY = "velocityX";
  private static final String VELOCITY_Y_PAYLOAD_KEY = "velocityY";

  public LifeCycleSystem(ECManager entityManager) {
    super(entityManager);
    addCollisionMapping(
        SPAWN_OBJECT_ACTION_NAME,
        event -> spawnObject(event.getSelf(), event.getPayload())
    );

    addCollisionMapping(
        DESTROY_OBJECT_ACTION_NAME,
        event -> destroyObject(event.getSelf().getId())
    );
  }

  private void spawnObject(GameObject self, Map<String, String> payload) {
    String which = payload.get(WHICH_PAYLOAD_KEY);
    double x = self.getX() + Double.parseDouble(payload.getOrDefault(OFFSET_X_PAYLOAD_KEY, "0"));
    double y = self.getY() + Double.parseDouble(payload.getOrDefault(OFFSET_Y_PAYLOAD_KEY, "0"));
    double vx = Double.parseDouble(payload.getOrDefault(VELOCITY_X_PAYLOAD_KEY, "0"));
    double vy = Double.parseDouble(payload.getOrDefault(VELOCITY_Y_PAYLOAD_KEY, "0"));
    Vector v = new Vector(vx, vy);
    System.out.println("Creating object " + which + "at" + x + ", " + y + " with velocity " + v);
    ecManager.addEntity(new ObjectInstance(which, x, y, v));
  }

  private void destroyObject(int entityId) {
    // System.out.println("Deleting object " + entityId);
    ecManager.deleteGameObject(entityId);
  }

  @Override
  public void update(double deltaTime) {
  }
}
