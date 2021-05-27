package slogo.view.mediator;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import slogo.view.component.Component;

/**
 * The Mediator class handles how different components can interact with each other.
 *
 * Each component that can be changed by another component will have to do so through its
 * mediator. A component can change another component by adding the mediator of the component
 * they want to change. They can then run notifyMediators(this) to alert all mediators that the
 * component is subscribed to, passing itself to let the mediator know what type of component is
 * calling the mediator. Mediators use reflection by using a Resource file to run a certain method
 * based on the class that called it.
 *
 * @author Bill Guo
 */
public abstract class Mediator {

  private static final String ERROR_MESSAGE = "No behavior found";
  protected ResourceBundle methodBundle;

  /**
   * Checks to see what method to run based on the component that called the method.
   * @param component
   */
  public void notifyTarget(Component component) {
    try {
      this.getClass().getDeclaredMethod(methodBundle.getString(component.getClass().getName()),
          Component.class).invoke(this, component);
    } catch (Exception e) {
      makeAlert(ERROR_MESSAGE);
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

  /**
   * Sets the Resource file that houses the class to method key value pairs to the specified
   * methodBundle.
   * @param methodBundle
   */
  protected void setMethodBundle(ResourceBundle methodBundle) {
    this.methodBundle = methodBundle;
  }
}
