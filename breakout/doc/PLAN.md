# Breakout Plan
#### Name: Robert Barnette

### Interesting Breakout Variants
I found the Super Breakout variant to be interesting in terms of its level design. It started out with a few very  
simple levels to get the player accustomed to the game mechanics and then slowly added more powerups and  
block types, making the game more complex. I think I want to incorporate this into my game design,  starting with  
simpler levels and then increasing the complexity and difficulty of the game with each individual level. I also  
found the Brick Breaker Hero and Brick n Balls variants interesting as they allowed for the ball to be aimed in a certain   
direction before shooting which I also may include in my game. 

### Paddle Abilities
For my paddle I currently want to have two distinct abilities. One of which being the ability to shoot the ball in  
a chosen direction at the start of the game or after the ball falls out of the screen. This will provide the player  
with more agency in selecting their shots and provides an additional level of strategy involved in perfecting one's  
gameplay. Additionally, I would like for the paddle to warp to the other side of the screen when it reaches an edge  
as this feature allows the player to more quickly adjust to oncoming projectiles on the other side of the screen.

### Block Types 
As far as block types go I want to include four distinct blocks. The first of these will be a basic block which takes  
a certain number of hits to break. The color of this block will change as it is hit by the ball and will be dependent  
on the number of hits it has left to break it. I also want to add an unbreakable block that instead serves as a hazard  
that the player must traverse to complete the level. I also want to have various powerup blocks that grant the user   
powerups when destroyed. Finally, I would like to add explosive blocks that damage or destroy surrounding blocks when   
hit.  

### Power Ups
Since I am including blocks that take multiple hits to break it would only be fitting to include a powerup that allowed  
for blocks to be broken at an increased rate. For instance if a block had 2 hits left to break this powerup would allow it  
to be broken in one hit. I also would like to include a powerup that increases the size of the paddle allowing the player   
to more easily hit the ball. Finally, I would like to include a powerups that either give an extra life or take a life away.   
I also may add a powerup that increases ball speed as a way to increase difficulties in later levels.


### Cheat Keys
I will include cheat keys that:
* Give the player unlimited lives
* Automatically clear the current level
* Decrease the ball speed by 50%
* Increase the ball speed by 100%
* Give the player a power up


### Levels
I want my levels to scale in both difficulty and complexity. To start I would like to have a level that consists of basic   
blocks with different numbers of hit requirements in order to introduce the player to the basic mechanics of the game.   
This level will look something like the diagram below.  
  
1 1 1 1 1 1   
2 2 2 2 2 2   
3 3 3 3 3 3   
4 4 4 4 4 4  
0 0 0 0 0 0 

where the numbers represent the number of times the block must be hit to break it and 0 represents empty space. For my   
second level I would like to include some unbreakable blocks as well as a small number of beneficial powerups as shown in the   
diagram below where B represents a beneficial powerup and U represents an unbreakable block. 

3 3 3 3 3 3  
4 4 4 4 4 4  
B 3 0 0 3 B  
4 4 4 4 4 4  
U 0 U U 0 0  

For my final level I want to include a large number of unbreakable blocks, some explosive blocks as well as some harmful powerups to make 
it more difficult  for the player. Below is a basic diagram of what this might look like in a simplified form. In the actual game I expect to include 
many more  blocks in more complex patterns, but for the purposes of this plan I just want to provide a general overview of what I plan to create. 

1 2 3 3 2 1   
U 3 H 4 2 H  
B 2 2 2 2 B  
1 E U U E 1  
0 0 0 0 0 0   




### Classes
For this project I will have a main class that creates the primary stage and the various scenes that will contain the  
different levels. This class will also have the main method that launches the application. I will also create a separate   
class for each level that reads a file and generates the objects present in the level. These classes will also have a method  
to update the level based on actions the player takes that is run every 1/60 of a second or so. I will also have a block class  
that will likely extend the rectangle class that will contain information regarding the status of each block and will have  
methods to retrieve and alter these values. Likewise I will have a class for both the powerups and the balls that will likely  
extend the imageview class to track the movements of these objects and their status in the level. 