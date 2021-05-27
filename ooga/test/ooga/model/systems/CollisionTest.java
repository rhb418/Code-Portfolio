package ooga.model.systems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import ooga.model.actions.ActionInfo;
import ooga.model.managers.ActionManager;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CollisionTest {

  private ECManager myECManager;
  private ActionManager myActionManager;
  private CollisionSystem myCollisionSystem;
  private GameObject obj1;
  private GameObject obj2;

  @BeforeEach
  void setUp() {
    myECManager = new ECManager(null);
    myActionManager = new ActionManager();
    myCollisionSystem = new CollisionSystem(myECManager, myActionManager);

    obj1 = myECManager.createEntity("obj1");
    obj2 = myECManager.createEntity("obj2");

    obj1.setWidth(50);
    obj1.setHeight(50);

    obj2.setWidth(50);
    obj2.setHeight(50);
  }

  @Test
  void testNoCollisionX() {
    obj1.setX(0);
    obj1.setY(50);

    obj2.setX(51);
    obj2.setY(50);

    myCollisionSystem.update(.01);
    assertFalse(obj1.getCollided());
    assertFalse(obj1.getCollided());
  }

  @Test
  void testNoCollisionY() {
    obj1.setX(0);
    obj1.setY(50);

    obj2.setX(0);
    obj2.setY(101);

    myCollisionSystem.update(.01);
    assertFalse(obj1.getCollided());
    assertFalse(obj1.getCollided());
  }

  @Test
  void testCollisionLeft() {
    obj1.setX(49);
    obj1.setY(50);

    obj2.setX(0);
    obj2.setY(50);

    String direction = myCollisionSystem.detectCollisionDirection(obj1, obj2);

    assertEquals("left", direction);

  }

  @Test
  void testCollisionRight() {
    obj1.setX(49);
    obj1.setY(50);

    obj2.setX(0);
    obj2.setY(50);

    String direction = myCollisionSystem.detectCollisionDirection(obj2, obj1);

    assertEquals("right", direction);
  }

  @Test
  void testCollisionBottom() {
    obj1.setX(49);
    obj1.setY(89);

    obj2.setX(50);
    obj2.setY(50);

    String direction = myCollisionSystem.detectCollisionDirection(obj1, obj2);

    assertEquals("bottom", direction);
  }

  @Test
  void testCollisionTop() {
    obj1.setX(49);
    obj1.setY(89);

    obj2.setX(50);
    obj2.setY(50);

    String direction = myCollisionSystem.detectCollisionDirection(obj2, obj1);

    assertEquals("top", direction);
  }

  @Test
  void testCollisionRectificationBottom() {
    obj1.setX(49);
    obj1.setY(89);
    obj1.setVelocityY(-1);

    obj2.setX(50);
    obj2.setY(50);

    myCollisionSystem.update(.01);
    String dir = myCollisionSystem.detectCollisionDirection(obj1,obj2);
    myCollisionSystem.rectifyCollision(obj1,obj2,dir);
    assertEquals(100, obj1.getY());

  }

  @Test
  void testCollisionRectificationRight() {
    obj1.setX(5);
    obj1.setY(50);
    obj1.setVelocityX(1);

    obj2.setX(50);
    obj2.setY(50);

    myCollisionSystem.update(.01);
    String dir = myCollisionSystem.detectCollisionDirection(obj1,obj2);
    myCollisionSystem.rectifyCollision(obj1,obj2,dir);
    assertEquals(0, obj1.getX());

  }

}
