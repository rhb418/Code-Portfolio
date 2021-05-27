package ooga.model.systems.creature;
/**
 * @author Tinglong Zhu
 */

import java.util.List;
import ooga.model.actions.NPCAction;
import ooga.model.actions.ObjectSpawner;
import ooga.model.annotations.Track;
import ooga.model.components.MovementComponent;
import ooga.model.components.MovementComponent.HorizontalMovementStatus;
import ooga.model.components.MovementSequenceComponent;
import ooga.model.components.PlayerComponent;
import ooga.model.managers.ECManager;
import ooga.model.systems.ComponentBasedSystem;
import ooga.model.systems.ComponentMapper;

/**
 * Logic for executing the NPC actions
 */
@Track({MovementSequenceComponent.class, PlayerComponent.class, MovementComponent.class})
public class NPCSystem extends ComponentBasedSystem {

  private ComponentMapper<MovementSequenceComponent> movementSequenceMapper;
  private ComponentMapper<MovementComponent> movementMapper;

  public NPCSystem(ECManager ecManager) {
    super(ecManager);
    movementSequenceMapper = getComponentMapper(MovementSequenceComponent.class);
    movementMapper= getComponentMapper(MovementComponent.class);
  }

  private List<MovementSequenceComponent> getMovementSequence() {
    return movementSequenceMapper.getComponents();
  }

  @Override
  public void update(double deltaTime) {
    for (MovementSequenceComponent ms : getMovementSequence()) {
      unitUpdate(deltaTime,movementMapper.get(ms.getOwner().getId()),ms);
    }
  }

  private void execAction(NPCAction actionCode, double deltaTime, MovementComponent m, MovementSequenceComponent ms){
    switch (actionCode.getAction()){
      case "move_left":
        moveLeft(deltaTime,m);
        break;
      case "move_right":
        moveRight(deltaTime,m);
        break;
      case "stand_still":
        standStill(m);
        break;
      case "spawn_object":
        ObjectSpawner spawner = new ObjectSpawner(actionCode.getPayload(), getECManager());
        spawner.handleSpawn(ms.getOwner().getId(), true);
      default:
        break;
    }
  }

  private void moveRight(double deltaTime, MovementComponent m){
    m.setHorizontalStatus(HorizontalMovementStatus.RUNNING);
    m.setDirection(1);
  }

  private void standStill(MovementComponent m){
    m.setHorizontalStatus(HorizontalMovementStatus.STILL);
  }

  private void moveLeft(double deltaTime, MovementComponent m){
    m.setHorizontalStatus(HorizontalMovementStatus.RUNNING);
    m.setDirection(-1);
  }

  private void unitUpdate(double deltaTime,MovementComponent m, MovementSequenceComponent ms){
    double cumTime= ms.getCumTime();
    List<NPCAction> actionSequence=ms.getActionSequence();
    List<Double> actionTime=ms.getActualActionTime();
    int actionIndex = ms.getActionIndex();
    if (cumTime < actionTime.get(actionIndex)) {
      cumTime += deltaTime;
      ms.setCumTime(cumTime);
    } else {
      ms.setCumTime(0);
      actionIndex++;
      actionIndex=actionIndex%actionSequence.size();
      ms.setActionIndex(actionIndex);
    }
    if (actionIndex >= actionSequence.size()) {
      ms.setActionIndex(0);
    }
    execAction(actionSequence.get(actionIndex), deltaTime, m, ms);
  }

}
