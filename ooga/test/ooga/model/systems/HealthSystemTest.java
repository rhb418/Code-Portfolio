package ooga.model.systems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ooga.model.components.HealthComponent;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HealthSystemTest {

  ECManager ecManager;
  GameObject go;
  HealthSystem healthSystem;
  HealthComponent healthComponent;

  @BeforeEach
  void setup() {

    ecManager = new ECManager(null);
    healthSystem = new HealthSystem(ecManager);

    go = ecManager.createEntity("test entity");
    healthComponent = ecManager.createComponent(go, HealthComponent.class);
    healthSystem = new HealthSystem(ecManager);
  }

  @Test
  void testChangeHealth() {
    healthComponent.setHealth(100);
    healthSystem.changeHealth(go.getId(), -50, false);
    assertEquals(50, healthComponent.getHealth());
  }

  @Test
  void testDeathHealth() {
    healthComponent.setHealth(100);
    healthSystem.changeHealth(go.getId(), -100, false);
    healthSystem.update(0.1);
    assertEquals(0, healthComponent.getHealth());
    assertEquals(0, ecManager.getEntities().size());
  }
}
