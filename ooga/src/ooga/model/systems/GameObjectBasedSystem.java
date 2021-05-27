package ooga.model.systems;

import java.util.List;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;

/**
 * The baseclass of all systems that tracked data in game objects
 */
public abstract class GameObjectBasedSystem extends BaseSystem {

  ECManager ecManager;

  public GameObjectBasedSystem(ECManager entityManager) {
    this.ecManager = entityManager;
  }

  protected List<GameObject> getTrackedGameObjects() {
    return ecManager.getEntities();
  }
}
