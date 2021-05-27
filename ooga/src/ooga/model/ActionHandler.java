package ooga.model;

/*
  Model detects collision, calls
  obj1.collided(obj2);
  obj2.collided(obj1);

  colidded(Object2 obj2) {
    for (String key : collisionFunctionMap.keySet()) {
      if (obj2.isA(key)) {
        HandlerThing e = collisionFunctionMap.get
        e.handleCollision(obj2);
      }
    }
    ^^ Same thing but with lamda
  }

class E {
  public E(GameObject, ModelRef, Payload) {
    this.gameo
    ...
  }
  handleCollision(Obj2 obj) {
    // Do stuff with the references it has
    modelRef.setImageID(payload);
  }
}
 */
