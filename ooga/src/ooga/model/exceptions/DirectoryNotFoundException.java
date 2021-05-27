package ooga.model.exceptions;

/**
 * Used to call to attention that a directory was not found
 * @author Oliver Rodas
 */
public class DirectoryNotFoundException extends ModelException {

  /**
   * Create a new exception
   * @param directory the directory attempted to be used
   */
  public DirectoryNotFoundException(String directory) {
    super(String
        .format(
            "The %s directory was not found. Please make sure it is in the root directory of the game."
            , directory));
  }
}
