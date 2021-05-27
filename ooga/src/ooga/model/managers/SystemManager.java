package ooga.model.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.systems.BaseSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemManager extends BaseManager {

  private static final Logger logger = LogManager.getLogger(SystemManager.class);

  private Map<Class<? extends BaseSystem>, BaseSystem> systems = new HashMap<>();
  private List<BaseSystem> orderedSystems = new ArrayList<>();

  public <T extends BaseSystem>
  T createSystem(Class<T> systemClass, ECManager ecManager) {
    ArrayList<BaseManager> args = new ArrayList<>();
    args.add(ecManager);

    ArrayList<Class<? extends BaseManager>> argTypes = new ArrayList<>();
    argTypes.add(ECManager.class);

    return createSystem(systemClass, args, argTypes);
  }

  public <T extends BaseSystem>
  T createSystem(Class<T> systemClass, ECManager ecManager, ActionManager actionManager) {
    ArrayList<BaseManager> args = new ArrayList<>();
    args.add(ecManager);
    args.add(actionManager);

    ArrayList<Class<? extends BaseManager>> argTypes = new ArrayList<>();
    argTypes.add(ECManager.class);
    argTypes.add(ActionManager.class);

    return createSystem(systemClass, args, argTypes);
  }

  public <T extends BaseSystem>
  T createSystem(
      Class<T> systemClass, List<? extends BaseManager> args,
      List<Class<? extends BaseManager>> argTypes
  ) {
    T ret = null;
    try {
      var constructor = systemClass.getConstructor(argTypes.toArray(new Class[0]));
      ret = constructor.newInstance((Object[]) args.toArray(new BaseManager[0]));
      ret.setSystemManager(this);
      systems.put(systemClass, ret);
      orderedSystems.add(ret);
    } catch (NoSuchMethodException e) {
      logger.error(
          "Cannot find a valid constructor in system class: " + systemClass.getName()
      );
    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
      logger.error(
          "Failed to instantiate system with class: " + systemClass.getName()
      );
    }
    return ret;
  }

  public List<BaseSystem> getAllSystems() {
    return orderedSystems;
  }

  public <T extends BaseSystem> T getSystem(Class<T> systemClass) {
    return (T) systems.get(systemClass);
  }
}
