package ooga.model.objects;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * This cla
 */
public class GameObjectAdapter {

  private ObjectFactory objectFactory;

  public GameObjectAdapter(ObjectFactory objectFactory) {
    this.objectFactory = objectFactory;
  }

  @FromJson
  GameObject objectFromJson(ObjectInstance instance) {
    return objectFactory.buildObject(instance);
  }

  @ToJson
  ObjectInstance objectToJson(GameObject object) {
    ObjectInstance json = new ObjectInstance(object.getName(),
        object.getX(), object.getY(), object.getVelocity());
    return json;
  }
}
