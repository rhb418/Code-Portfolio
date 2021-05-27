package ooga.view;

import ooga.model.ObservableObject;

/** A view component which can track with a given gameobject. */
public class ObjectView {
  ObservableObject gameObject;
  ImageConfiguration images;

  public ObjectView(ObservableObject obj, ImageConfiguration images) {
    this.gameObject = obj;
    this.images = images;
  }
}
