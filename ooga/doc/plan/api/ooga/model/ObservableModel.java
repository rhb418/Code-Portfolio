package ooga.model;

import java.util.function.Consumer;

public interface ObservableModel {

  void setOnLevelChange(Consumer<Level> callback);
  void setOnNewObject(Consumer<ObservableObject> callback);
  ModelController getController();
  void setOnTextUpdate(Consumer<String> text);

}
