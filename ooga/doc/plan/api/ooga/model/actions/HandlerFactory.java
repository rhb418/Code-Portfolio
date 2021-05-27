package ooga.model.actions;

import ooga.model.GameObject;
import ooga.model.Model;

public interface HandlerFactory {
  ActionHandler createActionHandler(String name, GameObject object, Model model, String payload);
  CollisionHandler createCollisionHandler(String name, GameObject object, Model model, String payload);
}
