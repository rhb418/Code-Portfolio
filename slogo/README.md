SLogo
====

This project implements a development environment that helps users write programs to draw using a turtle.

Names:
Robert Barnette (rhb19)
Billy Luqiu (wyl6)
Tomas Esber (tee9)
Bill Guo (wg78)

### Timeline

Start Date: 02/25/21

Finish Date: 03/21/21

Hours Spent:
Billy - 40-50 Hours
Tomas - 40-50 Hours
Robert - 40-50 Hours
Bill - 35 Hours

### Primary Roles

Bill: Front end for both basic and complete implementation. In charge of final front end design, including the layout, file structure, and class hierarchies such as the concept of components, clickables, menubuttons, subviews, observers, observables, mediators, and the factory pattern. Did much of the implementation for all mediators and subviews.

Billy: Front end in basic implementation, backend parser throughout project, debugging. Specifically, I wrote and designed all the classes in the Parser package, except for small portions of Parser2.java, and I worked on the intial grid and console views for Basic implementation (though they have gotten massively revamped by Bill).

Robert: Worked on the backend for the entirety of the project. Specifically I designed and implemented the command and supercommand abstract classes as well as all of their subclasses. I also created the commandexecutor class as well of the turtle class which worked to execute lists of command objects that were passed by the parser.

Tomas: For basic worked on the backend, focusing solely on the parser. Much of my work was refactored and added on to by Billy during the second week (hence Parser2). During the second week, I moved to front-end, helping Bill with ButtonClickables, MenuButtons, and Mediators.

### Resources Used
* A few inline citations dictate where small snippits of code were copied from the internet.

### Running the Program

Main class: Main.java

Data files needed: All files in resources package.

Features implemented:
Turtles can execute all commands given by the design specifications with support for multiple turtles. Commands can be in any legal order and the commandexecutor will evaluate them correctly. Divide by 0, nonreal answer, and not enough arguments errors are also handled by the commandexecutor which restores the state of all turtles prior to the execution of the failed command.

Parser handles all nested commands and multiple turtle commands, and grouping commands as well (with assumptions noted below).

Front end has buttons to save preferences, load preferences, directing move the turle, directly affect the pen, change a turtle's line color, change the background color, change the turtle's image, show history of successful commands, a help menu, and changing the language that is parsed. Additional workspaces can be made in the tabs on the top. Views include the turtle, a list of states, two palettes for colors and shapes, both user commands and user variables that can directly send commands pertaining to their information, a code entry box and a console entry box. All views can be hidden with open and close buttons.


### Notes/Assumptions

Assumptions or Simplifications:
* Loadpreferences.properties must be in the resources directory in order for a properties file to be loaded in
* Color palette and background color are not loaded in through the preferences file as they can be configured in the block of code which is run
* The preferences file must be in the format of the SavePreferences.properties file
* Grouping only works when all parameters are numerical/variable/or another grouping command
* Tell is the only command that can create additional turtles. If ask is called on turtles that have not been created then the commands only applies to turtles that have been created.
* Our project starts with a single turtle present at the center of the screen with and ID of 0. IDs are represented by non-negative integers and are assigned in increasing order as turtles are created.
* Commands that are not turtle commands or are affected by turtle queries only run once if there are multiple active turtles. For instance fd random 50 moves all active turtles the same random distance.
* A heading of 0 has the turtle pointing to the right of the screen and the angle advances similar to a unit circle.


Interesting data files:


Known Bugs:
* When turtles or lines are calculated to be off the screen, the whole turtle and line is not shown even if part of the image may still be on the screen
* Viewing to commands does not preserve the new line spaces so it is hard to read, however it is easy to see the parameters
*

Extra credit:
* Implemented additional backend feature of grouping

### Impressions
This project was very difficult and required multiple all nighters from all team members. The parser and command executor required a ton of thought into implementation details. Designing complete features required careful thought into API design.
