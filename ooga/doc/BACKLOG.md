# BACKLOG

## Oliver

1.  The player selects a game from the menu.

    The controller tells the model to set the game based off of a string. The
    model will then check the config.json file in the game package (named the
    same way as in the selection). The config file contains the levels in the
    order that they are played (but do not necessarily get played in that
    order). The first level is loaded and the view is told to update.

2.  The player presses space after selecting the mario game and sees the player
    character jump.

    The controller passes the input to the model using an interface. The model
    grabs the player object and tells it to handle the input. The player object
    uses a map to select the appropriate action based on the input.

3.  The game creator would like to create a new level for a game that was
    already created

    The creator would create a new json file that contains the level name. There
    they can set a background from the images json config file by setting a type
    name. To define a new type of game object, they must first add it in the
    GameObjects.json file. This file holds the presets that they can override.
    The new level's filename must be added in the position they want it to be
    shown.

4.  A game creator uses a type that was not defined in the config file

    The steps to start the game would occur, but once the game is created by the
    model (probably using a factory/parser), an error will be thrown saying that
    the game object was not defined.

5.  A gameobject collides with another game object that was set to "kill" one of
    the game objects if it was collided.

    When the collision is detected, both objects are told to collide with the
    other. Each object will do whatever action was done to it. The killed object
    will be removed (maybe play an animation)

6.  The player collides with a hitbox that makes the player stop and read a
    dialog

    When collision is detected, the hitbox is given the player object. The
    hitbox uses a map that takes in the object type and gets a handler for the
    collision. The handler will be a modifySpeed handler and the players speed
    will be set to 0. The other handler will be a display text handler and the
    text payload will be passed and the new game element will be drawn on the
    screen.

7.  The user wants to use a predefined "Goomba" object, but wants to double its
    size.

    The user has two options. In their level file, the user would add a goomba
    object and override its height and width. The user could also create a new
    object in the gameobjects file. They could reference the type goomba and
    override its height and width on a global level.

8.  The user wants to create a player with a mario image that crouches with the
    down arrow key.

    The user would define a player game object in the gameobjects file. The
    player would set the object image as a reference to the mario image. The
    user would also add a second image for the crouching mode. The user would
    then add an input manager so when the player holds down, the player object
    will display the crouching mode.

9.  The player has a damaging projective that hits a wall.

    The user has a projectile that is set so that when it hits another object,
    it disappears. The wall can be set as damagable, but if it is not, then the
    projectile just disappears.

10. The player moves to the right, updating the view representation of the
    player.

    The player will press the key to go right. It can be set to set velocity or
    position. Either way, when the player updates, it notifies the view and the
    view can use the getters on the object to get the position and state of the
    game object.

## Jiyang

1.  Game dev writes a new collider class for the player, and add it to the
    player gameobject in the config file.

    The level will load the config file, and find the collider class using
    reflection, initialize it and add it to the player's component list

2.  Game dev wants to bind up direction key to player jump.

    In the input config file, a new entry can be added, and game dev can specify
    what handler function the key maps to. During level loading, the game will
    remember what handler each key maps to, and trigger the handler(s) when the
    input is received.

3.  Game dev use a collider to determine if the current level is won

    Game dev set the `onCollide` attribute of an object to the win action
    handler, and the game will be won if player collide with the collider

4.  The player redefines the attack key to `j` in game settings

    The mapping from key name to callbacks in input manager is updated

5.  A platform in the air moves, and also moves the player with it

    When player steps on the platform, the collision triggers the player to
    register itself to be on a block. Then when the block moves, it tells the
    Transform component of player to move

6.  A platform in the air disappears, and the player starts dropping because of
    gravity

    When the platform disappears, the collider of it is disabled. And in the
    next frames, the collision detection loop won't chek this platform

7.  The "moon" level gravity is set to 0.1

    In the level config file, the dev can set the 'gravity' attribute of the
    RigidBody2D component of player to 0.1

8.  There is a light source on player, so it lights up areas around the player

    The game dev add a SpriteRenderer component to the player, with an
    half-transparent white image and a radius set.

9.  When the player swings a sword, a swing sound is played

    The game dev can add a SoundSource component to the player and set the
    callback of pressing attack key to play 'swingSword.wav' file.

10. When vampire enemies deal damage to the player, their health is then
    increased

    The game dev set the callback of enemy successfully dealing damage to
    increment health action.

## Robert

1.  The game developer wants the camera to follow the player as the level
    progresses.

    In the game config file, the developer specifies the specific mode that the
    camera should take. When the model loads this file in the camera is then set
    to follow the player.

2.  The player reaches the end of a level and the next level is started.

    The model loads the next level from a config file and signals the view to
    update the level using the setOnNewLevel listener.

3.  The player collides with a projectile and dies.

    The projectile and the player are collided with one another and the player's
    health is reduced to zero. An on death callback is called displaying a
    window allowing the player to restart the level.

4.  The game developer wants to add a new object to their level.

    The developer first creates an additional entry in the objects.json file and
    then adds a reference to this entry in a new level.json file, specifying its
    size and position.

5.  The player hits an object with their sword breaking it.

    The oncollide action handler is called on both the players sword and the
    object. The object is then broken and removed from play when this collision
    occurs.

6.  The game developer wants to create a new level and add it to the game.

    The developer makes a new configuration file specifying the objects that
    will be in the level and adds any new objects to the objects file.

7.  A player runs into a hitbox triggering a new obstacle to appear.

    In the oncollide method of the hitbox, a obstacle is triggered to be spawned
    at a certain position.

8.  The player beats the last level of the game and is returned to the title
    screen.

    A hitbox in this level is collided with the player when they finish the
    level and its oncollide method returns the player to the title screen.

9.  The game developer wants to bind mouse 1 to the shoot projectile action.

    The developer modifies the input configuration file mapping the shoot
    projectile action to mouse 1.

10. The player picks up an item and is given additional health.

    The item and the player collide with each other. The item is removed from
    the level and the player's health is incremented.

## Josh

1.  The user wants to change the language of the application

The user presses "Escape" to launch the global pause menu. The event filter
placed on the game scene is caught and checks that escape was pressed. A dialog
is launched that has a settings button. When the settings button is pressed the
dialog is changed to a view that shows all the settings availble for the system
and the specific game. The user can click on the language option under the
global settings and set the language of the application.

2.  The user presses the right arrow key

The event is catched and then checked to see if there is a related mapping. The
handler realizes that there isn't a mapping for the right arrow key and nothing
happens.

3.  The user presses the 'k' key

The event is catched and then checked to see if there is a related mapping. The
handler sees that there is a mapping for this and passes it to the controller.
Why is there a mapping for 'k' but not for the right arrow key? Well because of
[this](https://vim.fandom.com/wiki/Vim_Tips_Wiki)

4.  The user wants to remap a key

The user presses "Escape" to launch the global pause menu. The event filter
placed on the game scene is caught and checks that escape was pressed. A dialog
is launched that has a settings button. When the settings button is pressed the
dialog is changed to a view that shows all the settings availble for the system
and the specific game. The user can click on the "Game Settings" tab which
switches the settings pane view to settings specific to the game. From there
they can scroll to the "Hot Keys" section and look at the hotkey they want to
switch.

5.  The user tries to remap a hotkey to an already existing one

When the user clicks on an action the label changes to prompt them to "Press
Key". When the key is pressed the handler looks to see if there is already a
mapping for that key, if there is then an error dialog pops up insulting the
user.

6.  The user tries to remap a hotkey to a new key

When the user clicks on an action the label changes to prompt them to "Press
Key". When the key is pressed the handler looks to see if there is already a
mapping for that key.  If not then the mapping is changed on the view and then
the handler sets it on the controller.

7.  The User wants to change the images for this game

The user goes to the settings area by pressing escape and clicking on the
settings button. They go to the game settings location and scroll to the
"Images" file. Within this location they can click on the file launcher which
will let them browse their system for a json configuration that has the keys and
locations for the images they would like to be used in the game.

8.  The user wants to start a game

The user opens the application and if everything doesn't catch fire they click
on "new game". Once clicking on new game a file explorer is opened that lets the
peruse their system and find their configuration. This configuration file is
passed to a model factory and creates a model. This model is passed to a
GameScene and sparks fly.

9.  The user wants to quit a game

If the user finally realizes that they have spent too much time trying to
recreate minecraft they can press escape to launch the global settings dialog
and then click on "Exit game" which will have them exit the game. :(.

10. The user wants to modify the appearance of this specific game.

The user can launch the global settings dialog and then click on settings and
then scroll to the appearance section. They can change things such as the
primary color, the secondary color, the background color (hopefully not to light
mode cuz that'd tick me off). Once the change is made, it is saved in the system
preferences as that for that specific game. Yessir auto saving and loading for
settings pretty dope, Java isn't that bad lol.

## Liam 

1. If player is already on top of a block, pressing the down key will activate a movement that is not falling.

    When handling event, check if player object is colliding with the top surface of another game object. If it is, continue with implementation of event handler.

2. Create instances of a predefined game object and place them at certain locations.

    Call `clone()` in the game object's class with the new locations.    

3. Set a specific game object to be the target all enemies attack.

    For all attacking game objects, set their attribute "attacking" in the config files to the target object.

4. A game object starts to move when player reaches certain area. 

    Set `onCollide` attribute to hitboc representing the area to a move-object action handler specifying the moving object.

5. A game object moves with a specified horizontal speed (alla Flappy Bird).

    Set horizontal speed attribute of game object in config file. 

6. Powerups modify a game object. 

    Create game objects in config files that are variations of the original game object. Replace the original with these variatins in-game on the screen when powerups are activated (e.g. star mario vs. regular mario).


7. Different images represent a game object at different times. For example, player looks different when falling than when walking.

    Set change-image action handler in config files referencing game object and image.

8. The player can "grab" certain game objects i.e. come into contact with them and have them move with the player when user holds a specified key.

    Add a `grabbable` attribute to game objects that can be grabbed. Make custom event handler for specified key.

9. Player has a limited amount of time to complete action. Otherwise, dies.

    Have a `timer` attribute in level config attribute that specifies time left before player dies.
    
10. Player slides on the ground.

    Change image of player object and momentarily change horizontal speed. Once player covers certain distance, reset image and speed.

## Tinglong 

1. Game dev writes a health system for the game and add it to the GameObject.
    
    The game will load the health points from the config file and set it for the GameObject.

2. Game dev writes an attack system for the game.

    The player can use keyboard to execute the attack to the enemies in the certain game.
   
3. User get close to the enemies.

    The enemy will attack the user in a fixed frequency and fixed damage, which will cause the loss of player's health.
   
4. The player use a weapon to attack.

    The weapon's health will decrease and the player will carry greater damage to the target.
   
5. The player kill all the enemies.

    The player will win the game and choose whether to get to the next level.
   
6. The player triggers a critical attack.

    The attack damage carried to the enemies would be x times comparing to the normal attack.

7. The player finished a certain action (collecing a crown) that can directly make him/her to win the game.

    The player win the game.
   
8. The player press jump and left-arrow key at the same time.

    The player would jump leftward.
   
9. The enemies are allowed to move in a random sequence.

    The enemies would change their time for executing each action after each cycle.
   
10. When the enemies detect the player's existence.

    The enemies would move towards the player to execute attack.