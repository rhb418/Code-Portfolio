package ooga.model.objects;

import java.util.List;
import ooga.model.components.Component;

public class ActiveAgent extends GameObject {

  private String type;
  private int height, width;
  private List<Component> components;
  private String imageID;
  private int x, y;

  public ActiveAgent(String type,
      int height, int width,
      List<Component> components,
      String imageID, int x, int y) {

    super(1, type);

    this.type = type;
    this.height = height;
    this.width = width;
    this.components = components;
    this.imageID = imageID;
    this.x = x;
    this.y = y;
  }


}
