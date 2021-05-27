package slogo.view.component;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import slogo.view.mediator.Mediator;

/**
 * The Component abstract class represents any graphic made in the Workspace that can subscribe to
 * any number of mediators in order to notify any component's mediator that a changed has occurred.
 *
 * Components can also call alerts when an error has occurred.
 *
 * @author Bill Guo
 */
public abstract class Component {

  private List<Mediator> mediators;

  /**
   * Constructor of the Component.
   */
  public Component() {
    mediators = new ArrayList<>();
  }

  /**
   * Adds a mediator to the component.
   * @param mediator to be added
   */
  public void addMediator(Mediator mediator) {
    mediators.add(mediator);
  }

  /**
   * Calls the notifyTarget() method of all mediators that the component subscribes to.
   */
  protected void notifyMediator() {
    for (Mediator mediator : mediators) {
      mediator.notifyTarget(this);
    }
  }

  /**
   * Displays alert message, used to handle errors that occur during the loading or parsing of a
   * configuration file
   *
   * @param errorMessage - the message displayed to the user
   */
  protected void makeAlert(String errorMessage) {
    Alert alert = new Alert(AlertType.ERROR, errorMessage);
    alert.showAndWait().filter(response -> response == ButtonType.OK);
  }
}
