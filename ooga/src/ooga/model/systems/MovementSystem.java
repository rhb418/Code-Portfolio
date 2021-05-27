package ooga.model.systems;

import java.util.List;
import ooga.model.annotations.Track;
import ooga.model.components.MovementComponent;
import ooga.model.components.MovementComponent.HorizontalMovementStatus;
import ooga.model.components.MovementComponent.VerticalMovementStatus;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;

/**
 * This system handles movements (either active or passive). It can be used to move horizontally or
 * vertically, its collision actions can also be used to automatically stop at obstacles.
 */
@Track(MovementComponent.class)
public class MovementSystem extends ComponentBasedSystem {

  private static final String BLOCKED_BOTTOM_ACTION_NAME = "blocked_bottom";
  private static final String BLOCKED_RIGHT_ACTION_NAME = "blocked_right";
  private static final String BLOCKED_LEFT_ACTION_NAME = "blocked_left";
  private static final String BLOCKED_TOP_ACTION_NAME = "blocked_top";

  private ComponentMapper<MovementComponent> movementMapper;

  public MovementSystem(ECManager ecManager) {
    super(ecManager);
    movementMapper = getComponentMapper(MovementComponent.class);

    addCollisionMapping(
        BLOCKED_BOTTOM_ACTION_NAME,
        event -> obstacleOnBottom(event.getSelf(), event.getHitter())
    );
    addCollisionMapping(
        BLOCKED_RIGHT_ACTION_NAME,
        event -> obstacleOnRight(event.getSelf(), event.getHitter())
    );
    addCollisionMapping(
        BLOCKED_LEFT_ACTION_NAME,
        event -> obstacleOnLeft(event.getSelf(), event.getHitter())
    );
    addCollisionMapping(
        BLOCKED_TOP_ACTION_NAME,
        event -> obstacleOnTop(event.getSelf(), event.getHitter())
    );
  }

  private void obstacleOnBottom(GameObject go, GameObject other) {
    MovementComponent p = movementMapper.get(go.getId());
    if (p != null) {
      p.setVerticalStatus(VerticalMovementStatus.GROUNDED);
      p.setObstacle(MovementComponent.OBSTACLE_KEY_BOTTOM, other);
    }
  }

  private void obstacleOnTop(GameObject go, GameObject other) {
    MovementComponent p = movementMapper.get(go.getId());
    p.setVerticalStatus(VerticalMovementStatus.FALLING);
    p.setObstacle(MovementComponent.OBSTACLE_KEY_TOP, other);
  }

  private void obstacleOnLeft(GameObject go, GameObject other) {
    MovementComponent p = movementMapper.get(go.getId());
    p.setObstacle(MovementComponent.OBSTACLE_KEY_LEFT, other);
  }

  private void obstacleOnRight(GameObject go, GameObject other) {
    MovementComponent p = movementMapper.get(go.getId());
    p.setObstacle(MovementComponent.OBSTACLE_KEY_RIGHT, other);
  }

  /**
   * Handles move left key presses
   *
   * @param entityId The id of the entity
   * @param on       Whether the move left key is pressed or released
   */
  public void moveLeft(int entityId, boolean on) {
    handleHorizontalMovement(entityId, on, MovementComponent.LEFT_DIRECTION);
  }

  /**
   * Handles move right key presses
   *
   * @param entityId The id of the entity
   * @param on       Whether the move right key is pressed or released
   */
  public void moveRight(int entityId, boolean on) {
    handleHorizontalMovement(entityId, on, MovementComponent.RIGHT_DIRECTION);
  }

  private void handleHorizontalMovement(int entityId, boolean run, int direction) {
    MovementComponent p = movementMapper.get(entityId);
    if (p == null) {
      return;
    }
    p.setDirection(direction);
    if (run) {
      p.setHorizontalStatus(HorizontalMovementStatus.RUNNING);
    } else {
      p.setHorizontalStatus(HorizontalMovementStatus.STILL);
    }
  }

  public void jump(int entityId, boolean on) {
    MovementComponent p = movementMapper.get(entityId);
    if (on) {
      GameObject go = p.getOwner();
      if (p.getVerticalStatus() == VerticalMovementStatus.GROUNDED) {
        go.setVelocityY(p.getJumpHeight() / p.getJumpTime());
        go.setY(go.getY() + 3.0);
        p.setVerticalStatus(VerticalMovementStatus.RISING);
        p.resetJumpTimer();
        p.setObstacle(MovementComponent.OBSTACLE_KEY_BOTTOM, null);
      }
    } else {
      p.setVerticalStatus(VerticalMovementStatus.FALLING);
    }
  }

  /**
   * Update the object's horizontal and vertical velocity according to the movement status
   */
  @Override
  public void update(double deltaTime) {
    List<MovementComponent> comps = movementMapper.getComponents();
    for (MovementComponent p : comps) {
      GameObject go = p.getOwner();

      // set horizontal velocity of player if it's moving
      go.setVelocityX(
          p.getHorizontalStatus() == HorizontalMovementStatus.RUNNING
              ? p.getDirection() * p.getMaxSpeed()
              : 0);
      if (p.getObstacle(MovementComponent.OBSTACLE_KEY_LEFT) != null
          && go.getVelocity().getX() < 0) {
        go.setVelocityX(0);
      }
      if (p.getObstacle(MovementComponent.OBSTACLE_KEY_RIGHT) != null
          && go.getVelocity().getX() > 0) {
        go.setVelocityX(0);
      }

      // update vertical movement status
      if (p.getObstacle(MovementComponent.OBSTACLE_KEY_BOTTOM) == null) {
        if (p.getVerticalStatus() != VerticalMovementStatus.RISING) {
          p.setVerticalStatus(VerticalMovementStatus.FALLING);
        }
      } else {
        p.setVerticalStatus(VerticalMovementStatus.GROUNDED);
      }

      // update vertical velocity according to vertical movement status
      if (p.getVerticalStatus() != VerticalMovementStatus.GROUNDED) {
        go.setVelocityY(go.getVelocity().getY() - p.getGravAccel());
      } else if (p.getVerticalStatus() == VerticalMovementStatus.GROUNDED) {
        go.setVelocityY(0);
      }

      // update the vertical velocity
      p.incrementJumpTimer(deltaTime);

      // reset list of obstacles
      p.setObstacle(MovementComponent.OBSTACLE_KEY_LEFT, null);
      p.setObstacle(MovementComponent.OBSTACLE_KEY_RIGHT, null);
      p.setObstacle(MovementComponent.OBSTACLE_KEY_TOP, null);
      p.setObstacle(MovementComponent.OBSTACLE_KEY_BOTTOM, null);
    }
  }
}
