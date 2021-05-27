# Design

# DESIGN Document for OOGA
## NAME(s)

Names: Robert Barnette (rhb19) Tinglong Zhu (tz88) Oliver Rodas (oar5) Jiyang Tang (jt304) Joshua Petitma (jmp157)

## Role(s)
Oliver: I focused on configuration to convert data files into objects that could be used in the game. I created the original classes for game levels and game objects. I wrote the example files for data and wrote the original goomba's revenge and galaga. I helped on the view working on caching and adding right clicking to the game. I created factories and the files that held the data for things like collisions and data files.

Robert: I primarily worked on writing a directional collision detection algorithm in the CollisionSystem class that also served to rectify collisions, preventing objects from being inside each other. I also wrote the WinSystem, WinComponent, and WinCondition classes that were responsible for detecting and handling win/loss conditions for a level. I also wrote the configuration files for the Geometry Dash game.

Tinglong:
- Controller (Mainly proxy, now the Controller.java is under the view folder)
- Systems:
    - AttackSystem
    - EnemySystem
    - NPCSystem
    - PlayerSystem
    - SampleEnemySystem
    - HealthSystem
    - MovementSystem(with Jiyang)
- Components:
    - HateComponent
    - WeaponComponent
    - EquipmentComponent
    - AttackComponent
    - CriticalHitComponent
    - HealthComponent
    - MovementComponent (with Jiyang)
    - MovementSequenceComponent
- Some small feature implementation and bug fixes.
- tests for the system created.


## Design Goals
The primary goal of this design was to create a flexible, data driven game engine that could be used to generate a variety of 2D scroller games using data files. We wanted to give the player as much agency as possible in building their game by allowing them to specify as much about the game as possible from within the configuration files. Additionally we wanted to demonstrate the effectiveness of this system by creating several unique games taking full advantage of the Entity Component system we created to allow for a compelling gameplay experience.

## High-Level Design
### MVC + Observer Pattern

#### Backend:
#### Model
- Action:
    - Action: Data storage: name, payload, etc for certain actions
      Will be called in the level.
- Track: Annotation help to find instances of a certain class.

- Component:
    - Data storage: name, etc (like attack damage). for the certain component. It provides data for system.

- Systems:
    - logic of the game rule/features.
- Manager:
    - Used for add/link/delete objects/components/systems

- GameObjects:
    - Store the basic properties of the gameobject: enemies, player, etc.

- Adapter & Factory: Turn config files to Java GameObjects,  components and levels.


- Observables:
    - Use callbacks to notify the changes to other component of the project: view, etc.

- Controller:
    - Proxy between Model and View

- Model:
    - Deal with the levels.



## Assumptions or Simplifications
Assumptions or Simplifications:
- The enemy will immediately attack the player when the distance between the player and the enemy is within certain range.
- All GameObjects will have speed and locations.
- What can be overwritten in a data file is limited to position, speed, and typing. All other parameters can be changed by created a new type

## Changes from the Plan
- Treat Action as distince classes represented by strings and payload. Now use the ActionManager to manage them
- The view will not communicate to the model directly. All it will do is observe
  changes that happen, but if it needs to tell the model something it will do it
  through the controller. -> View act as a proxy (pattern) between model and view.

## How to Add New Features

### Data File

* objects.json : This file describes the types of GameObjects that are present in the game and assigns components to GameObjects if necessary. It also describes all the possible actions the GameObjects can run when collided with other objects in a particular direction.
* levelx.json : There should be any number of levelx.json files where x is the level number. These files define the GameObjects present in the level and assigns them their x,y positions.
* view.json : This file should contain the KeyMappings to map certain keys to actions that are called in the game
* images.json : This file maps the image IDs of the objects to a filename for that image

### Systems and Components
- add new systems (new logic rules) to the src/ooga/model/systems
- add new components needed to the src/ooga/model/components