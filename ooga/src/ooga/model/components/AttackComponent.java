package ooga.model.components;
/**
 * @author Tinglong Zhu
 */


import ooga.model.objects.GameObject;

/**
 * Used for storing attack data
 */
public class AttackComponent extends Component {

  protected int frequency = 0;// attack every n frames
  protected int counter = 0;

  public AttackComponent(int id, GameObject owner) {
    super(id, owner);
  }

  @Override
  public String typeUnerasure() {
    return AttackComponent.class.getName();
  }

  /**
   * Set the frequency for this attack
   * @param frequency
   */
  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }

  /**
   * determine whether the player is able to attack
   * @return
   */
  public boolean attack() {
    return counter == 0;
  }

  /**
   * Update the attack count
   */
  public void update() {
    counter ++;
    counter = counter % frequency;
  }

  /**
   * Reset the attack count to 0
   */
  public void reset() {
    counter = 0;
  }

}
