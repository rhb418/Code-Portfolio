package ooga.model.gameproperties;


import ooga.model.Vector;

public interface GameProperties {

  // Get the gravity for an object at position
  Vector getAcceleration(Vector object);
}
