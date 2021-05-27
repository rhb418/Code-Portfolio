package ooga.model.exceptions;

/**
 *
 * This exception catches an error if a directory was not passed to something that needs one
 *
 * @author Oliver Rodas
 */
public class NotADirectoryException extends ModelException {

  /**
   * Creates a new exception
   * @param dirName the file attempted to be used
   */
  public NotADirectoryException(String dirName) {
    super(String.format("Error: %s must be a directory.", dirName));
  }
}
