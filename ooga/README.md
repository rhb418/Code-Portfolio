# README

Names: Robert Barnette (rhb19) Tinglong Zhu (tz88) Oliver Rodas (oar5) Jiyang Tang (jt304) Joshua Petitma (jmp157)

### Timeline

Start Date: March 25 2021

Finish Date: April 30 2021

Hours Spent:
Robert : 50
Tinglong: 60

### Primary Roles
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

### Resources Used
Unity Document

### Running the Program

Main class: Main

Data files needed:

Each game needs a folder containing configuration files defining the game. These include:

* objects.json : This file describes the types of GameObjects that are present in the game and assigns components to GameObjects if necessary. It also describes all the possible actions the GameObjects can run when collided with other objects in a particular direction.
* levelx.json : There should be any number of levelx.json files where x is the level number. These files define the GameObjects present in the level and assigns them their x,y positions.
* view.json : This file should contain the KeyMappings to map certain keys to actions that are called in the game
* images.json : This file maps the image IDs of the objects to a filename for that image
  Features implemented:
- win lose the game
- select the game from different folders
- exit game after finished and reselect the level
- splash screen
- transition after effects
- some exceptions
- press key to move the player/attack the enemy
- the enemy can moved in a sequence.
- the enemy can spawn bullets to attack the player
- health, scoring system and their displays
- critical hit

#### Systems
Systems classes contain the core logic dictating gameplay behaviors.
* Collision System: Using this system our program can detect collisions between objects as well as the direction in which the collision occurs. This system also automatically uncollides objects upon collisions preventing overlap. This system can be used to determine if objects are collided and calls predefined actions on GameObjects which are specified in the objects.json file. These actions can differ based on the direction of the collision and the objects that are collided allowing the user to specify a great deal of information
* Win System: Using this system our program can detect and execute win conditions that the user defines in the objects.json file. For instance the user can specify that the player should win when score is greater than 100 and the level will end with a win once the player's score has exceeded 100.


### Notes/Assumptions

Assumptions or Simplifications:
- The enemy will immediately attack the player when the distance between the player and the enemy is within certain range.
- All GameObjects will have speed and locations.

Interesting data files:
- Galaga: Random movement and actions for the enemies

Known Bugs:
* Occasionally a GameObject can get stuck when it jumps and hits a corner where it is surrounded by 3 other objects.
* Occasionally the camera will not follow the player when a level is reset
* When the player been killed, sometimes it will got errors, might due to that the deleted objects are not completely unlinked.

Extra credit:


### Impressions

* This project proved to be very difficult with the time allowed, especially considering some of us have been dealing with other project based ECE and CS courses and as such we were not able to implement all of the features we would have liked to.
* (Tinglong) The project is great. I got impressed by the design of our game engine. I think I have to do more research even before starting thinking about the design. I really learned a lot ideas from my teammates' designs.
* (Oliver) This project was difficult to finish as I had three other project based classes that I had to work on as well. The time zones were also difficult to manage as I was either asleep or working for something else at any given time.