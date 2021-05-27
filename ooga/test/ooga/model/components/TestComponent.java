package ooga.model.components;

import ooga.model.components.Component;
import ooga.model.objects.GameObject;

public class TestComponent extends Component {

  public TestComponent(int id, GameObject owner) {
    super(id, owner);
  }

  @Override
  public String typeUnerasure() {
    return TestComponent.class.getName();
  }
}

