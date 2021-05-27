package ooga.model.actions;

import ooga.model.GameObject;

public interface CollisionHandler {
  void handleCollision(GameObject obj);
}