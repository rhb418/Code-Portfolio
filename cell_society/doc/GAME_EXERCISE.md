# Simulation Lab Discussion

## Breakout with Inheritance

## Names and NetIDs
Robert Barnette rhb19
Kevin Yang kyy2
Felix Jiang fj32

### Block

This superclass's purpose as an abstraction: different kinds of blocks can all be created with their own functionality extending Block, but the core features of a block are all present in this Block class
```java
 public class Block {
   //Alter the color of the block based on the number of hits it has left
     public void updateHits (int hits) 
     //extends rectangle and determines the number of hits left before the block breaks
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class MovingBlock extends Block {
     @Override
     public void step (double time)
     // describes how the block will move, since normal blocks don't need to move, but moving ones do 
 }
```

#### Affect on Game/Level class (the Closed part)
Game/level class did not care what type of block each block was. It looped through a list of block calling the step function. Due to polymorphism, if the block was moving, java would call the correct method.


### Power-up

This superclass's purpose as an abstraction:
```java
 public class PowerUp {
     public Pair<Integer, Integer> getCoords(); 
     public int getType(); 
     // returns the type of powerups that this object represents as well as its position
     public void executePowerUp(Hashmap(Pair<Integer, Integer>, Block) blocks)
     //alters surrounding blocks when the powerup is activated. 
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends PowerUp {
     public int specialAction ()
     // powerup gives the special effect to the game when interacted with
 }
```

#### Affect on Game/Level class (the Closed part)
Similar to the block, if a powerup is given, the game only called the special action method of the powerup. The powerup would handle when its effect would be.


### Level
We did not have anyone subclass a level class, but one person did have a level class that would be responsible for updating all the items in the game. Because our game did not have too many different features per level, only new level objects were required, and not a subclass of a level class. However, adopting the level class idea would make implementing level class that is very different possible.

This superclass's purpose as an abstraction:
```java
 public class Level {
    private void updateScene ()
     //This class would contain the game loop and construct the level from other objects. It could also check for collisions and update the position of objects as it goes through the game loop. 
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends Level {
     public int something ()
     // this would create a either a new level from a new input or create a specific premade level, this can also handle switching between different levels
 }
```

#### Affect on Game class (the Closed part)
Creating a new level and switching between them would be managed completely outside of the main Game class. These levels can be passed to the game or other level manager to handle level interactions.


### Others?
A top level class called GameObject that all objects displayed on the game shares. Getter/setter methods could all be placed in such a class so that each new class would already have those methods, and we wouldn't need duplicate code in order to call these common methods on new objects added to the game (bouncer, paddle, block, etc.). Allows for more objects to be added to the game in an easier way. 
