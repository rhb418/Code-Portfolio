# TODO
### Bill Guo (sorry its late)

## Toolbar
Basic structure is that Clickables, which implement an interface, are used to make the 
controls in the toolbar. These Clickables are known by the frontend Mediator, which 
is an implementation of an interface that handles all the communication between components of 
the UI. Main flows of communication are from the Clickables to TurtleDisplay, ConsoleDisplay, 
and other displays like the ColorPalette and ShapePalette

### Create each Button and MenuButton clickable
#### ButtonClickables
```LoadPreferencesButton``` should load a new preference file and change each display on the view accordingly
   * Needs info from: LoadPreferenceButton - the button should store the new preferences and 
     have the appropriate getters so the Mediator can access them. 
   * Updates: TurtleDisplay, FrontEndController, ColorPalette, ShapePalette, CodeEntryBox  
   * Preferences file should be a properties file with the following info:
     * background color - TurtleDisplay
     * language - FrontEndController
     * shape palette: list of file names of shape images - ShapePalette
     * file of code to send instantly to parser - CodeEntryBox 
  
```SavePreferencesButton``` should write a new properties file with the following info
   * Needs info from: TurtleDisplay, Workspace, ColorPalette, ShapePalette, FrontEndController
     * current background color - TurtleDisplay
     * current language - Workspace
     * current color palette - ColorPalette
     * current shape palette - ShapePalette
     * all code that has been run to this point - FrontEndController
  
```HelpButton``` should bring up a separate window that describes all the commands
  * The functionality of this is essentially done in the Toolbar class, it does not need to be 
    put in the Mediator
    
```UndoButton``` should load the states before the last command was submitted
  * All this information should be from the BackEndController
  * Needs info from: BackEndController
  * Updates: TurtleDisplay, TurtleStateList, UserDefinedCommands, UserVariables
  

#### MenuButtonClickables
```slogo.view.component.clickable.menubutton.TurtleMenuButton``` should run commands from the press of an item in a MenuButton
  * The list of commands should be from a Properties file with keys of
    * MoveForward, MoveBackward, RightTurn, LeftTurn
  * and values of the actual commands sent to the parser (look at current resources folder for 
    an example of how it could be done, if you have a better idea feel free to shoot me a message)
  * Needs info from: slogo.view.component.clickable.menubutton.TurtleMenuButton
  * Updates: TurtleDisplay, TurtleStateList

```PenMenuButton``` should run commands from the press of an item in a MenuButton
  * The list of commands should be from a Properties file with keys of
    * PenUp, PenDown, PenWidthUp, PenWidthDown
  * and values of the actual commands sent to the parser
  * Needs info from: PenMenuButton, BackendController (for current pen size)
  * Updates: TurtleDisplay 

```BackgroundColorMenuButton``` should change the color of the background
  * Pretty easy, maybe start with this first
  * Needs info from: BackgroundColorMenuButton
  * Updates: TurtleDisplay

```LineColorMenuButton``` should change the line color drawn by currently active turtles
  * The list of colors is from a resource file where the keys are the labels in the MenuButton 
    and the values are the hex codes for the colors
  * Needs info from: LineColorMenuButton
  * Updates: TurtleDisplay 

```TurtleShapeMenuButton``` changes the shape of active turtle 
  * The list of shapes is from a resource file where the keys are the shape names and the 
    values are the file path to the image, path should start with slogo I think
  * Needs info from: TurtleShapeMenuButton
  * Updates: TurtleDisplay

```LanguageMenuButton``` changes the language of the commands (and maybe labels if we finish early)
  * All it has to do is send a string to the FrontEndController of the chosen language, the 
    backend should handle the rest
  * Supported languages should be read from a resource file
  * Needs info from: LanguageMenuButton
  * Updates: FrontEndController