package ooga.model.systems;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.font.GlyphMetrics;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import ooga.model.components.MovementComponent;
import ooga.model.components.MovementComponent.VerticalMovementStatus;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovementSystemTest {
  ECManager ecManager;
  GameObject go0;
  GameObject go1;
  MovementSystem movementSystem;
  MovementComponent movementComponent0;
  MovementComponent movementComponent1;
  Method obOnTop;
  Method obOnBot;
  Method obOnLef;
  Method obOnRig;



  @BeforeEach
  void setup() throws NoSuchMethodException {
    ecManager= new ECManager(null);
    movementSystem = new MovementSystem(ecManager);

    go0=ecManager.createEntity("test ent1");
    go1=ecManager.createEntity("test ent2");
    movementComponent0=ecManager.createComponent(go0,MovementComponent.class);
    movementComponent1=ecManager.createComponent(go1,MovementComponent.class);
    obOnTop=movementSystem.getClass().getDeclaredMethod("obstacleOnTop", GameObject.class, GameObject.class);
    obOnBot=movementSystem.getClass().getDeclaredMethod("obstacleOnBottom", GameObject.class, GameObject.class);
    obOnLef=movementSystem.getClass().getDeclaredMethod("obstacleOnLeft", GameObject.class, GameObject.class);
    obOnRig=movementSystem.getClass().getDeclaredMethod("obstacleOnRight", GameObject.class, GameObject.class);
    obOnBot.setAccessible(true);
    obOnLef.setAccessible(true);
    obOnTop.setAccessible(true);
    obOnRig.setAccessible(true);
  }

  @Test
  void obstacleOnRight() throws InvocationTargetException, IllegalAccessException {
    obOnRig.invoke(movementSystem,go0,go1);
    assertEquals(go1.getId(),movementComponent0.getObstacle(MovementComponent.OBSTACLE_KEY_RIGHT).getId());
  }

  @Test
  void obstacleOnLeft() throws InvocationTargetException, IllegalAccessException {
    obOnLef.invoke(movementSystem,go0,go1);
    assertEquals(go1.getId(),movementComponent0.getObstacle(MovementComponent.OBSTACLE_KEY_LEFT).getId());
  }

  @Test
  void obstacleOnBottom() throws InvocationTargetException, IllegalAccessException {
    obOnBot.invoke(movementSystem,go0,go1);
    assertEquals(go1.getId(),movementComponent0.getObstacle(MovementComponent.OBSTACLE_KEY_BOTTOM).getId());
    assertEquals(movementComponent0.getVerticalStatus(), VerticalMovementStatus.GROUNDED);
  }

  @Test
  void obstacleOnTop() throws InvocationTargetException, IllegalAccessException {
    obOnTop.invoke(movementSystem,go0,go1);
    assertEquals(go1.getId(),movementComponent0.getObstacle(MovementComponent.OBSTACLE_KEY_TOP).getId());
    assertEquals(movementComponent0.getVerticalStatus(), VerticalMovementStatus.FALLING);
  }

}