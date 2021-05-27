package ooga.model.systems;

import java.util.List;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import ooga.model.components.Component;
import ooga.model.components.PlayerComponent;
import ooga.model.components.TestComponent;
import ooga.model.systems.creature.PlayerSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ComponentManagerTest {

  ECManager ecManager;
  GameObject go;

  ComponentManagerTest() {
    ecManager = new ECManager(null);
  }

  @BeforeEach
  void setup() {
    go = new GameObject(0, "test game object");
  }

  @Test
  void testCreateComponent() {
    TestComponent component = ecManager.createComponent(go, TestComponent.class);
    assertNotNull(component);
  }

  @Test
  void testSystemGetComponent() {
    PlayerSystem playerSystem = new PlayerSystem(ecManager);
    ecManager.createComponent(go, PlayerComponent.class);
    var comps = playerSystem.getComponentMapper(PlayerComponent.class).getComponents();
    assertEquals(1, comps.size());
  }

  @Test
  void testRemoveComponent() {
    ecManager.createComponent(go, PlayerComponent.class);
    ecManager.createComponent(go, PlayerComponent.class);
    ecManager.createComponent(go, PlayerComponent.class);
    Component comp = ecManager.createComponent(go, PlayerComponent.class);

    ecManager.removeComponent(PlayerComponent.class, comp.getId());
    var comps = ecManager.getComponents(PlayerComponent.class);
    assertEquals(3, comps.size());
  }

  @Test
  void testComponentRegistering() {
    Component comp1 = new PlayerComponent(9999, null);
    Component comp2 = new PlayerComponent(9999, null);
    go.addComponent(comp1);
    go.addComponent(comp2);

    ecManager.registerExistingComponents(List.of(go));
    assertNotEquals(9999, comp1.getId());
    assertNotEquals(9999, comp2.getId());
    assertEquals(go, comp1.getOwner());
    assertEquals(go, comp2.getOwner());
  }

}
