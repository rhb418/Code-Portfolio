package ooga.model.systems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.StatsInfo;
import ooga.model.actions.CollisionAction;
import ooga.model.annotations.Track;
import ooga.model.components.ScoreComponent;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides the collision action to change score
 * <p>
 * User must specified the score value to change in the config, such as '-10.0'
 */
@Track(ScoreComponent.class)
public class ScoreSystem extends ComponentBasedSystem {

  private static final Logger logger = LogManager.getLogger(ScoreSystem.class);
  private static final String SCORE_STATS_NAME = "score";
  private static final String WHOSE_PAYLOAD_KEY = "whose";
  private static final String AMOUNT_PAYLOAD_KEY = "amount";

  private ComponentMapper<ScoreComponent> scoreMapper;

  public ScoreSystem(ECManager ecManager) {
    super(ecManager);
    addCollisionMapping("change_score", this::changeScore);
    scoreMapper = getComponentMapper(ScoreComponent.class);

    addStatsSupplier(SCORE_STATS_NAME, this::scoreStatsSupplier);
  }

  private List<StatsInfo> scoreStatsSupplier() {
    ArrayList<StatsInfo> ret = new ArrayList<>();
    for (ScoreComponent comp : scoreMapper.getComponents()) {
      ret.add(new StatsInfo(comp.getScore() + "", comp.getOwner().getId()));
    }
    return ret;
  }

  public void changeScore(CollisionAction event) {
    Map<String, String> payload = event.getPayload();
    if (payload.containsKey(WHOSE_PAYLOAD_KEY)) {
      for (GameObject entity : getECManager().getEntities(payload.get(WHOSE_PAYLOAD_KEY))) {
        ScoreComponent comp = scoreMapper.get(entity.getID());

        double delta = 1;
        try {
          delta = Double.parseDouble(payload.get(AMOUNT_PAYLOAD_KEY));
        } catch (NullPointerException | NumberFormatException e) {
          logger.error(
              "Cannot parse payload of 'change_score' action as a double: {}",
              event.getPayload()
          );
        }

        comp.changeScore(delta, true);
        logger.debug("Score of {} is now {}", entity.getName(), comp.getScore());
      }
    }
    triggerStatsUpdate(SCORE_STATS_NAME);
  }

  @Override
  public void update(double deltaTime) {
  }
}
