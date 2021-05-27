package ooga.model.components.enemy;
/**
 * @author Tinglong Zhu
 */

import ooga.model.components.Component;
import ooga.model.objects.GameObject;

public class HateComponent extends Component {

  private double range;
  private boolean hate;
  private int party; // different parties will have different hate relationship
  private int frequency = 240;
  private double damage=10;

  public HateComponent(){};

  public HateComponent(int id, GameObject owner) {
    super(id, owner);
  }

  @Override
  public String typeUnerasure() {
    return HateComponent.class.getName();
  }

  /**
   * Set the detection range for the enmeies.
   * @param range   The range that the enemy will detect the player
   */
  public void setRange(double range) {
    this.range = range;
  }

  /**
   * Determine whether the enemy has detected the existence of the player/
   * @param distance  the distance between the player and the enemy
   * @return          true for hate established, false for not.
   */
  public boolean detectHate(double distance) {
    return distance < this.range;
  }

  /**
   * Get the attack frequency of the enemy
   * @return    Attack frequency of the enemy
   */
  public int getFrequency() {
    return frequency;
  }

  /**
   * Set the attack frequency for the enemy
   * @param frequency     attack frequency
   */
  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }

  /**
   * Get the damage of a single attack of the enemy.
   * @return    damage of a single attack of the enemy.
   */
  public double getDamage() {
    return damage;
  }

  /**
   * Set the damage of a single attack of the enemy.
   * @param damage
   */
  public void setDamage(double damage) {
    this.damage = damage;
  }
}
