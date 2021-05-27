package ooga.view.components.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import ooga.view.util.LabelPair;
import ooga.view.util.ObservableResource;

public class StatsView extends HBox {
  private ObservableResource resources;
  private Map<String, LabelPair> stats;

  public StatsView(ObservableResource resources) {
    this.resources = resources;
    getStyleClass().add("stats-view");
    this.stats = new HashMap<>();
  }

  public void addStatistics(ObservableList<String> newStats) {
    for (String stat : newStats) {
      StringBinding binding = resources.getStringBinding(stat);
      LabelPair pair = new LabelPair(binding);
      stats.put(stat, pair);
      getChildren().add(pair);
    }
  }

  public void updateStat(String stat, String value) {
    if (stats.containsKey(stat)) {
      stats.get(stat).setValue(value);
    }
  }

  public Collection<String> getTrackedStats() {
    System.out.println(stats.keySet());
    return stats.keySet();
  }

  public void clear() {
    stats.values().forEach(o -> o.setValue(""));
  }
}
