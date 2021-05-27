package ooga.model.managers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import ooga.model.StatsInfo;
import ooga.model.observables.ObservableStatistics;

public class StatsManager extends BaseManager implements ObservableStatistics {

  Multimap<String, Consumer<List<StatsInfo>>> callbacks;
  Map<String, Supplier<List<StatsInfo>>> suppliers;

  public StatsManager() {
    callbacks = ArrayListMultimap.create();
    suppliers = new HashMap<>();
  }

  @Override
  public void setOnStatisticUpdate(String stats, Consumer<List<StatsInfo>> consumer) {
    callbacks.put(stats, consumer);
  }

  /**
   * Systems register a supplier so that StatsManager can get the value of a statistics using the
   * supplier
   *
   * @param stats    Statistics name
   * @param supplier Supplier function
   */
  public void registerStatsSupplier(String stats, Supplier<List<StatsInfo>> supplier) {
    suppliers.put(stats, supplier);
  }

  /**
   * Trigger the update of a statistics
   *
   * @param stats Name of the stats
   */
  public void updateStats(String stats) {
    List<StatsInfo> value = suppliers.get(stats).get();
    callbacks.get(stats).forEach(callback -> callback.accept(value));
  }

  @Override
  public List<String> getTrackableStatistics() {
    return new ArrayList<>(callbacks.keys());
  }
}
