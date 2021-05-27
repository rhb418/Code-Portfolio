package ooga.model.systems.creature;
/**
 * @author Tinglong Zhu
 */


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import ooga.model.actions.ObjectSpawner;
import ooga.model.annotations.Track;
import ooga.model.components.MovementComponent;
import ooga.model.components.PlayerComponent;
import ooga.model.objects.ObjectInstance;
import ooga.model.systems.ComponentBasedSystem;
import ooga.model.systems.ComponentMapper;
import ooga.model.managers.ECManager;
import ooga.model.systems.MovementSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: add tests for this
@Track({PlayerComponent.class, MovementComponent.class})
public class PlayerSystem extends ComponentBasedSystem {

  private static final Logger logger = LogManager.getLogger(PlayerSystem.class);

  protected ComponentMapper<PlayerComponent> playerMapper;
  protected ComponentMapper<MovementComponent> movementMapper;
  private final Map<String, BiConsumer<Integer, Boolean>> movementActionExecutors = Map.of(
      "MoveLeft", this::moveLeft,
      "MoveRight", this::moveRight,
      "Jump", this::jump);

  // TODO: support active and inactive players

  public PlayerSystem(ECManager ecManager) {
    super(ecManager);
    playerMapper = getComponentMapper(PlayerComponent.class);
    movementMapper = getComponentMapper(MovementComponent.class);

    for (PlayerComponent comp : playerMapper.getComponents()) {
      registerAction(comp);
    }
  }

  private void registerAction(PlayerComponent player) {
    for (ActionPair mapping : player.getActionMapping()) {
      int goId = player.getOwner().getId();
      MovementComponent movementComp = movementMapper.get(goId);
      if (movementComp == null) {
        logger.error(
            "PlayerComponent requires a MovementComponent on the its game object, but object {} doesn't",
            goId
        );
      }

      // Getting around the hardcoded methods
      if (mapping.getAction().equals("SpawnObject")) {
        ObjectSpawner spawner = new ObjectSpawner(mapping.getPayload(), getECManager());
        addMapping(mapping.getInput(), on -> spawner.handleSpawn(goId, on));
      } else if(movementActionExecutors.containsKey(mapping.getAction())) {
        BiConsumer<Integer, Boolean> executor = movementActionExecutors.get(mapping.getAction());
        addMapping(mapping.getInput(), on -> executor.accept(goId, on));
      }
    }
  }

  public void moveLeft(int entityId, boolean on) {
    MovementSystem movementSystem = getSystem(MovementSystem.class);
    movementSystem.moveLeft(entityId, on);
  }

  public void moveRight(int entityId, boolean on) {
    MovementSystem movementSystem = getSystem(MovementSystem.class);
    movementSystem.moveRight(entityId, on);
  }

  public void jump(int entityId, boolean on) {
    MovementSystem movementSystem = getSystem(MovementSystem.class);
    movementSystem.jump(entityId, on);
  }

  public List<PlayerComponent> getPlayers() {
    return playerMapper.getComponents();
  }



  @Override
  public void update(double deltaTime) {

  }
}
