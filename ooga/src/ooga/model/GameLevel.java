package ooga.model;

import com.squareup.moshi.Json;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.managers.ECManager;
import ooga.model.managers.SystemManager;
import ooga.model.managers.ActionManager;
import ooga.model.managers.InputManager;
import ooga.model.managers.StatsManager;
import ooga.model.objects.GameObject;
import ooga.model.observables.ObservableLevel;
import ooga.model.observables.ObservableObject;
import ooga.model.systems.BaseSystem;
import ooga.model.systems.CollisionSystem;
import ooga.model.systems.HealthSystem;
import ooga.model.systems.LifeCycleSystem;
import ooga.model.systems.ScoreSystem;
import ooga.model.systems.MovementSystem;
import ooga.model.systems.TransformSystem;
import ooga.model.systems.WinSystem;
import ooga.model.systems.creature.AttackSystem;
import ooga.model.systems.creature.NPCSystem;
import ooga.model.systems.creature.PlayerSystem;
import ooga.model.systems.creature.SampleEnemySystem;

/**
 * Game Level is Meant to Implement and manage a game level
 *
 * It is created using a level factory and is returned as a Level
 * It implements the observable level interface in order to be represented by the view
 *
 * @author Oliver Rodas
 */
class GameLevel implements Level, ObservableLevel {

  // Moshi Items (add anything that is needed)
  private String name;
  private int levelID;
  private int height;
  private int width;
  private String background;
  private String focus;
  private int levelNumber;

  private transient List<BaseSystem> systems = new ArrayList<>();
  @Json(name = "objects")
  private ECManager ecManager;
  private transient InputManager inputManager = new InputManager();
  private transient ActionManager actionManager = new ActionManager();
  private transient StatsManager statsManager = new StatsManager();
  private transient SystemManager systemManager;
  private transient Consumer<ObservableObject> focusChangeCallback;

  /**
   * Create a new game level with default values
   */
  public GameLevel() {
  }

  /**
   * Initialize the level and create the systems
   */
  public void init() {
    // MUST be created AFTER ecManger is init by Moshi
    systemManager = new SystemManager();
    systemManager.createSystem(HealthSystem.class, ecManager);
    systemManager.createSystem(LifeCycleSystem.class, ecManager);
    systemManager.createSystem(CollisionSystem.class, ecManager, actionManager);
    systemManager.createSystem(PlayerSystem.class, ecManager);
    systemManager.createSystem(MovementSystem.class, ecManager);
    systemManager.createSystem(TransformSystem.class, ecManager);
    systemManager.createSystem(SampleEnemySystem.class, ecManager);
    systemManager.createSystem(NPCSystem.class, ecManager);
    systemManager.createSystem(WinSystem.class, ecManager);
    systemManager.createSystem(ScoreSystem.class, ecManager);
    systemManager.createSystem(AttackSystem.class, ecManager);
    systems = systemManager.getAllSystems();

    //ecManager.registerExistingComponents(ecManager.getEntities());

    for (var s : systems) {
      s.registerAllInputs(inputManager);
      s.registerAllActions(actionManager);
      s.registerAllStats(statsManager);
    }

    for (GameObject object : ecManager.getEntities()) {
      if (object.getName().equals(focus)) {
        notifyFocusChange(object);
        break;
      }
    }
  }

  private void notifyFocusChange(GameObject object) {
    if (focusChangeCallback != null) {
      focusChangeCallback.accept(object);
    }
  }

  /**
   * Handle a code from the controller as an input
   * @param k the input
   * @param on if the input was on or off
   */
  public void handleCode(String k, boolean on) {
    inputManager.handleCode(k, on);
  }

  @Override
  public String getBackgroundID() {
    return background;
  }

  @Override
  public ObservableLevel asObservable() {
    return this;
  }

  @Override
  public void setOnLevelEnd(Consumer<Boolean> update) {
    WinSystem winSystem = systemManager.getSystem(WinSystem.class);
    winSystem.setSetOnLevelEnd(update);
  }


  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public void setOnFocusUpdate(Consumer<ObservableObject> callback) {
    focusChangeCallback = callback;
  }

  @Override
  public void setOnStatsUpdate(String statsName, Consumer<List<StatsInfo>> callback) {
    getStatsManager().setOnStatisticUpdate(statsName, callback);
  }

  @Override
  public List<String> getAvailableStats() {
    return getStatsManager().getTrackableStatistics();
  }

  @Override
  public List<String> getAvailableActions() {
    return getActionManager().getAvailableActions();
  }

  @Override
  public List<String> getAvailableInputs() {
    return getInputManager().getRegisteredKeys();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getID() {
    return levelID;
  }

  @Override
  public void update(double deltaTime) {
    for (BaseSystem s : systems) {
      s.update(deltaTime);
    }
  }

  @Override
  public List<GameObject> generateObjects() {
    return ecManager.getEntities();
  }

  @Override
  public ECManager getECManager() {
    return ecManager;
  }

  /**
   * Get the stats manager
   * @return
   */
  public StatsManager getStatsManager() {
    return statsManager;
  }

  /**
   * Get the input manager
   * @return
   */
  public InputManager getInputManager() {
    return inputManager;
  }

  /**
   * Get the action manager
   * @return
   */
  public ActionManager getActionManager() {
    return actionManager;
  }

  @Override
  public void setOnNewObject(Consumer<ObservableObject> callback) {
    ecManager.setNewObjectCallback(callback);
  }

  @Override
  public void setOnObjectDestroy(Consumer<ObservableObject> callback) {
    ecManager.setDeleteObjectCallback(callback);
  }

  @Override
  public List<? extends ObservableObject> getAvailableGameObjects() {
    return ecManager.getEntities();
  }

  @Override
  public int getLevelNumber() {
    return levelNumber;
  }
}
