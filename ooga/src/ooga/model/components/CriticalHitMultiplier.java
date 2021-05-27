package ooga.model.components;
/**
 * @author Tinglong Zhu
 */

import ooga.model.objects.GameObject;

/**
 * Used for critical hit multiplication
 */
public class CriticalHitMultiplier extends Component {

  private double multiplier = 2;
  private double chance  = 0.5;

  public CriticalHitMultiplier(int id, GameObject owner) {
    super(id, owner);
  }

  @Override
  public String typeUnerasure() {
    return CriticalHitMultiplier.class.getName();
  }

  /**
   * determine whether it is a critical hit
   * @return   1 for normal attack multiplier for critical attack
   */
  public double getMultiplier() {
    if(Math.random()<chance){
      return multiplier;
    }
    else return 1;
  }


  /**
   * Set the probability for the critical hit
   * @param chance    probability
   */
  public void setChance(double chance){
    this.chance=chance;
  }
}
