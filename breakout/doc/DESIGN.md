# DESIGN Document for Breakout
## NAME: Robert Barnette

#Role


I was the sole author of this project and worked to implement all features present in the project. 


## Design Goals
I wanted to create a game that met all the design criteria present in the assignment that was visually appealing and  
was genuinely fun to play. I also wanted to make it so that new levels could be added very easily and for additional  
powerups to be easily incorporated into the game. 

## High-Level Design
This game is run through the Main class which sets the stage and generates all the scenes that the game will play out on.  
It does this by creating StartScreen, EndScreen, and Level objects which are attached to a given scene. The StartScreen   
class extends Pane and sets up the elements present on the start screen of the game. Likewise, the EndScreen class  
extends Pane and sets up the elements present on the end screen of the game. The Main class creates as many Level objects  
as there are Levels with the Level class setting up each level using a configuration file and handling user input while   
the level is active. All of these classes are also passed the primary stage and an ArrayList of all the scenes allowing   
them to change their scene to another part of the game when a certain condition is reached. The level class also uses the  
Filereader class to read and process a file into a game type. It also uses the block, powerup, paddle, and bouncer classes  
to create these types of objects. 

## Assumptions or Simplifications
The main simplification was to include a single level class that could generate any number of different levels instead of  
having a class for each level. This allows new levels to be created by simply adding another level text file and then  
creating an additional level object in Main. To that end I also created an encoding allow text files of integers to be  
processed and turned into specific objects that could be utilized in the game. 


## Changes from the Plan
For the most part I stuck with my initial plan; however, I decided to use a single level class instead of having multiple  
level classes for the reason described above. I also slightly altered some of my cheat codes as some of my original codes  
were not as interesting as some of the ones I chose to include in the final design. 



## How to Add New Features
New levels can be added by creating additional level configuration files and then by updating the main class with additional  
level objects. Powerups can be added by first selecting an image and integer encoding for the powerup and then adding these  
to the map in the powerup class. Then the powerup behavior can be implemented by adding another if branch in the executePowerup  
method in Level dictating the effect of the powerup. 