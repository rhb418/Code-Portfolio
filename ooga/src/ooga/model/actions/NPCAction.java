package ooga.model.actions;

/**
 * Describes the action for an NPC to do.
 * @author Oliver Rodas
 */
public class NPCAction {
  private String action;
  private String payload;

  /**
   * Create a new NPC Action
   * @param payload the payload of the action
   */
  public NPCAction(String payload) {
    this.payload = payload;
  }

  /**
   * The action name to be done
   * @return The action name
   */
  public String getAction() {
    return action;
  }

  /**
   * Get the payload of the action
   * @return the action payload
   */
  public String getPayload() {
    return payload;
  }
}
