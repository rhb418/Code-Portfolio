package ooga.model.systems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import ooga.model.StatsInfo;
import ooga.model.actions.CollisionAction;
import ooga.model.managers.ActionManager;
import ooga.model.managers.InputManager;
import ooga.model.managers.StatsManager;
import ooga.model.managers.SystemManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Baseclass of all systems. Manage input keymaps, action maps, and statistics maps.
 */
public abstract class BaseSystem {

  private static final Logger logger = LogManager.getLogger(BaseSystem.class);

  private Map<String, Consumer<Boolean>> keymaps;
  private Map<String, Consumer<CollisionAction>> actionMaps;
  private Map<String, Supplier<List<StatsInfo>>> statsSuppliers;

  /**
   * Used to trigger the update of a stats.
   * <p>
   * Optional since some systems don't need to track stats
   */
  private StatsManager statsManager;

  private SystemManager systemManager;

  /**
   * At this stage system communication is not functional yet, so don't call {@link
   * BaseSystem#getSystem(Class)} in the constructor
   */
  public BaseSystem() {
    keymaps = new HashMap<>();
    actionMaps = new HashMap<>();
    statsSuppliers = new HashMap<>();
  }

  /**
   * Set the system manager. This method should be only called by SystemManager
   */
  public void setSystemManager(SystemManager systemManager) {
    this.systemManager = systemManager;
  }

  /**
   * Get the system of specified type. This API is useful because it provides an interface for
   * cross-system communication.
   *
   * @param systemClass The class of the system being requested.
   * @param <T>         The system instance type
   */
  protected <T extends BaseSystem> T getSystem(Class<? extends BaseSystem> systemClass) {
    if (systemManager == null) {
      logger.error(
          "Cannot get system with type {} because this.systemManager is null", systemClass
      );
      return null;
    }
    BaseSystem ret = systemManager.getSystem(systemClass);
    if (ret == null) {
      logger.error(
          "Cannot find the requested system {} in this level, did you forget to add the system?",
          systemClass.toString()
      );
      return null;
    }
    return (T) ret;
  }

  protected void addMapping(String code, Consumer<Boolean> callback) {
    keymaps.put(code, callback);
  }

  protected void addCollisionMapping(String code, Consumer<CollisionAction> callback) {
    actionMaps.put(code, callback);
  }

  protected void addStatsSupplier(String stats, Supplier<List<StatsInfo>> supplier) {
    statsSuppliers.put(stats, supplier);
  }

  /**
   * Register all input keymappings.
   * <p>
   * This must be called for a system to receive and handle input events.
   */
  public void registerAllInputs(InputManager inputManager) {
    keymaps.forEach(inputManager::registerInput);
  }

  /**
   * Register all action mappings.
   * <p>
   * This must be called for a system to receive and handle action events.
   */
  public void registerAllActions(ActionManager actionManager) {
    actionMaps.forEach(actionManager::registerAction);
  }

  /**
   * Register all statistics mappings.
   * <p>
   * This must be called for a system to supply its statistics to view.
   */
  public void registerAllStats(StatsManager statsManager) {
    this.statsManager = statsManager;
    statsSuppliers.forEach(statsManager::registerStatsSupplier);
  }

  /**
   * Trigger the update of a stats, intended to be called in subclasses
   *
   * @param stats Stats name
   */
  protected void triggerStatsUpdate(String stats) {
    if (statsManager != null) {
      statsManager.updateStats(stats);
    } else {
      logger.warn("StatsManager is null, but triggerStatsUpdate() is called");
    }
  }

  /**
   * Called per-frame to update the game data.
   */
  public abstract void update(double deltaTime);
}
