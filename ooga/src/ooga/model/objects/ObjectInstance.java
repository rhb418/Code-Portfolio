package ooga.model.objects;

import ooga.model.Vector;

/**
 * This class serves as a conversion between a game object and how it is represented in a data file
 * It is used as the input to the object factory to create clones of new objects
 *
 * @author Oliver Rodas
 */
public class ObjectInstance {

  private String name;
  private double x, y;
  private Vector velocity;

  /**
   * Create a new object instance
   * @param name The name of the type of object
   * @param x The x position of the object
   * @param y The y position of the object
   */
  public ObjectInstance(String name, double x, double y) {
    this.name = name;
    this.x = x;
    this.y = y;
  }

  /**
   * An object instance with a specified velocity.
   * @param name
   * @param x
   * @param y
   * @param velocity the velocity to begin this object with
   */
  public ObjectInstance(String name, double x, double y, Vector velocity) {
    this(name, x, y);
    this.velocity = velocity;
  }

  /**
   * Creates a new object instance out of a game object
   * @param object
   */
  public ObjectInstance(GameObject object) {
    this.name = object.getName();
    this.x = object.getX();
    this.y = object.getY();
  }

  /**
   * @return the x position
   */
  public double getX() {
    return x;
  }

  /**
   * @return the y position
   */
  public double getY() {
    return y;
  }

  /**
   * @return the name of the object
   */
  public String getName() {
    return name;
  }

  /**
   * @return the velocity of the object
   */
  public Vector getVelocity() {
    return velocity;
  }
}
