# API Lab Discussion
# SLogo API Design

## Names and NetIDs

Billy Luqiu (wyl6)
Bill Guo (wg78)
Robert Barnette (rhb19)
Tomas Esber (tee9)

### Planning Questions

 * What behaviors (methods) should the turtle have?
   It should be able to translate a number of units in a given direction, rotate to any angular positions, change its color, keep track of its position, and draw a line along its path of movement. 

 * When does parsing need to take place and what does it need to start properly?
 It needs to take place right after a user enters a command and must have robust error checking for garbage or null inputs. 

 * What is the result of parsing and who receives it?
 This result should be a command and a set of arguments that would be passed to the controller that would then be used to update the state of the turtle. 

 * When are errors detected and how are they reported?
 Errors should be detected by the parser and will print out the specific error on the front end. 

 * What do commands know, when do they know it, and how do they get it?
 The command should know what arguments it can take and then throw an exception if it is trying to be passed too many arguments when the user input is being parsed. 

 * How is the GUI updated after a command has completed execution?
 The GUI checks the state of the turtle and make any changes to its position, rotation, color, and path. 

 * What behaviors does the result of a command need to have to be used
 by the front end?
It just needs to know how the command changes the state of the turtle. 

### APIs
 
#### Backend External API
The backend external API allows for the front end to get various features regarding the current slogo.model state. The backend API should allow for the user to get information about the turtle such as the current location and state information such as if the turtle should be visible or if the pen is currently drawing anything or not. It will keep track of all these states regarding the turtle and return the current state when called. The backend will also keep track of all previous commands.

Terminal -> Parser -> Command -> Controller -> Turtle -> Frontend

public Turtle{

  public List<LineCoords> getDrawnLines()

  public double getPosition()
  public double getDirection()
  public Color getColor()
  public boolean isDrawn()
  
}

public LineCoords{
  public double getXStartPosition()
  public double getYStartPosition()
  public double getXEndPosition()
  public double getYEndPosition()
}

public Controller{
  public ImmutableList<Command> getPreviousCommands()
  public ImmutableList<String> getCurrentVariables()
}

public Command{
  public String getCommandString()
}





#### Frontend External API
The frontend external API will allow the users to pass in commands and then pass the inputted commands to the backend for execution. It also may allow the user adjust some parameters of the simulation which would then be passed to the backend to change. 

public UserInput{

  private Parser myParser;

    
}
public Display{
  public void updateDisplay(Turtle turtle){
    
  }

}

public Parser{

  public Command parseCommand(String command){
    
  }
  
}


#### Backend Internal API

The backend Internal API allows for the backend to communicate with each other regarding commands. For instance, the controller class would call the turtle to update the turtle based off the command, and the command class would have methods to return the state of the command (i.e. what the command should do). Each different type of command would require a different classes. Such classes will include commands that effect the turtle position, commands that just return a value, commands that are nested commands (for loops), and each type of command will have different attributes. 


public Controller{

  protected getCommand
}

public abstract Command{
  
}

#### Frontend Internal API

The frontend internal API should be able to return the current language of the program, as well as the attributes regarding the GUI such as colors/turtle image, and background image. 

public Display{

  public void setLanguage(String language)

  public void setTurtleColor(Color color)

  public void setBackground(String background)

    
}



### Design

#### Backend Design CRCs

public Turtle{

  Dependencies: LineCoords class
  Assumptions: Turtle objects will be created in the controller and their state will be update based on the parsed commands

  public List<LineCoords> getDrawnLines()

  public double getPosition()
  public double getDirection()
  public Color getColor()
  public boolean isDrawn()
  
}


public LineCoords{

  Dependencies: None
  
  public double getXStartPosition()
  public double getYStartPosition()
  public double getXEndPosition()
  public double getYEndPosition()
}

public Controller{

Dependencies: Turtle Class, Command Class

  public ImmutableList<Command> getPreviousCommands()
  public ImmutableList<String> getCurrentVariables()
  private void applyCommand(Turtle turtle, Command command)
  
}

public Command{

Dependencies: Parser?

  public String getCommandString()
}

#### Frontend Design CRCs



#### Use Cases

 * The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.

 * The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

 * The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).

 * The user changes the color of the environment's background.
