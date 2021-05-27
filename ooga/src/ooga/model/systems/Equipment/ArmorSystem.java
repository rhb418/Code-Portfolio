package ooga.model.systems.Equipment;

import ooga.model.components.equipment.EquipmentComponent;
import ooga.model.managers.ECManager;
import ooga.model.systems.ComponentBasedSystem;
import ooga.model.systems.ComponentMapper;

public class ArmorSystem extends ComponentBasedSystem {

  ComponentMapper<EquipmentComponent> componentMapper;

  public ArmorSystem(ECManager ecManager) {
    super(ecManager);
  }

  @Override
  public void update(double deltaTime) {
  }

}
