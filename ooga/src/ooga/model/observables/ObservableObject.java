package ooga.model.observables;

public interface ObservableObject {

  void setOnPositionUpdate(Runnable callback);

  double getX();

  double getY();

  double getHeight();

  double getWidth();

  int getID();

  String getImageID();
}
