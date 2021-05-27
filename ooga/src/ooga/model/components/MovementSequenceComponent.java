package ooga.model.components;
/**
 * @author Tinglong Zhu
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ooga.model.actions.NPCAction;
import ooga.model.objects.GameObject;

/**
 * Component for storing the NPC movements/actions
 */
public class MovementSequenceComponent extends MovementComponent {
  private transient Random RNJesus = new Random();
  List<NPCAction> actionSequence;
  List<Double> actualActionTime;
  List<Double> actionTime;
  private double cumTime = 0;
  private int actionIndex = 0;

  public MovementSequenceComponent(){};

  public MovementSequenceComponent(int id, GameObject owner) {
    super(id, owner);
    actionSequence = new ArrayList<>();
    actionTime = new ArrayList<>();
    actualActionTime=new ArrayList<>();
  }

  /**
   * Get the current action index
   * @return  The index representing the current movement/action
   */
  public int getActionIndex() {
    return actionIndex;
  }

  /**
   * Get the time already been used to execute this action
   * @return  time used for executing the action
   */
  public double getCumTime() {
    return cumTime;
  }

  /**
   * get the action sequence stored in the config file
   * @return action sequence
   */
  public List<NPCAction> getActionSequence() {
    return actionSequence;
  }

  /**
   * Turn -1 to random number
   * @return  actual action time sequence that will be used by the system
   */
  public List<Double> getActualActionTime() {
    if(actionIndex==0&&cumTime==0){
      if(actualActionTime==null){
        actualActionTime=new ArrayList<>();
      }
      else{
        actualActionTime.clear();
      }
      for(double t: actionTime){
        if(t==-1){
          actualActionTime.add(Math.random());
        }
        else{
          actualActionTime.add(t);
        }
      }
    }
    return actualActionTime;
  }

  /**
   * Get the time set for the certain action
   * @return    time needed for executing the certain action
   */
  public List<Double> getActionTime() {
    return actionTime;
  }

  /**
   * Set the time being used for the certain action
   * @param cumTime   time already used for the certain action
   */
  public void setCumTime(double cumTime) {
    this.cumTime = cumTime;
  }

  /**
   * Set the index representing the action
   * @param actionIndex   the index representing the action
   */
  public void setActionIndex(int actionIndex) {
    this.actionIndex = actionIndex;
  }
}
