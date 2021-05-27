package ooga.model;

import java.util.function.Consumer;

public interface ObservableObject {
  void setOnUpdate(Runnable callback);

  double getX();
  double getY();
  double getHeight();
  double getWidth();
  int getMode();
  boolean isVisible();
  String objectId();
}

/*
imageModes.json
  {
    "happy_background": /path/to/OtherThing.jpg
    "yelling_mario": /path/tp/dank.jpg
  }

 GmaeObjects.json

 [

 levels [{
  image: "hapy_background"
 }
 ]
{
  modes : [
    1: thisImage.jpg
  ]
  onCollide: [
  {
    with: "player",
    action: "set_image",
    payload: "yelling_mario"
  }
  {
    with: "changer_thing",
    action: "setBackground",
    payload: "dark_mode";
  },
  ]
}

Map <String, Consumer> collisionMapper;

GameObject {
  for (String collision : onCollide) [
    handler = HandlerFactory.getHandler(collision.action, payload, this)
    collisionMapper.put(collision.with(), handler)
  ]
}

onCollide(Object)
collisionMapper.get(Object.getId).execute(Object)

collide(Object

 */