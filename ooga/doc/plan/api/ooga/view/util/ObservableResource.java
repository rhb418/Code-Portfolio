package ooga.view.util;

import java.util.ResourceBundle;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * A class that allows bindings for resources to allow bindings between strings
 * and anything that takes string bindings.
 */
public class ObservableResource {

  private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

  public ObjectProperty<ResourceBundle> resourcesProperty() {
    return resources;
  }

  private ResourceBundle getResources() {
    return resourcesProperty().get();
  }

  public void setResources(ResourceBundle resources) {
    resourcesProperty().set(resources);
  }

  public StringBinding getStringBinding(String key) {
    return new StringBinding() {
      {
        bind(resourcesProperty());
      }

      @Override
      protected String computeValue() {
        return getResources().getString(key);
      }
    };
  }
}
