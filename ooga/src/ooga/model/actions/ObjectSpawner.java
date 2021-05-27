package ooga.model.actions;

import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import ooga.model.objects.ObjectInstance;

/**
 * Object Spawner is meant to be used to have any action create arbitrary objects.
 *
 * It is meant to be instantiated using the the ecManager to be able to create the objects
 *
 * @author Oliver Rodas
 */
public class ObjectSpawner {
  private String payload;
  private ECManager ecManager;
  private double cooldown;
  private long start = System.nanoTime();

  /**
   * Create a new object spawner
   * @param payload The payload of the spawner to use
   * @param ecManager the manager to use to create objects
   */
  public ObjectSpawner(String payload, ECManager ecManager) {
    this.payload = payload;
    this.ecManager = ecManager;
  }

  /**
   * Spawn an object using the id of the parent
   * @param id The id of the parent object
   * @param on if to do the spawn
   */
  public void handleSpawn(Integer id, boolean on) {
    if (on) {
      double elapsedTime = (Long.valueOf(System.nanoTime() - start)).doubleValue()/1000000000;
      System.out.println(elapsedTime);
      if (elapsedTime > cooldown) {
        ObjectInstance newGuy = parsePayload(payload, ecManager.getEntity(id));
        ecManager.addEntity(newGuy);
        start = System.nanoTime();
      }
    }
  }


  private ObjectInstance parsePayload(String payload, GameObject original) {
    String[] params = payload.split(",");
    String which = params[0];

    int offsetx = Integer.parseInt(params[1]);
    int offsety = Integer.parseInt(params[2]);
    cooldown = Double.parseDouble(params[3]);
    
    return new ObjectInstance(which, original.getX() + offsetx, original.getY() + offsety);
  }
}
