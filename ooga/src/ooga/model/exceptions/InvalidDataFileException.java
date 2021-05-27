package ooga.model.exceptions;

/**
 * Used to inform the user that a file was not properly formatted
 * @author Oliver Rodas
 */
public class InvalidDataFileException extends ModelException {

  private static final String format =
      "Error reading %s. Please check to make sure the file is formatted as a proper json file.%n";

  /**
   * Create a new exception
   * @param fileName The filename
   */
  public InvalidDataFileException(String fileName) {
    super(String.format(format, fileName));
  }
}
