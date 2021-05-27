# OOGA Plan Discussion

## Names

Oliver Rodas

Tinglong is the main character

### Project goals

*   Side scrolling ENGINE

Things that are (easily) changed:

Obstacles:

*   Falling
*   Health depletion obstacles
*

Objects:

*   CPU controlled objects

Players:

*   Specify # of players
*   Assign contraols to anything on screen and control that thing
*   Become what you kill
*   Collect characters that you can switch to. They are your lives
*   Certain characters force you to switch to them, so you don't want to kill them

```java=
interface GameObject {
  setImage(String url)
}

```

```json=
cameraConfig: {
  followPlayer: boolean;
  
}
```

```json=
"1" : {
    image: "thing.jpg"
   type: "chacater",
   name: "CharacterName",
   actions: [
   
   ],
   action: "shooter",
   collidble: boolean,
   onCollide: [
       {
       with: "player",
       action: "damage",
       payload: 
       }
   ],
   onDeath: ""
   locations: [
     {
       level: "2",
       x: 2149,
       y: 10,
     }
   ]   
},
{
    type: "character",
    ...
    onCollide: [
        {
         with: "enemy",
         location: "south",
         action: "jump"
        }
       {
        with: "any",
        location: "south",
        action: "st"
       }
    ]
},
"dialog_box2" : {
    type: "hitbox",
    id: 5,
    ...
    onCollide: [
        {
         with: "player",
         location: "any",
         action: "trigger_di"
         payload: ID_OF_DIALOG
        },
    ]
},
{
    type: "enemy",
    ...
    onCollide: [
     with: "character",
     location: "top",
     action: "decrement_self"
    ]
}

```

```json=
{
    dialog: "Bowser: Mario I love you",
    dialogID: id,
    level,
    responses: {
        ...
    },
    chosenResponse: response,
    dependsOn: {
        dialogID: id,
        dialogResponse: response
    }

}
```

Dialog stuff can be triggered through a Hit box. You can specify the
X and Y so that it can be triggered at a specific part.

Scale images to certain size so the model can

Notes:

Need to specify defaults for all of the values.

Modifiers:

*   Powerups:
    *   Any

*   Antipowerups (powerdowns lol)

*   currency

*   Things can't go through, collisions

Camera Movements:

*   Camera follows player
*   Player has to keep up with camera
*   Camera Doesnt Move

Triggers:

*   When you hit certain % of
*   Player Health. Something that happens when health reaches percent

Trigger Actions:

*   What happens when a trigger happens

### Project Emphasis

### Project Extensions

### Project Progress

#### Sprint 1 (Test)

#### Sprint 2 (Basic)

#### Sprint 3 (Complete)

# Random cool ideas

*   Custom Variables
    For example, the user could specify in game configurable
    options with types. Like this:

```json=
runtimeConfiguration: {
   cameraSpeed: "number",
}

cameraConfiguration : {
   speed: "runtime.cameraSpeed"
}
```

So the user can hardcode values or allow them to be modified
in game. Then again this would be a little difficult.
