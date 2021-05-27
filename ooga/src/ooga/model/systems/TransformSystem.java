package ooga.model.systems;

import java.util.ArrayList;
import java.util.List;
import ooga.model.StatsInfo;
import ooga.model.Vector;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;

/**
 * Update GameObject's position and rotation according to its velocity and angular velocity
 */
public class TransformSystem extends GameObjectBasedSystem {

  private static final String POSITION_STATS_NAME = "position";

  public TransformSystem(ECManager ecManager) {
    super(ecManager);
    addStatsSupplier(POSITION_STATS_NAME, this::positionStatsSupplier);
  }

  private List<StatsInfo> positionStatsSupplier() {
    ArrayList<StatsInfo> ret = new ArrayList<>();
    List<GameObject> gameObjects = getTrackedGameObjects();
    for (GameObject go : gameObjects) {
      double x = go.getX();
      double y = go.getY();
      ret.add(new StatsInfo("(" + x + ", " + y + ")", go.getId()));
    }
    return ret;
  }

  @Override
  public void update(double deltaTime) {
    List<GameObject> gameObjects = getTrackedGameObjects();

    for (GameObject go : gameObjects) {
      double x = go.getX();
      double y = go.getY();
      Vector v = go.getVelocity();

      go.setX(x + deltaTime * v.getX());
      go.setY(y + deltaTime * v.getY());
    }

    triggerStatsUpdate(POSITION_STATS_NAME);
  }
}
