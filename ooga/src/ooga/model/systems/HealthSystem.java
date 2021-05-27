package ooga.model.systems;
/**
 * @author Tinglong Zhu
 */

import java.util.ArrayList;
import java.util.List;
import ooga.model.StatsInfo;
import ooga.model.actions.CollisionAction;
import ooga.model.annotations.Track;
import ooga.model.components.HealthComponent;
import ooga.model.managers.ECManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Managing the Health System and the destroy detection.
 */
@Track(HealthComponent.class)
public class HealthSystem extends ComponentBasedSystem {

  private static final Logger logger = LogManager.getLogger(HealthSystem.class);

  private static final String HEALTH_STATS_NAME = "health";
  private static final String CHANGE_HEALTH_ACTION_NAME = "change_health";
  private static final String AMOUNT_PAYLOAD_KEY = "amount";

  protected ComponentMapper<HealthComponent> componentMapper;

  public HealthSystem(ECManager ecManager) {
    super(ecManager);
    componentMapper = getComponentMapper(HealthComponent.class);
    addStatsSupplier(HEALTH_STATS_NAME, this::healthStatsSupplier);

    addCollisionMapping(CHANGE_HEALTH_ACTION_NAME, this::changeHealth);
  }

  private List<StatsInfo> healthStatsSupplier() {
    ArrayList<StatsInfo> ret = new ArrayList<>();
    for (HealthComponent healthComponent : componentMapper.getComponents()) {
      ret.add(new StatsInfo(healthComponent.getHealth() + "", healthComponent.getOwner().getId()));
    }
    return ret;
  }

  private void changeHealth(CollisionAction collisionAction) {
    double delta = Double.parseDouble(collisionAction.getPayload().get(AMOUNT_PAYLOAD_KEY));
    changeHealth(collisionAction.getSelf().getId(), delta, delta >= 0);
  }

  /**
   * Change the health of a game object
   *
   * @param entityId ID of the entity whose health is changed
   * @param delta    Health to add, can be negative
   */
  public void changeHealth(int entityId, double delta, boolean increase) {
    HealthComponent comp = componentMapper.get(entityId);
    if (comp == null) {
      logger.warn("Cannot find a health component of entity with id: {}", entityId);
      return;
    }
    comp.healthIncrement(delta, increase);

    logger.info(
        "Health of {} is changed by {}, its remaining health is {}", entityId, delta,
        comp.getHealth()
    );
    triggerStatsUpdate(HEALTH_STATS_NAME);
  }

  protected void destroyObject(int entityId) {
    getECManager().deleteGameObject(entityId);

    triggerStatsUpdate(HEALTH_STATS_NAME);
  }

  public void destroyDetection() {
    for (HealthComponent healthComponent : componentMapper.getComponents()) {
      if (healthComponent.getHealth() <= 0) {
        destroyObject(healthComponent.getOwner().getId());
      }
    }
  }

  @Override
  public void update(double deltaTime) {
    destroyDetection();
  }
}
