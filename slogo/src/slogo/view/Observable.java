package slogo.view;

/**
 * The Observable interface is used to notify Observers whenever a change happens to the
 * Observable.
 *
 * Observables can add Observers to themselves and call notifyObservers(), which will call the
 * Observers update() method. This design pattern is used to let classes communicate with
 * eachother without having a specific reference to each other in order to decrease coupling.
 * This design pattern is more simple than than the Mediator design pattern, as Observers can
 * only subscribe to one Observer.
 *
 * @author Bill Guo
 */
public interface Observable {

  /**
   * Attaches Observers to this Observable object to notify them.
   * @param observer Observer to be notified
   */
  void attach(Observer observer);

  /**
   * Alerts all Observers that a change has been added.
   */
  void notifyObservers();

}
