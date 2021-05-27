package ooga.model.systems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import ooga.model.StatsInfo;
import ooga.model.Vector;
import ooga.model.actions.CollisionAction;
import ooga.model.components.ScoreComponent;
import ooga.model.managers.ECManager;
import ooga.model.managers.StatsManager;
import ooga.model.managers.SystemManager;
import ooga.model.objects.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreSystemTest {

  SystemManager systemManager;
  ECManager ecManager;
  StatsManager statsManager;
  GameObject go;
  ScoreSystem scoreSystem;
  ScoreComponent scoreComponent;
  List<StatsInfo> stats;

  @BeforeEach
  void setup() {
    statsManager = new StatsManager();
    systemManager = new SystemManager();
    ecManager = new ECManager(null);

    scoreSystem = systemManager.createSystem(ScoreSystem.class, ecManager);

    go = ecManager.createEntity("test entity");
    scoreComponent = ecManager.createComponent(go, ScoreComponent.class);

    scoreSystem.registerAllStats(statsManager);
  }

  @Test
  void testChangeScore() {
    scoreComponent.changeScore(-10, true);
    assertEquals(-10, scoreComponent.getScore());
    scoreSystem.changeScore(new CollisionAction(go, null, Map.of(
        "amount", "10",
        "whose", "test entity"
    )));
    assertEquals(0, scoreComponent.getScore());
  }

  @Test
  void testScoreStats() {
    statsManager.setOnStatisticUpdate("score", info -> stats = info);
    assertTrue(statsManager.getTrackableStatistics().contains("score"));

    scoreSystem.changeScore(new CollisionAction(go, null, Map.of(
        "amount", "10", "whose", "test entity"
    )));
    assertEquals(1, stats.size());
    assertEquals(10, Double.parseDouble(stats.get(0).value()));
  }

}
