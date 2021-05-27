package ooga.model.systems.creature;
/**
 * @author Tinglong Zhu
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import ooga.model.Vector;
import ooga.model.annotations.Track;
import ooga.model.components.Component;
import ooga.model.components.PlayerComponent;
import ooga.model.components.enemy.HateComponent;
import ooga.model.managers.ECManager;
import ooga.model.systems.ComponentBasedSystem;
import ooga.model.systems.ComponentMapper;

/**
 * Logic for the hate of the enemy
 */
@Track({PlayerComponent.class, HateComponent.class})
public abstract class EnemySystems extends ComponentBasedSystem {

  protected Map<Pair<HateComponent, PlayerComponent>, AttackDealer> hateMap = new HashMap<>();
  private ComponentMapper<HateComponent> hateMapper;
  private ComponentMapper<PlayerComponent> playerComponent;

  public EnemySystems(ECManager ecManager) {
    super(ecManager);
    hateMapper = getComponentMapper(HateComponent.class);
    playerComponent = getComponentMapper(PlayerComponent.class);
  }

  private List<PlayerComponent> getPlayers() {
    return playerComponent.getComponents();
  }

  //TODO: put these thing into Hate system when the system design is done
  private boolean detectHate(HateComponent hate0, Component hate) {
    Vector difference = hate0.getOwner().getLocation().difference(hate.getOwner().getLocation());
    double distance = difference.magnitude();
    return hate0.detectHate(distance);
  }

  /**
   * Now assume that the the enemy will only attack the player.
   */
  public void loopHateMap() {
    hateMap.clear();
    List<HateComponent> hateAll = hateMapper.getComponents();
    List<PlayerComponent> playerComponents = getPlayers();
    for (HateComponent hateComponent : hateAll) {
      for (int j = 0; j < playerComponents.size(); j++) {
        Pair<HateComponent, PlayerComponent> tmpPair = new Pair<>(hateComponent,
            playerComponents.get(j));
        if (!hateMap.containsKey(tmpPair)) {
          AttackDealer tmpAttackDealer = new AttackDealer();
          hateMap.put(tmpPair, tmpAttackDealer);
        }
        AttackDealer tmpDealer = hateMap.get(tmpPair);
        tmpDealer.setHate(detectHate(hateComponent, playerComponents.get(j)));
        tmpDealer.determineAttack(hateComponent.getFrequency());
      }
    }
  }

  protected static class AttackDealer {

    private boolean hate = false;
    private int counter = 0;
    private boolean attack = false;

    /**
     * determine whether the enemy is able to attack
     * @param frequency   the attack frequency of the enemy
     */
    public void determineAttack(int frequency) {
      if (hate) {
        if (counter == 0) {
          attack = true;
          update(frequency);
          return;
        }
      }
      attack = false;
      update(frequency);
    }

    private void update(int frequency) {
      counter++;
      counter %= frequency;
    }

    public void setHate(boolean hate) {
      this.hate = hate;
    }

    public boolean attackOrNot() {
      return attack;
    }
  }


}
