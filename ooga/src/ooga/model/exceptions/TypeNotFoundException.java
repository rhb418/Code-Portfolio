package ooga.model.exceptions;

/**
 * If a type is not found in the object presets
 * @author Oliver Rodas
 */
public class TypeNotFoundException extends ModelException {

  /**
   * Create a new exception
   * @param type the type expected
   */
  public TypeNotFoundException(String type) {
    super(String
        .format("The type: %s was not found. Please Define it in the objects directory.%n", type));
  }
}
