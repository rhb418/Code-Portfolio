package ooga.model.actions;

import java.util.List;
import java.util.Set;


/**
 * Action Info is the main object used in collisions. It encompasses actions to create an object
 * that collisions can use.
 *
 * @author Oliver Rodas
 */
public class ActionInfo {

  private static final String ANY_ITEM = "any";
  private static final String ANY_SIDE = "any";
  private Set<String> with = Set.of(ANY_ITEM);
  private Set<String> positions = Set.of(ANY_SIDE);
  private List<Action> actions;

  /**
   * Create an action info with the default parameters
   */
  public ActionInfo() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CollisionInfo)) {
      return false;
    }
    CollisionInfo info = (CollisionInfo) o;
    boolean ret = with.contains(ANY_ITEM);
    for (String item : with) {
      ret = ret || info.other().isA(item);
    }

    return ret && (positions.contains(ANY_SIDE) || positions.contains(info.position()));
  }

  /**
   * Get the actions associated with this collision
   * @return the list of actions to be done if this collision is triggered
   */
  public List<Action> getActions() {
    return this.actions;
  }

}
