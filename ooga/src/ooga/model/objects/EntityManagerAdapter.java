package ooga.model.objects;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import java.util.ArrayList;
import java.util.List;
import ooga.model.managers.ECManager;

/**
 * This adapter allows the entity manager to be created from a list of objects
 *
 * This class is a helper method for the Moshi Library to generate an ECManager without calling a contructor for it
 *
 * This class is to be added with the object factory into the GameLevel adapter when creating the adapter
 *
 * @author Oliver Rodas
 */
public class EntityManagerAdapter {

    private ObjectFactory factory;

  /**
   * Creates a new entity manager adapter
   * @param factory the object factory to use when creating an object
   */
  public EntityManagerAdapter(ObjectFactory factory) {
        this.factory = factory;
    }

  @FromJson
  /**
   * Converts a list of object instances to an ECManager
   * it is meant to deserialize a json object to the manager
   *
   * @param instanceList The list of objectinstances to convert to the manager
   * @return The new ECManager
   */
  public ECManager listToManager(List<ObjectInstance> instanceList) {
    ECManager manager = new ECManager(factory);
    for (ObjectInstance instance : instanceList) {
      manager.addEntity(instance);
    }
    return manager;
  }


  @ToJson
  /**
   * Converts an ECManager to a list of game objects
   * It is used to serialize an object to a json object
   *
   * @param manager The ECManager to serialize
   * @return the list of game object instances
   */
  public List<ObjectInstance> managerToList(ECManager manager) {
    List<ObjectInstance> instanceList = new ArrayList<>();
    for (GameObject object : manager.getEntities()) {
      instanceList.add(new ObjectInstance(object));
    }
    return instanceList;
  }
}
