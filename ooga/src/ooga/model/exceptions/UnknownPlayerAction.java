package ooga.model.exceptions;

/**
 * This exception is used when an unknown player action is registered
 * @author Oliver Rodas
 */
public class UnknownPlayerAction extends RuntimeException {

  /**
   * Create a new exception
   * @param action The action that was attempted to be used
   */
  public UnknownPlayerAction(String action) {
    super(String
        .format("Unknown Player Action: %s. Actions are capitalized in the config file.", action));
  }
}
