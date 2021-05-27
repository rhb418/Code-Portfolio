package ooga.model.exceptions;

/**
 * An abstract exception class that can be caught by the view  or whatever is running the game
 *
 * It is meant to be extended with whatever is needed to be made
 * @author Oliver Rodas
 */
public abstract class ModelException extends RuntimeException {

  /**
   * Create a new model exception
   * @param message the message to return
   */
  public ModelException(String message) {
    super(message);
  }
}
