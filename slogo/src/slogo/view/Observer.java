package slogo.view;

/**
 * The Observer interface is used to update a class in response to a change in another class.
 *
 * Observers only need to implement the update() method, which is called by an Observable object
 * who has added this Observer to them. In this project, the Observer pattern is mainly used to
 * update Subviews when a command has been run. Subviews can then check their BackEndController
 * in order to update themselves.
 *
 * @author Bill Guo
 */
public interface Observer {

  /**
   * Runs the update process of the Observer whenever the Observable notifies the Observer of a
   * change.
   */
  void update();

}
