package ooga.model.components;

import java.util.ArrayList;
import java.util.List;
import ooga.model.objects.GameObject;
import ooga.model.systems.WinCondition;

/**
 * @author Robert Barnette This class defines the behavior of WinComponents which can be given to
 * GameObjects to set their win and loss conditions
 */
public class WinComponent extends Component {

  private List<WinCondition> winConds = new ArrayList();

  /**
   * This constructor is used by MOSHI to create a WinComponent and initialize its fields
   */
  public WinComponent() {

  }

  /**
   * @param id    is the id of the component
   * @param owner is the GameObject that contains this component This constructor creates a
   *              WinComponent object
   */
  public WinComponent(int id, GameObject owner) {
    super(id, owner);
  }


  /**
   * @returns a list of possible win conditions
   */
  public List<WinCondition> getWinConds() {
    return winConds;
  }

  /**
   * @returns null
   */
  @Override
  public String typeUnerasure() {
    return null;
  }
}
