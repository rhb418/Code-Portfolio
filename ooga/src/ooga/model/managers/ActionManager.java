package ooga.model.managers;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.actions.Action;
import ooga.model.actions.ActionInfo;
import ooga.model.actions.CollisionAction;
import ooga.model.actions.CollisionInfo;
import ooga.model.objects.GameObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionManager extends BaseManager {

  private static final Logger logger = LogManager.getLogger(ActionManager.class);

  ListMultimap<String, Consumer<CollisionAction>> actions;

  public ActionManager() {
    actions = MultimapBuilder.hashKeys().arrayListValues().build();
  }

  public void registerAction(String actionName, Consumer<CollisionAction> action) {
    actions.put(actionName, action);
  }

  public void handleAction(GameObject self, GameObject other, CollisionInfo a) {
    List<ActionInfo> actionInfos = self.getActions();
    for (ActionInfo ai : actionInfos) {
      if (ai.equals(a)) {
        doAction(ai, self, other);
      }
    }
  }

  private void doAction(ActionInfo action, GameObject self, GameObject other) {
    for (Action actionInstance : action.getActions()) {
      var callbacks = actions.get(actionInstance.getName());

      // check if the action being trigger is registered
      if (callbacks == null || callbacks.size() == 0) {
        logger.error("The action being triggered is not registered {}", actionInstance.getName());
        return;
      }

      for (Consumer<CollisionAction> actions : callbacks) {
        actions.accept(new CollisionAction(self, other, actionInstance.getPayload()));
      }
    }
  }

  public List<String> getAvailableActions() {
    return new ArrayList<String>(actions.keys());
  }

}
