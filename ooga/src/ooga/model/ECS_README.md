ECS Note by Jiyang:

*I plan to implement ECS by imitating the designs
in [artemis-odb](https://github.com/junkdog/artemis-odb)*

*I'll update this file along with way to explain things more clearly*

# Overview

ECS is a data-oriented design that focus on separating the data from the logic. It has three parts:

- Entity is a bridge between components and system, it doesn't have much data. We can treat
  GameObject as an entity, or let the game object have an entity instance.
- Component contains most of the data. I was wrong about putting the logic here. System should take
  care of the game logic. Components should only contain data.
- Systems take care of all the game logic. They also handle creating, accessing, updating, and
  removing entities, components and other stuff.

Of course, we don't need to follow a 100% strict ECS design, so in this project, entities
are `GameObject`s, and they can contain data for convenience, for example, x and y coordinate.

ECS is great. We have all the data in the single place (`GameObject` or `Component`), and the
systems can fetch and update them in batch, and then view get the data and display them.

# Workflow

When a level is initialized, all the entities (game objects) and components are registered in and
will be managed by `EntityManager` and `ComponentManager`. These two managers keep track of all the
game objects and components in the current level.

During the update step of the level (`GameLevel.update()`), game systems will update their "
subscribed data" in their `update()` method. We can specify which data a system subscribes to/tracks
with help of entity and component managers, for example, `PlayerSystem` tracks all `PlayerComponent`
, while `TransformSystem` tracks data in all `GameObject`. See also `GameObjectBasedSystem`
and `ComponentBasedSystem`.

# Details

## How to add a new component-based system

TODO: adding a new component type

Create a class which extends `ComponentBasedSystem`, then annotate it with `@Track()` to specify
which Component type(s) the system wants to use. Also, implement `update()`.

```java
import ooga.model.components.HealthComponent;
import ooga.model.systems.ComponentBasedSystem;

@Track({HealthComponent.class, PlayerComponent.class})
// @Track(HealthComponent.class)
class XXXSystem extends ComponentBasedSystem {

  @Override
  public void update(double deltaTime) {
    destroyDetection();
  }
}
```

To get all the components of a specific type (using `HealthComponent` as an example), you can use
a `ComponentMapper<T>`. This class is quite useful, and will be the main gateway for systems and
components to communicate. It can be obtained using `ComponentBasedSystem.getComponentMapper()` with
a specified component type:

```java
import ooga.model.components.HealthComponent;
import ooga.model.components.PlayerComponent;

@Track({HealthComponent.class, PlayerComponent.class})
// @Track(HealthComponent.class)
class XXXSystem extends ComponentBasedSystem {

  ComponentMapper<HealthComponent> healthCompMapper;
  ComponentMapper<PlayerComponent> playerCompMapper;

  public XXXSystem() {
    healthCompMapper = getComponentMapper(HealthComponent.class);
    playerCompMapper = getComponentMapper(PlayerComponent.class);
    List<HealthComponent> comps = healthCompMapper.getComponents();
    PlayerComponent comp1 = playerCompMapper.get(entityID);
  }
}
```

Also, you can create and remove a component type in a game object,
see `systems/ComponentMapper.java`

## Entity/Component Manager

## Input handling

## Action handling