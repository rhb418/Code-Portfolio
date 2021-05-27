package ooga.model.components;
/**
 * @author Tinglong Zhu
 */

import ooga.model.objects.GameObject;

public class HealthComponent extends Component {

  protected double health = 0;

  public HealthComponent(int id, GameObject owner) {
    super(id, owner);
  }

  public void healthIncrement(double delta, boolean increase) {
    if (increase) {
      if (delta >= 0) {
        health += delta;
      }
      return;
    }
    if (delta < 0) {
      health += delta;
    }
  }

  public double getHealth() {
    return health;
  }

  public void setHealth(double health) {
    this.health = health;
  }

  public String typeUnerasure() {
    return HealthComponent.class.getName();
  }
}
