package ooga.model.systems.creature;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ooga.model.components.AttackComponent;
import ooga.model.components.CriticalHitMultiplier;
import ooga.model.components.HealthComponent;
import ooga.model.components.MovementComponent;
import ooga.model.components.PlayerComponent;
import ooga.model.components.enemy.HateComponent;
import ooga.model.components.equipment.WeaponComponent;
import ooga.model.managers.ECManager;
import ooga.model.managers.SystemManager;
import ooga.model.objects.GameObject;
import ooga.model.systems.HealthSystem;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SampleEnemySystemTest {
  ECManager ecManager;
  GameObject player;
  HealthComponent healthComponentP;
  HealthComponent healthComponentE;
  AttackComponent attackComponent;
  WeaponComponent weaponComponent;


  GameObject enemy;
  HateComponent hateComponent;
  PlayerComponent playerComponent;
  MovementComponent movementComponentP;
  MovementComponent movementComponentE;
  CriticalHitMultiplier hitMultiplier;
  SystemManager systemManager;

  PlayerSystem playerSystem;
  SampleEnemySystem sampleEnemySystem;
  HealthSystem healthSystem;
  AttackSystem attackSystem;

  @BeforeEach
  void setup(){
    ecManager = new ECManager(null);

    enemy=ecManager.createEntity("enemy");
    player=ecManager.createEntity("player");

    hateComponent = ecManager.createComponent(enemy,HateComponent.class);
    playerComponent =ecManager.createComponent(player,PlayerComponent.class);
    healthComponentE=ecManager.createComponent(enemy,HealthComponent.class);
    healthComponentP=ecManager.createComponent(player,HealthComponent.class);
    movementComponentE=ecManager.createComponent(enemy,MovementComponent.class);
    movementComponentP=ecManager.createComponent(player,MovementComponent.class);
    weaponComponent = ecManager.createComponent(player,WeaponComponent.class);
    attackComponent = ecManager.createComponent(player,AttackComponent.class);
    hitMultiplier=ecManager.createComponent(player,CriticalHitMultiplier.class);
    systemManager = new SystemManager();

    enemy.setX(10);
    enemy.setY(0);
    player.setY(0);
    player.setX(5);
    hateComponent.setFrequency(10);
    hateComponent.setRange(10);
    hateComponent.setDamage(-10);
    healthComponentP.setHealth(100);
    healthComponentE.setHealth(100);
    attackComponent.setFrequency(10);
    weaponComponent.setPayLoad(-10);
    weaponComponent.setAttackRange(100);
    hitMultiplier.setChance(0);
    systemManager.createSystem(HealthSystem.class,ecManager);
    systemManager.createSystem(PlayerSystem.class,ecManager);
    systemManager.createSystem(AttackSystem.class,ecManager);
    systemManager.createSystem(SampleEnemySystem.class,ecManager);
    healthSystem= systemManager.getSystem(HealthSystem.class);
    playerSystem=systemManager.getSystem(PlayerSystem.class);
    attackSystem=systemManager.getSystem(AttackSystem.class);
    sampleEnemySystem = systemManager.getSystem(SampleEnemySystem.class);

  }

  @Test
  void update() {
    attackSystem.attack(true);
    attackSystem.attack(true);
    assertEquals(90, healthComponentE.getHealth());
    hitMultiplier.setChance(1);
    for (int i = 0; i < 9; i++)
      attackSystem.attack(true);

    assertEquals(70, healthComponentE.getHealth());
    sampleEnemySystem.update(0.1);
    sampleEnemySystem.update(0.1);
    assertEquals(80, healthComponentP.getHealth());
    for (int i = 0; i < 9; i++) {
      sampleEnemySystem.update(0.1);
    }
  }
}