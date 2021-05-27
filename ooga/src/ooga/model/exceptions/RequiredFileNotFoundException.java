package ooga.model.exceptions;

/**
 * An exception thrown if a required file is not found
 *
 * @author Oliver Rodas
 */
public class RequiredFileNotFoundException extends ModelException {

  /**
   * Create a new exception
   * @param file the file that was not found
   */
  public RequiredFileNotFoundException(String file) {
    super(String
        .format(
            "The %s file was not found. Please make sure it is in the root directory of the game."
            , file));
  }
}
