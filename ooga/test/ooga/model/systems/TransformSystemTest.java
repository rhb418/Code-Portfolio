package ooga.model.systems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ooga.model.Vector;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransformSystemTest {

  ECManager ecManager;

  TransformSystemTest() {
    ecManager = new ECManager(null);
  }

  @BeforeEach
  void setup() {
  }

  @Test
  void testPositionUpdating() {
    GameObject go1 = ecManager.createEntity("go1");
    assertNotNull(go1);
    go1.setVelocity(new Vector(5, 10));
    GameObject go2 = ecManager.createEntity("go2");
    assertNotNull(go2);
    go2.setVelocity(new Vector(-5, 0));

    TransformSystem transformSystem = new TransformSystem(ecManager);
    transformSystem.update(0.1);

    assertEquals(0.5, go1.getX());
    assertEquals(1, go1.getY());
    assertEquals(-0.5, go2.getX());
    assertEquals(0, go2.getY());
  }

}
