package ooga.model.systems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntityManagerTest {

  ECManager ecManager;
  GameObject go;

  EntityManagerTest() {
    ecManager = new ECManager(null);
  }

  @BeforeEach
  void setup() {
  }

  @Test
  void testCreateEntity() {
    go = ecManager.createEntity("Test game object");
    assertNotNull(go);
  }

  @Test
  void testGetAllEntities() {
    int numGOs = 10;
    for (int i = 0; i < numGOs; ++i) {
      go = ecManager.createEntity(Integer.toString(i));
      assertNotNull(go);
    }

    List<GameObject> objects = ecManager.getEntities();
    assertEquals(numGOs, objects.size());
  }

}
