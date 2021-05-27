package ooga.model.gameproperties.gravities;

import ooga.model.Vector;

public class CircularGravity implements Gravity {

  private double amount;
  private Vector origin;

  public CircularGravity(double amount, Vector origin) {
    this.amount = amount;
    this.origin = origin;
  }

  @Override
  public Vector calculate(Vector object) {
    Vector diff = object.difference(origin);
    return new Vector(amount * diff.cos(), amount * diff.sin());
  }
}
