package ooga.model.systems;

import java.util.List;
import ooga.model.components.Component;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;

/**
 * Component mapper is a helper for accessing, updating, and deleting components that are associated
 * with game objects
 *
 * @param <T> Type of the component being tracked
 */
public class ComponentMapper<T extends Component> {

  private ECManager ecManager;
  private Class<T> componentClass;

  public ComponentMapper(
      ECManager ecManager, Class<T> componentClass
  ) {
    this.ecManager = ecManager;
    this.componentClass = componentClass;
  }

  /**
   * Get the component of type T from this entity
   *
   * @param entityId ID of the entity that possess the component
   */
  public T get(int entityId) {
    GameObject entity = ecManager.getEntity(entityId);
    if (entity == null) {
      return null;
    }

    for (Component component : entity.getComponents()) {
      if (component.getClass() == componentClass) {
        return (T) component;
      }
    }
    return null;
  }

  /**
   * Create a Component with type T for this entity
   *
   * @param entityId ID of the entity where component is created
   * @return the instance of the component
   */
  public T create(int entityId) {
    return ecManager.createComponent(ecManager.getEntity(entityId), componentClass);
  }

  /**
   * Remove component of type T from this entity
   *
   * @param entityId ID of the entity that possess the component
   */
  public void remove(int entityId) {
    GameObject entity = ecManager.getEntity(entityId);
    T comp = get(entityId);
    if (comp != null) {
      ecManager.removeComponent(componentClass, comp.getId());
      entity.removeComponent(comp.getId());
    }
  }

  /**
   * Get all components that are of type T in the current game level
   */
  public List<T> getComponents() {
    // NOTE: putting method call here can make sure that newly added components mid-game are included
    return ecManager.getComponents(componentClass);
  }
}
