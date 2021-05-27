package ooga.model.managers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles keyboard input. Register a callback using {@link InputManager#registerInput(String,
 * Consumer)}, then when the specified key is pressed/released, InputManager will call the
 * callback(s).
 */
public class InputManager extends BaseManager {

  private static final Logger logger = LogManager.getLogger(InputManager.class);

  private Multimap<String, Consumer<Boolean>> inputHandlerMap;

  public InputManager() {
    this.inputHandlerMap = ArrayListMultimap.create();
  }

  /**
   * Get all the keys that are already registered
   */
  public List<String> getRegisteredKeys() {
    return new ArrayList<>(inputHandlerMap.keys());
  }

  /**
   * Register a callback to trigger when a key is pressed/released
   *
   * @param code     The keycode as string
   * @param callback The callback to call
   */
  public void registerInput(String code, Consumer<Boolean> callback) {
    inputHandlerMap.put(code, callback);
  }

  /**
   * Handle a keycode using the registered callbacks
   *
   * @param code The keycode as string
   * @param on   Whether the key is pressed or released
   */
  public void handleCode(String code, boolean on) {
    inputHandlerMap.get(code).forEach(e -> e.accept(on));
    logger.info("Code {} handled for {} key", code, on);
  }

}
