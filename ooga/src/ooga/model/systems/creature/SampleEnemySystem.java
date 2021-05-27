package ooga.model.systems.creature;
/**
 * @author Tinglong Zhu
 */

import javafx.util.Pair;
import ooga.model.annotations.Track;
import ooga.model.components.HealthComponent;
import ooga.model.components.PlayerComponent;
import ooga.model.components.enemy.HateComponent;
import ooga.model.managers.ECManager;
import ooga.model.systems.ComponentMapper;

/**
 * Logic for executing the enemy attacks
 */
@Track({HateComponent.class, HealthComponent.class, PlayerComponent.class})
public class SampleEnemySystem extends EnemySystems {

  private ComponentMapper<HealthComponent> healthMapper;


  public SampleEnemySystem(ECManager ecManager) {
    super(ecManager);
    healthMapper = getComponentMapper(HealthComponent.class);
  }

  @Override
  public void update(double deltaTime) {
    loopHateMap();
    for (Pair<HateComponent, PlayerComponent> p : hateMap.keySet()) {
      AttackDealer tmpDealer = hateMap.get(p);
      if (tmpDealer.attackOrNot()) {
        HealthComponent health = healthMapper.get(p.getValue().getOwner().getId());
        health.healthIncrement(p.getKey().getDamage(), false);
      }
    }
  }
}
