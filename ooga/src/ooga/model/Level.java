package ooga.model;

import java.util.List;
import java.util.function.Consumer;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import ooga.model.observables.ObservableLevel;

public interface Level {

  String getName();

  int getID();

  void update(double deltaTime);

  List<GameObject> generateObjects();

  ECManager getECManager();

  void handleCode(String k, boolean on);

  String getBackgroundID();

  ObservableLevel asObservable();

  void setOnLevelEnd(Consumer<Boolean> update);

  int getLevelNumber();
}
