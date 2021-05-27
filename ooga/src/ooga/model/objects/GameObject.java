package ooga.model.objects;

import java.util.ArrayList;
import java.util.List;
import ooga.model.Vector;
import ooga.model.actions.ActionInfo;
import ooga.model.components.Component;
import ooga.model.observables.ObservableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class serves as an actor in our game
 * The class implements the observable interface so that it can be displayed
 *
 * This class is meant to be a container for data. Systems that hold the logic for actions
 * can operate on the objects. This made objects modular as any data can be swapped for anything
 *
 * @author Oliver Rodas
 */
public class GameObject implements ObservableObject, Comparable<GameObject> {

  private static final Logger logger = LogManager.getLogger(GameObject.class);
  private List<Component> components = new ArrayList<>();
  private int id;
  private String name;
  private double x, y;
  private String imageID;
  private boolean isCollided;
  private List<String> isA = new ArrayList<>();
  private Vector velocity = new Vector(0,0);
  private boolean collidable = true;
  private double height, width;
  private List<ActionInfo> onCollide = new ArrayList<>();
  private transient Runnable positionCallback;

  /**
   * Creates a new game object.
   * Empty to use the default values during serialization
   */
  public GameObject() {
  }

  /**
   * Creates a new game object. This is mainly used for testing purposes
   * @param id The ID of the object
   * @param name The name of the object
   */
  public GameObject(int id, String name) {
    this.id = id;
    this.name = name;
    this.components = new ArrayList<>();
  }

  /**
   * Get the actions corresponding to this object on collisions
   * @return the actions corresponding to this object on collisions
   */
  public List<ActionInfo> getActions() {
    return this.onCollide;
  }

  /**
   * This method is used to check if this type falls into a more general category
   * @param type
   * @return
   */
  public boolean isA(String type) {
    return isA.contains(type) || name.equals(type);
  }

  /**
   * @return the image ID of the object
   */
  public String getImageID() {
    return imageID;
  }

  @Override
  public void setOnPositionUpdate(Runnable callback) {
    positionCallback = callback;
  }

  /**
   * @return the ID of the object
   */
  public int getId() {
    return id;
  }

  /**
   * @return if the object is collidable
   */
  public boolean isCollidable() {
    return collidable;
  }

  private void notifyPositionUpdate() {
    if (positionCallback != null) {
      positionCallback.run();
    }
  }

  @Override
  public double getX() {
    return x;
  }

  /**
   * Set the x position of the object
   * @param x the new x position
   */
  public void setX(double x) {
    this.x = x;
    notifyPositionUpdate();
  }

  @Override
  public double getY() {
    return y;
  }

  /**
   * Sets the y position of the object
   * @param y the new position of the object
   */
  public void setY(double y) {
    this.y = y;
    notifyPositionUpdate();
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  /**
   * Set the new width of the object
   * @param width the new width of the object
   */
  public void setWidth(double width){
    this.width = width;

  }

  /**
   * @param height the new height of the object
   */
  public void setHeight(double height){
    this.height = height;
  }

  @Override
  public int getID() {
    return this.id;
  }

  /**
   * @return the name of the object
   */
  public String getName() {
    return name;
  }

  /**
   * @return a list of components this object supports
   */
  public List<Component> getComponents() {
    return components;
  }

  /**
   * @param component the component to add to the object
   */
  public void addComponent(Component component) {
    this.components.add(component);
  }

  /**
   * remove a component with the specified ID
   * @param id
   */
  public void removeComponent(int id) {
    components.removeIf(comp -> comp.getId() == id);
  }

  /**
   * @return the velocity vector
   */
  public Vector getVelocity() {
    return velocity;
  }

  /**
   * @param velocity set a new velocity for the object
   */
  public void setVelocity(Vector velocity) {
    this.velocity = velocity;
  }

  /**
   * @return get a new location for the object
   */
  public Vector getLocation() {
    return new Vector(x, y);
  }

  /**
   * @param velocityX set a new x velocity
   */
  public void setVelocityX(double velocityX) {
    this.velocity.setX(velocityX);
  }

  /**
   * @param velocityY set a new y velocity
   */
  public void setVelocityY(double velocityY) {
    this.velocity.setY(velocityY);
  }

  /**
   * @return if the object has collided
   */
  public boolean getCollided() {
    return isCollided;
  }

  /**
   * @param collided check if the object has been collided with
   */
  public void setCollided(boolean collided) {
    isCollided = collided;
  }

  @Override
  public int compareTo(GameObject o) {
    double diff = this.getX() - o.getX();
    return (int) Math.signum(diff);
  }
}
