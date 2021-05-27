package ooga.model.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ooga.model.components.Component;
import ooga.model.objects.GameObject;
import ooga.model.objects.ObjectFactory;
import ooga.model.objects.ObjectInstance;
import ooga.model.observables.ObservableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * System for creating, accessing, updating, and deleting entities/components.
 */
public class ECManager extends BaseManager {

  private static final Logger logger = LogManager.getLogger(ECManager.class);

  private IDManager idManager = new IDManager();
  private Map<Integer, GameObject> entities = new HashMap<>();
  private Map<Class<? extends Component>, Map<Integer, Component>> existingComponents = new HashMap<>();
  private ObjectFactory factory;
  private Consumer<ObservableObject> newObjectCallback;
  private Consumer<ObservableObject> deleteObjectCallback;

  public ECManager(ObjectFactory factory) {
    this.factory = factory;
  }

  public List<GameObject> getEntities() {
    return new ArrayList<>(entities.values());
  }

  public GameObject createEntity(String name) {
    int id = idManager.getNewId();
    GameObject ret = new GameObject(id, name);
    entities.put(id, ret);
    return ret;
  }

  public GameObject getEntity(int ID) {
    return entities.get(ID);
  }

  /**
   * Delete entity from EntityManager, and remove the components it has from ComponentManager
   */
  public void deleteGameObject(int ID) {
    GameObject entity = entities.get(ID);
    if (entity == null) {
      return;
    }

    notifyObjectDelete(entity);

    // remove all of its components
    for (Component component : entity.getComponents()) {
      removeComponent(component.getClass(), component.getId());
    }
    logger.debug("Removing {}", ID);
    entities.remove(ID);
  }

  public void addEntity(ObjectInstance instance) {
    GameObject newObject = factory.buildObject(instance);
    entities.put(newObject.getId(), newObject);

    for (Component component : newObject.getComponents()) {
      registerExistingComponent(newObject, component);
    }

    notifyNewObject(newObject);
  }

  public void setNewObjectCallback(Consumer<ObservableObject> newObjectCallback) {
    this.newObjectCallback = newObjectCallback;
  }

  public void setDeleteObjectCallback(Consumer<ObservableObject> deleteObjectCallback) {
    this.deleteObjectCallback = deleteObjectCallback;
  }

  private void notifyNewObject(GameObject newObject) {
    if (newObjectCallback != null && newObject != null) {
      newObjectCallback.accept(newObject);
    }
  }

  private void notifyObjectDelete(GameObject deleteObject) {
    if (deleteObjectCallback != null && deleteObject != null) {
      deleteObjectCallback.accept(deleteObject);
    }
  }

  public <T> List<T> getComponents(Class<T> componentClass) {
    Map<Integer, Component> components = existingComponents.get(componentClass);
    if (components == null) {
      return new ArrayList<T>();
    }
    return (List<T>) new ArrayList<>(components.values());
  }

  public <T extends Component> void registerExistingComponent(GameObject owner, T component) {
    component.setOwner(owner);
    component.setId(idManager.getNewId());
    addComponentToMap(component);
  }

  /**
   * @apiNote This doesn't remove the component from its parent's component list
   */
  public <T> void removeComponent(Class<T> componentType, int id) {
    Map<Integer, Component> idCompMap = existingComponents.get(componentType);
    if (idCompMap != null) {
      idCompMap.remove(id);
    } else {
      logger.warn(
          "Remove component of type {}, but there is currently no such components",
          componentType.toString()
      );
    }
  }

  private <T extends Component> void addComponentToMap(T component) {
    int id = component.getId();

    existingComponents.putIfAbsent(component.getClass(), new HashMap<>());
    Map<Integer, Component> idCompMap = existingComponents.get(component.getClass());
    idCompMap.put(id, component);
  }

  public void registerExistingComponents(List<GameObject> gameObjects) {
    for (GameObject go : gameObjects) {
      for (Component comp : go.getComponents()) {
        registerExistingComponent(go, comp);
      }
    }
  }

  public <T extends Component> T createComponent(
      GameObject owner, Class<T> componentClass
  ) {
    T ret = null;
    try {
      var constructor = componentClass.getConstructor(int.class, GameObject.class);
      ret = constructor.newInstance(idManager.getNewId(), owner);
      owner.addComponent(ret);
      addComponentToMap(ret);
    } catch (NoSuchMethodException e) {
      logger.error(
          "Cannot find a valid constructor in component class: " + componentClass.getName()
      );
    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
      logger.error(
          "Failed to instantiate component class: " + componentClass.getName()
      );
    }
    return ret;
  }

  public List<GameObject> getEntities(String whose) {
    List<GameObject> ret = new ArrayList<>(getEntities());
    ret.removeIf((entity) -> !entity.getName().equals(whose));
    return ret;
  }
}
