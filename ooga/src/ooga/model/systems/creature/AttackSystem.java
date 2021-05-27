package ooga.model.systems.creature;
/**
 * @author Tinglong Zhu
 */

import java.util.Map;
import java.util.function.BiConsumer;
import ooga.model.Vector;
import ooga.model.annotations.Track;
import ooga.model.components.AttackComponent;
import ooga.model.components.CriticalHitMultiplier;
import ooga.model.components.HealthComponent;
import ooga.model.components.MovementComponent;
import ooga.model.components.enemy.HateComponent;
import ooga.model.components.equipment.WeaponComponent;
import ooga.model.managers.ECManager;
import ooga.model.systems.ComponentBasedSystem;
import ooga.model.systems.ComponentMapper;
import ooga.model.systems.HealthSystem;

/**
 * Logic for attack
 */
@Track({WeaponComponent.class, MovementComponent.class, HateComponent.class, HealthComponent.class,  AttackComponent.class,CriticalHitMultiplier.class})
public class AttackSystem extends ComponentBasedSystem {

  private static final String ATTACK_ACTION_NAME = "attack";

  private ComponentMapper<WeaponComponent> weaponMapper;
  private ComponentMapper<MovementComponent> movementMapper;
  private ComponentMapper<HateComponent> enemyMapper;
  private ComponentMapper<AttackComponent> attackMapper;
  private ComponentMapper<CriticalHitMultiplier> criticalHitMapper;


  public AttackSystem(ECManager ecManager) {
    super(ecManager);
    weaponMapper = getComponentMapper(WeaponComponent.class);
    movementMapper = getComponentMapper(MovementComponent.class);
    enemyMapper = getComponentMapper(HateComponent.class);
    attackMapper=getComponentMapper(AttackComponent.class);
    criticalHitMapper=getComponentMapper(CriticalHitMultiplier.class);

    addMapping(ATTACK_ACTION_NAME, this::attack);
  }

  /**
   * Executing the attack
   * @param on  callbacks needed var
   */
  public void attack(boolean on) {
    for (WeaponComponent w : weaponMapper.getComponents()) {
      AttackComponent attacker=attackMapper.get(w.getOwner().getId());
      CriticalHitMultiplier multiplier=criticalHitMapper.get(w.getOwner().getId());
      for (HateComponent h : enemyMapper.getComponents()) {
        int enemyID = h.getOwner().getId();
        if (withinRange(w, h) && faceToEnemy(w, h)&& attacker.attack()) {
          HealthSystem healthSystem = getSystem(HealthSystem.class);
          healthSystem.changeHealth(enemyID, w.getAttack()*multiplier.getMultiplier(), false);
        }
        attacker.update();
      }
    }
  }

  private boolean withinRange(WeaponComponent w, HateComponent h) {
    return (Vector.difference(w.getOwner().getLocation(), h.getOwner().getLocation()) < w
        .getAttackRange());
  }

  private boolean faceToEnemy(WeaponComponent w, HateComponent h) {
    Vector delta = h.getOwner().getLocation().difference(w.getOwner().getLocation());
    double deltaX = delta.getX();
    return deltaX * movementMapper.get(w.getOwner().getId()).getDirection() >= 0;
  }

  @Override
  public void update(double deltaTime) {
    HealthSystem healthSystem= getSystem(HealthSystem.class);
    healthSystem.update(deltaTime);
  }
}
