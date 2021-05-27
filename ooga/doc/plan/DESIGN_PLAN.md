# DESIGN_PLAN.md

## Introduction

* This section describes the problem your team is trying to solve by writing this program, the primary design goals of the project
(i.e., where is it most flexible), and the primary architecture of the design (i.e., what is closed and what is open). Discuss
the design at a high-level (i.e., without referencing specific classes, data structures, or code).

* The primary goal of this product is to design a game architecture for 2D scroller games that allows for a great deal of flexibility in game design. In order to accomplish this, the project will be as data driven as possible allowing users to create files containing information about levels, game objects, camera positioning and more. Additionally game objects will be designed as generally and flexibly as possible, allowing them to be injected with components altering their functionailty. This program will thus implement a large number of features but provide users with a great deal of control over how their games will be structured via modification of configuration files.

## Overview


***

*   The model will detect collisions between game objects
*   GameObjects will have listeners placed on them that the view can update based off of
*   KeyInput events will be passed to the controller and the controller will retrieve a string that maps to that event's keycode.
    For example, if the user presses "UP", the controller will retrieve what string (function) name that maps to "UP". This command name will map to a callback function that is put in the controller from the model. The controller will call this callback.
*   Each model will have a controller that will be retrieved from the view. Probably a `getController()` method.
*   GameObjects should be able to register their own key event actions. For example:

```json
interface GameObject {
    type: "character",
    ...
    input: [
        {
            key: "A",
            method: "fireBullet"
        }
    ]
}
```

So then when creating this gameobject it will register
the function onto the viewcontroller that will call its
"fireBullet" method everytime the 'A' is pressed.

## Design Details

Gameobjects:

Game objects include any and every thing that is placed within the game.
Everything from players, enemies, obstacles, platforms... all of these are game
objects.

Each gameobject also implements an interface called "ObservableObject", this
interface allows for the view to track evrything concerning this object such as
when its image updates, its position, visibility, and any thing view related
such as that. This is done through an event based observer pattern where the
view sets listeners on objects that call callbacks when an event occurs.

Model:

The model's basic role is to detect collisions between game objects. When a
collision occurs it will notify each of the objects that collided with one
another and pass a reference of those objects to each other.

Handlers:

Handlers will be classes that perform certain actions on the game as a whole
(change the level, set the background...) and/or specific objects (damage a game
object, give a player flying abilities...). Instead of coding abilities into the
gameobject class, we will create handlers and pass them to the classes using a
string that will be parsed within the configuration file.

A collision handler for example will be triggered from with a GameObject.
Everytime an object collides with another it's passed a reference and then
checks to see if it contains a handler regarding actions that occured with
an ID (kind of like type but multiple) of that collided `GameObject`.

```java
// Within GameObject
public void collided(GameObject colObj) {
		Set<String> colObj.getIDs();
    for (String key : colObj.keySet()) {
			collisionMap.computeIfPresent(key, (id, handler) -> {
					handler.handleCollision(obj2);
			});
    }
}
```

Controller:

The controller will facillitate all communication between the view and the
model. For example, when the user presses a button the controller will receive
the string of the button pressed and then translate that into something the
model can use. The controller will also give the view the ability to map certain
inputs to different functions

For example, the controllerw will allow the view to set which string triggers
the walk forward functionality within the model. This will allow for another
layer of configuration as the user can set what triggers what within the model.

View:

ObjectView:

The objectview takes in an observable object. It tracks this object and updates
needed fields as it goes along.

Styling:

The view is going to have predefined stylesheets that can be modified using CSS
lookup colors (pretty cool didn't think javafx css would support something like
this). This will allow the look and feel of the entire view to be customized.
But to be honest people should be content with the (default) dark mode theme.

Components will be separated into different classes. For example, the settings
/pause popup will be its own class and have bindings to update settings. The
observable objects class will be used

The view will not communicate *to* the model directly. All it will do is observe
changes that happen, but if it needs to tell the model something it will do it
through the controller. This way we can add more logic to actions which will
make them more extensible.

***

### Data Files

Our data files will be structured in a way so that gameObjects and levels have "presets", established in the config.json and gameobjects.json. A level may choose to override any of these presets to make their own version of an object that does what is needed.

### "onCollide" property

The "onCollide" property specifies the object with which the parent object is expected to collide with,
as well as an *action* and *payload*.

#### gameobjects.json

The "objects" property declares all the types of game objects that are
expected to be created for the game. These types can be overriden.

#### level2.json

An example of a level configuration file. The "objects" property declares the objects that are expected
to be declared in this level. Each object must be of a type declared in gameobjects.json and has its location in the level
layout specified.

#### config.json

Specifies game information, such as name, levels, and objects. Properties in "levels" correspond to
level data files, and properties in "objects" correspond to data files containing game object types.

## Design Considerations

*This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution.
Include any design decisions discussed at length (include pros and cons from all sides of the discussion) as well as any ambiguities,
assumptions, or dependencies regarding the program that impact the overall design.*

***

*   class for every single object type.
*   all updates go through controller. Instead of view looking at model, view asking controller for updates.
*   actions are strings representing methods (reflection or lambdas)

We are going to implement a component-based system (like in the Unity and Godot game engines). The potential issues with doing so:

*   We need to write a parser to parse string to a Reference class
*   Might overcomplicate things for simple levels

## Example games

*Describe three example games that differ significantly in detail. Clearly identify how the functional differences in these games is
supported by your design. Use these examples to help clarify the abstractions in your design.*

### Mario

By creating floor objects that the player can stand on a value for gravity, we can create a platforming game that can run like mario. This will be supported by our design because we will set the player inputs to add horizontal velocity to the left or right with the arrow keys and jump with the spacebar. We will create enemies that will also stand on the platform and contain a camera that moves in one direction, regardless of the player position.

### Space Invaders

By creating a level with a space background and setting the gravity to zero, the player's movement can completely depend on player input. The inputs for the player will be
the arrow keys still, but the inputs will be left and right to move that direction, but not add velocity to that direction. The space bar will fire projectiles at enemies. The camera will be fixed and the player will be bound to the area

### Metroid

This game will be a much simpler version of metroid, but the game will show that our design can handle different collision types. The player will be able to pick up different items and use them. The player will be able to move around freely and the camera will follow them. This will play with different win conditions and player state. We can mess with gravity here and have enemies that are unaffected by gravity. We can play with moving to and from levels while maintaining player state.

## Test Plan

We are going to apply the following strategies to make our testing much easier:

*   Smaller classes: Instead of using huge classes, small classes can help us locate buggy code in fewer times. (Also reduce the time for scrolling up and down).
*   Throwing detailed exceptions: throwing detailed exceptions can help us to accurately locate the buggy class without speculation.

3 Scenarios:

*   Error producing:
    *   Model clipping -> Collision test:
        *   Test the case when player moves the characters (with relatively large speed), whether the player will pass through/collides the obstacles.
        *   How: check whether the speed has been changed in the testing process.
    *   Use the items (Metroid) that has been used up/blocked items:
        *   Test whether the player can use the items that has been used up/blocked items.
        *   How: determine whether everything remains the same.
    *   Space Invaders: Camera Shifting:
        *   Test the case that the player keep moving to indirection.
        *   How: keep tracking the position of the camera and the position of the player, make sure that their relative positions remain unchanged.
    *   Mario: keep jumping:
        *   Test keep pressing spacebar in the mario game:
        *   How: make sure mario only jump 1/2 times instead of keep jumping while he was off the ground.

*   UI testing:
    *   Text testing:
        *   Press different buttons in the dialog box to see whether it will generate different response.
        *   How: check the string in the current dialog box to see whether it matches the expected one.
    *   Character display:
        *   Test whether Mario is always been displayed in the screen field.
        *   How: For instance, while keep moving Mario, place a button on Mario (set the button the same position as Mario's, and the button's layer should below the Mario's to make sure that the button is invisible) try to click that button, if it fails, then Mario is not on the screen at that moment.
    *   Button test:
        *   Test: click certain buttons to see the results:
        *   How: for instance, whether clicking "next level" will make the game switch to the next level
    *   Entities test:
        *   Test whether a entity disappear after it finished its "mission":
        *   How: check the pixel's RGB at certain position.

*   Model & Action testing & Contoll:
    *   Multi key strokes at the same time:
        *   Even though the player press the invalid key at the moment, the game should not abort.
        *   How: check whether the game run properly after multiple (invalid) keystrokes (maybe multi key strokes at the same time).
    *   Config File reading:
        *   No corruptions during the reading process.
        *   How: Detect whether the game has corrupted during the invalid file reading process.
    *   Level completion:
        *   Check whether the player is asked to play the next level after he complete the current ones.
        *   How: Check notification (text box) and try pressing "next level"
    *   Multi buffs:
        *   Detect whether the model acts correctly when the player gets multiple buffs in a single frame.
        *   How: Check whether the state of the player is expected after he/she gets these buffs.
