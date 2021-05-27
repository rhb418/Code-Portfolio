package ooga.model.components.equipment;
/**
 * @author Tinglong Zhu
 */

import ooga.model.components.Component;
import ooga.model.components.HealthComponent;
import ooga.model.objects.GameObject;

/**
 * The weapon/armor needs AP(attack power)
 */
public class EquipmentComponent extends Component {

  private double payLoad = 0;

  public EquipmentComponent(int id, GameObject owner) {
    super(id, owner);
  }

  @Override
  public String typeUnerasure() {
    return EquipmentComponent.class.getName();
  }

  /**
   * Get the payload (damage for weapon, defense for armor) of the equipment.
   * @return
   */
  public double getPayLoad() {
    return payLoad;
  }

  /**
   * Set the payload (damage for weapon, defense for armor) of the equipment.
   */
  public void setPayLoad(double payLoad) {
    this.payLoad = payLoad;
  }

}
