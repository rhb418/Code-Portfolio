package ooga.model.observables;

import java.util.List;
import java.util.function.Consumer;
import ooga.model.StatsInfo;

public interface ObservableStatistics {

  public void setOnStatisticUpdate(String statistic, Consumer<List<StatsInfo>> consumer);

  public List<String> getTrackableStatistics();
}
