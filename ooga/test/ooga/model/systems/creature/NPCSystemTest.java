package ooga.model.systems.creature;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;
import ooga.model.Vector;
import ooga.model.actions.NPCAction;
import ooga.model.components.MovementComponent;
import ooga.model.components.MovementSequenceComponent;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import ooga.model.systems.MovementSystem;
import ooga.model.systems.TransformSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NPCSystemTest {
  ECManager ecManager;
  MovementComponent movementComponent;
  MovementSequenceComponent movementSquenceComponent;

  MovementSystem movementSystem;
  NPCSystem npcSystem;
  TransformSystem transformSystem;
  GameObject go;
  NPCAction a1;
  NPCAction a2;

  @BeforeEach
  void setup() throws NoSuchFieldException, IllegalAccessException {
    ecManager =  new ECManager(null);
    go = ecManager.createEntity("test");
    movementComponent= ecManager.createComponent(go,MovementComponent.class);
    movementSquenceComponent=ecManager.createComponent(go, MovementSequenceComponent.class);
    npcSystem = new NPCSystem(ecManager);
    movementSystem= new MovementSystem(ecManager);
    transformSystem=new TransformSystem(ecManager);
    List<NPCAction> moves=movementSquenceComponent.getActionSequence();
    List<Double> times=movementSquenceComponent.getActionTime();
    a1=new NPCAction("a1");
    a2=new NPCAction("a1");
    moves.add(a1);
    moves.add(a2);
    Field npcActionName=NPCAction.class.getDeclaredField("action");
    npcActionName.setAccessible(true);
    npcActionName.set(a1,"move_right");
    npcActionName.set(a2,"move_left");
    times.add(1.0);
    times.add(1.0);

    go.setX(0);
    go.setY(0);
    go.setVelocity(new Vector(1.0,0));
  }

  @Test
  void update() {
    updates(0.1);
    assertEquals(go.getX(),10);
    updates(0.9);
    assertEquals(go.getX(),100);
    updates(0.1);
    assertEquals(go.getX(),90);
    updates(0.9);
    assertEquals(go.getX(),0);
  }

  private void updates(double deltaTime) {
    npcSystem.update(deltaTime);
    movementSystem.update(deltaTime);
    transformSystem.update(deltaTime);
  }
}