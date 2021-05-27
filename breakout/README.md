# Breakout

This project implements the game of Breakout.

Name: Robert Barnette

### Timeline

Start Date: 1/24/2021

Finish Date: 1/31/2021

Hours Spent: 25

### Resources Used
JavaFX Documentation/Tutorials  
https://horstmann.com/corejava/corejava_11ed-bonuschapter13-javafx.pdf  
https://docs.oracle.com/javase/8/javafx/api/toc.htm

Image Sources:  
https://id.pinterest.com/pin/770819292455320272/

https://www.performance-pcs.com/case-parts-mods/appliques/ppcs-premium-applique-skull-crossbones-vin-snc.html

https://www.amazon.com/LIGHTNING-BOLTS-PACK-REFLECTIVE-Windows/dp/B072HJ8Q6P

https://all-free-download.com/free-vector/red-heart-background.html

https://anupghosal.com/what-you-know-about-space-background-and-what-you-dont-know-about-space-background-space-background/ofila-universe-backdrop-13x13ft-earth-surface-planet-stars-footprints-outer-space-background-astronaut-party-event-decoration-school-activity-children-space-background/

https://www.cleanpng.com/png-explosion-encapsulated-postscript-starburst-777119/

https://www.nationalgeographic.com/science/space/

https://agfundernews.com/space-hemp-its-happening.html

https://time.com/universe-space-photos/

https://scitechdaily.com/space-is-not-empty-why-photons-flying-from-other-galaxies-do-not-reach-earth/


### Running the Program

Main class: Main

Data files needed: All of the data files present in the data folder are needed. These files represent the images that   
PowerUps are made from as well as the text files from which the objects in the levels are created.

Key/Mouse inputs:
* Left and right arrow keys are used to move the paddle 
* The mouse is used to aim the ball when the ball is caught by the paddle and mouse 1 is used to fire it in the direction  
of the target line.  
* The space bar can be used to catch the ball when it comes close to the paddle allowing it to be aimed and fired in a  
direction of the players choosing. The timing can be a bit tricky to get used to at first. 

Cheat keys:
* Characters 1-9 send the player to that particular level with 4-9 corresponding to level 4 as there are only 4 levels
* 'G' gives the player God mode allowing any block to be destroyed in one hit besides unbreakables
* 'R' resets the bouncer position to being stuck on the paddle
* 'P' increases the paddle speed
* 'B' decreases the ball speed
* 'L' gives the player 5 extra lives


### Notes/Assumptions

Assumptions or Simplifications:  
I slightly changed my cheat keys from the plan as some of my planned cheat codes were not very good. Specifically I did  
not include a cheat code to increase the ball speed as this made the game too fast and my collision detection not as consistent.  
Overall however, this game meets all requirements and executes my plan as I envisioned it.

Known Bugs: The bouncer can occasionally get stuck after breaking a block directly next to an unbreakable block. However,  
I adjusted my levels so that this doesn't happen and if the bouncer does get stuck it can be reset using the 'R' cheat key.

Extra credit: I added a fourth level to the game as well as 4 unique block types and 4 unique powerups. My paddle also  
has three different abilities in that it can change it size, catch and shoot balls, and move to the opposite side of the screen.  



### Impressions
I really enjoyed this assignment. It has been a long time since I wrote a JavaFX program and it is a lot more fun to make a game that  
users can interact with than a random C program that prints out stuff. It was also very easy to debug and I did not have too much trouble  
in getting the computer to do what I wanted. 
