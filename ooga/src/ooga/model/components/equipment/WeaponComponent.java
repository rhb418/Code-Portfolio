package ooga.model.components.equipment;
/**
 * @author Tinglong Zhu
 */

import ooga.model.objects.GameObject;

/**
 * Component who stores the weapon's data
 */
public class WeaponComponent extends EquipmentComponent {

  private double attackRange = 1;


  /**
   * moshi constructor
   * @param id     Component id
   * @param owner  the owner of the component (game object)
   */
  public WeaponComponent(int id, GameObject owner) {
    super(id, owner);
  }

  /**
   * Get the attack range of the weapon
   * @return  the attack range of the weapon
   */
  public double getAttackRange() {
    return attackRange;
  }

  /**
   * Set the attack range of the weapon
   */
  public void setAttackRange(double attackRange) {
    this.attackRange = attackRange;

  }

  /**
   * Get the attack damage of the weapon
   * @return    attack damage of the weapon
   */
  public double getAttack() {
    return getPayLoad();
  }

}
