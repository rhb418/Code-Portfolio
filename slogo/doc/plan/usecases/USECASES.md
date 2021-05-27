* The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.
```java
String command = frontEndController.getNextCommand();
Command command = myParser.parseCommand(command);

if(myParser.throwsError() != null){
  myConsole.add(myParser.throwsError())
}

```
* The user types in COS 50 in the command menu and sees the correct output on the window
```java
String command = frontEndController.getNextCommand();
myParser.parseCommand(command);

Command command =  myParser.getCommand(); 
command.executeCommand(turtle);

backendcontroller.add(command.getResult())
```

* The user types in a correct command but one that has an incorrect number of arguments
```java
String command = frontEndController.getNextCommand();
Command command = myParser.parseCommand(command);

if(myParser.throwsError() != null){
  myConsole.add(myParser.throwsError())
}
```


* The user types in XCOR

```java
String command = frontEndController.getNextCommand();
myParser.parseCommand(command);

Command command =  myParser.getCommand(); 
command.executeCommand(turtle);

backEndController.add(command.getResult())

``` 

* The user sets a variable's value and sees it updated in the UI's Variable view.

```java
String command = frontEndController.getNextCommand();
myParser.parseCommand(command);
slogo.model.storeVariable(name, value);
backEndController.getCurrentVariables();
myDisplay.updateAllViews();
myVariableDisplay.showVariablesDefined(variableMap);
```

* The user wants to see user defined functions

```java
myConsoleDisplay.showPreviousFunctionsDefined(functionMap);
```

* Change Background Color
```java
turtleDisplay.setBackgroundColor(color);
```

* Create a user defined command and display it
```java

String command = frontEndController.getNextCommand();
myParser.parseCommand(command);
slogo.model.storeUserDefinedCommand(command.getCommandAsString())
backEndController.getPreviousCommands();
myDisplay.updateAllViews();
myConsoleDisplay.updateCommandHistory(command);

```


* The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.
```java
String command = frontEndController.getNextCommand();
Command command = myParser.parseCommand(command);

if(myParser.throwsError() != null){
  myConsole.add(myParser.throwsError())
}

```
* The user types in COS 50 in the command menu and sees the correct output on the window
```java
String command = frontEndController.getNextCommand();
myParser.parseCommand(command);

Command command =  myParser.getCommand(); 
command.executeCommand(turtle);

backendcontroller.add(command.getResult())
```

* The user types in a correct command but one that has an incorrect number of arguments
```java
String command = frontEndController.getNextCommand();
Command command = myParser.parseCommand(command);

if(myParser.throwsError() != null){
  myConsole.add(myParser.throwsError())
}
```


* The user types in XCOR

```java
String command = frontEndController.getNextCommand();
myParser.parseCommand(command);

Command command =  myParser.getCommand(); 
command.executeCommand(turtle);

backendcontroller.add(command.getResult())

``` 

* User requests list of previous commmands
```java
  consoleDisplay.showPreviousCommmands()

  controller.getPreviousCommands();

``` 
* Change Languages

```java
  DisplayAttributes.setLanguage();
  parser.setLanguage();
  frontEndController.getLanguage();

``` 

* Change Pen Color
```java

  displayAttributes.setPenColor();
  turtleDisplay.setLineColor();

``` 
* Change Turtle Image
```java

  displayAttributes.setImageOfTurtle();
  turtledisplay.setImageOfTurtle();

``` 

* The user types in 'repeat 4 [fd 50 lt 90]' for the Parser to handle
```java

String cmd = frontEndController.getNextCommand();
myParser.parseCommand(cmd); 
//should treat command as SuperCommand

```

* The user wants to determine what error caused the parser to fail
```java
String command = frontEndController.getNextCommand();
myParser.parseCommand(command);
myParser.getError();
```

* The user wants to parse through an Instruction
```java
  Instruction instruction = new Instruction("fd 50");
  Command command = myParser.parseCommand(instruction);
  //should treat command as Instruction type
```



