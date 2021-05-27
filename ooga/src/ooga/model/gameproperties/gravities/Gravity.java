package ooga.model.gameproperties.gravities;

import ooga.model.Vector;

public interface Gravity {

  Vector calculate(Vector object);
}
