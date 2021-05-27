package ooga.model.systems.creature;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.components.HealthComponent;
import ooga.model.components.MovementComponent;
import ooga.model.components.PlayerComponent;
import ooga.model.managers.ECManager;
import ooga.model.managers.SystemManager;
import ooga.model.objects.GameObject;
import ooga.model.systems.HealthSystem;
import ooga.model.systems.MovementSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerSystemTest {
  ECManager ecManager;
  GameObject go;
  PlayerSystem playerSystem;
  PlayerComponent playerComponent;
  MovementComponent movementComponent;
  MovementSystem movementSystem;
  SystemManager systemManager;

  @BeforeEach
  void setup(){
    ecManager = new ECManager(null);
    systemManager = new SystemManager();
    systemManager.createSystem(MovementSystem.class,ecManager);
    systemManager.createSystem(PlayerSystem.class,ecManager);
    movementSystem=systemManager.getSystem(MovementSystem.class);
    playerSystem=systemManager.getSystem(PlayerSystem.class);

    go = ecManager.createEntity("test entity");
    playerComponent= ecManager.createComponent(go,PlayerComponent.class);
    movementComponent=ecManager.createComponent(go, MovementComponent.class);
  }


  @Test
  void moveLeft() {
    playerSystem.moveLeft(go.getId(),true);
    assertEquals(movementComponent.getDirection(), -1);
  }

  @Test
  void moveRight() {
    playerSystem.moveRight(go.getId(),true);
    assertEquals(movementComponent.getDirection(), 1);
  }

}