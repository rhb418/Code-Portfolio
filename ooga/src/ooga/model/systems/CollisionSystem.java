package ooga.model.systems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ooga.model.actions.ActionInfo;
import ooga.model.actions.CollisionInfo;
import ooga.model.managers.ActionManager;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;

/**
 * @author Robert Barnette This class handles directional collision detection between GameObjects
 * and rectifies object positions when they are collided
 */
public class CollisionSystem extends GameObjectBasedSystem {

  private ActionManager myActionManager;


  /**
   * @param ecmanager     is the ECManager used to initialize the systems for this level
   * @param actionManager is the ActionManager that handles collision actions when they occur
   */
  public CollisionSystem(ECManager ecmanager, ActionManager actionManager) {
    super(ecmanager);
    myActionManager = actionManager;
  }

  /**
   * @param deltaTime is the time that has elapsed since the last iteration of the game loop This
   *                  method is run every iteration of the GameLoop and detects collisions, their
   *                  direction, and attempts to rectify them
   */
  @Override
  public void update(double deltaTime) {
    checkCollisions();
  }

  /**
   * This method checks if GameObjects are collidable and then sorts collidable objects by their
   * x-coordinate
   */
  private void checkCollisions() {
    List<GameObject> objects = getTrackedGameObjects();
    List<GameObject> collidableObjects = new ArrayList<>();

    for (GameObject gameObject : objects) {
      if (gameObject.isCollidable()) {
        collidableObjects.add(gameObject);
      }
    }
    Collections.sort(collidableObjects);
    findCollisions(collidableObjects);
  }

  /**
   * @param collidableObjects is the list of collidable objects in the level This method loops
   *                          through the collidable objects and intelligently checks to see if they
   *                          collide with other objects.
   */
  private void findCollisions(List<GameObject> collidableObjects) {
    for (int k = 0; k < collidableObjects.size() - 1; k++) {
      executeCollisions(k, collidableObjects);
    }
  }

  /**
   * @param index             is the index of the list where the object being checked is contained
   * @param collidableObjects is the list of collidable objects in the level This method checks to
   *                          see if a given object has collided with other objects
   */
  private void executeCollisions(int index, List<GameObject> collidableObjects) {
    GameObject collidingObject = collidableObjects.get(index);
    double width = collidingObject.getWidth();
    double height = collidingObject.getHeight();
    double x = collidingObject.getX();
    double y = collidingObject.getY();

    for (int k = index + 1; k < collidableObjects.size(); k++) {
      GameObject collidedObject = collidableObjects.get(k);

      if (x + width < collidedObject.getX()) {
        return;
      } else if (collidedObject.getY() >= y && collidedObject.getY() <= y + height
          || y >= collidedObject.getY() && y <= collidedObject.getY() + collidedObject
          .getHeight()) {
        collide(collidingObject, collidedObject);
      }
    }
  }

  /**
   * @param self  is one of the collided objects
   * @param other is the other collided object This method calls the action handlers to manage the
   *              collision when a collision is detected
   */
  private void collide(GameObject self, GameObject other) {
    String selfDirection = detectCollisionDirection(self, other);
    String otherDirection = detectCollisionDirection(other, self);

    self.setCollided(true);
    other.setCollided(true);

    CollisionInfo selfInfo = new CollisionInfo(self, other, selfDirection);
    CollisionInfo otherInfo = new CollisionInfo(other, self, otherDirection);

    if (!(canCollide(self, selfInfo) || canCollide(other, otherInfo))) {
      return;
    }

    if (self.getVelocity().magnitude() >= other.getVelocity().magnitude()) {
      rectifyCollision(self, other, selfDirection);
    } else {
      rectifyCollision(other, self, otherDirection);
    }

    myActionManager.handleAction(self, other, selfInfo);
    myActionManager.handleAction(other, self, otherInfo);
  }

  private boolean canCollide(GameObject self, CollisionInfo collisionInfo) {
    for (ActionInfo action : self.getActions()) {
      if (action.equals(collisionInfo)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param self          is one of the collided objects
   * @param other         is the other collided object
   * @param selfDirection is the direction of collision relative to the self object This method
   *                      un-collides objects when they are collided to prevent objects from
   *                      occupying the same space on the screen
   */
  public void rectifyCollision(GameObject self, GameObject other, String selfDirection) {
    if (self.getVelocity().magnitude() == 0) {
      return;
    }
    switch (selfDirection) {
      case "left":
        self.setX(other.getX() + other.getWidth());
        break;
      case "right":
        self.setX(other.getX() - self.getWidth());
        break;
      case "top":
        self.setY(other.getY() - self.getHeight());
        break;
      case "bottom":
        self.setY(other.getY() + other.getHeight());
        break;
    }
  }

  /**
   * @param collidingObject is one of the collided objects
   * @param collidedObject  is the other collided object
   * @returns the direction of the collision
   */
  public String detectCollisionDirection(GameObject collidingObject, GameObject collidedObject) {
    double x = collidedObject.getX();
    double y = collidedObject.getY();
    double width = collidedObject.getWidth();
    double height = collidedObject.getHeight();

    double lComp = 0;
    double rComp = 0;
    double tComp = 0;
    double bComp = 0;

    if (collidingObject.getX() <= x) {
      rComp = Math.max(0,
          Math.min(y + height, collidingObject.getY() + collidingObject.getHeight()) - Math
              .max(y, collidingObject.getY()));
    }
    if (collidingObject.getX() + collidingObject.getWidth() >= x + width) {
      lComp = Math.max(0,
          Math.min(y + height, collidingObject.getY() + collidingObject.getHeight()) - Math
              .max(y, collidingObject.getY()));
    }
    if (collidingObject.getY() <= y) {
      tComp = Math.max(0,
          Math.min(x + width, collidingObject.getX() + collidingObject.getWidth()) - Math
              .max(x, collidingObject.getX()));
    }
    if (collidingObject.getY() + collidingObject.getHeight() >= y + height) {
      bComp = Math.max(0,
          Math.min(x + width, collidingObject.getX() + collidingObject.getWidth()) - Math
              .max(x, collidingObject.getX()));
    }
    if (lComp + rComp + tComp + bComp == 0) {
      if (collidingObject.getY() < y) {
        return "top";
      }
      if (collidingObject.getY() > y) {
        return "bottom";
      }
    }

    return calculateCollisionDirection(lComp, rComp, tComp, bComp);
  }

  /**
   * @param lComp is the length of the left component of the collision
   * @param rComp is the length of the right component of the collision
   * @param tComp is the length of the top component of the collision
   * @param bComp is the length of the bottom component of the collision
   * @returns the direction of the collision
   */
  private String calculateCollisionDirection(double lComp, double rComp, double tComp,
      double bComp) {
    if (lComp >= rComp && lComp >= tComp && lComp >= bComp) {
      return "left";
    }
    if (rComp >= lComp && rComp >= tComp && rComp >= bComp) {
      return "right";
    }
    if (tComp >= lComp && tComp >= rComp && tComp >= bComp) {
      return "top";
    }
    if (bComp >= lComp && bComp >= rComp && bComp >= tComp) {
      return "bottom";
    }
    return "";
  }

}
