# Cell Society Design Final
### Names
Robert Barnette (rhb19)
Felix Jiang (fj32)
Kevin Yang (kyy2)

## Team Roles and Responsibilities

1. Felix: In charge of the visualization aspect of the project.
* Handle anything the user can see or interact with
    * Made sure changes to frontend were carried out and displayed, made those changes myself, and then called backend to update the chanegs on the back
* Communicate with Evolution and XMLReader classes in order to accomplish desires of the user
2. Robert: In charge of the simulation aspect of the project.
* Implemented the logic for all seven simulations which includes all the code in the Evolution class and its subclasses
* Wrote the Grid class in its entirety including all of the neighbor determination methods for the different shapes and modes of the simulation.
3. Kevin: In charge of the configuration aspect of the project.
* Implemented XML SAX parser to read from XML files and StAX parser to write current/random configurations back into files.  Also implemented parser to read visual styling data, but unused as these elements are more efficiently handled through the visualization portion.
* Managed error handling/debugging, correct invalid file or value inputs to prevent program from crashing.


## Design goals
Easy to add new evolutions (simulation types), XML files and format, and drawers for the simulation (shape of cell or graph).

#### What Features are Easy to Add
1. New Evolutions
    * All evolutions inherit from the Evolution abstract class. A new type of simulation can be easily created by inheriting the Evolution class and creating new logic for how each cell updates itself
2. New XML files and file format
    * The XML format is very simple and straightforward, so creating new XML tags or config files is intuitive and makes sense without understanding much of *how* it works.
3. New ways to display the simulation
    * Because Drawer was an interface, if we wanted to add a new type of drawer (way the cells are displayed), we would only need to create a new class that implements the single method of the Drawer intereface. We wouldn't need to make any other changes afterwards in order to see a new displayer


## High-level Design
The XMLReaderWriter class handles the parsing of XML files and uses this information to create Grid and then Evolution objects dependent on the type of simulation the file implements. The XMLReaderWriter class also handles errors and invalid entries from input files, preventing the program from crashing by always giving the visualization a usable simulation. This class also handles writing the current configuration data to a file.

The Grid class stores the cells present in the simulation and contains neighbor finding methods to determine different types of neighbors dependent on the shape and edge type of the simulation. The Evolution classes implement the logic of different simulations and all extend the Evolution superclass taking advantage of shared behaviors.

Visualizer class controls all of the open windows the user sees, while the Displayer class represents each window. UserInputDisplay handles all of the actions a user wants to do in order to change what they see. the UserInputDisplay class communicates to the Displayer or Visualizer classes, which then may or may not call backend methods in order to acheive what the user wants.

#### Core Classes
1. Evolution
    * Main class for anything that is a simulation type, such as WaTor or Percolation
2. Displayer
    * Represents one window that is open, and can easily switch out way that cells are displayed by changing the runtime type of the Drawer interface
3. XMLReaderWriter
    * Tells how the program can read through input files and take the data from the file to use for creating an Evolution object

## Assumptions that Affect the Design
Since the language implementation uses resource packs, this was handled in with the visualization code.  This is because the visualization handles user input, where the user is able to select which language they want when launching the program.

These portions of the code are handled within the Visualization code, rather than the Configuration code:
* Language
* Cell grid type
* Cell colors
* Enabling/Disabling cell grid
* Window/Text styling

The grid type is handled through user input directly on the simulation window. We decided that this was much better than getting the grid type from a file due to the change being in real-time with user input. Little time is taken to select a new grid type from the drop-down menu, and restarting the simulation is recommended after changing grids anyway.

Window and text styling is done through external CSS files, which are not created by reading the styling parameters from XML files.  This consolidates the data in a single place that is more accessible to the visualization code.

Language handling is also done through the resource packs, which is managed by the visualization code.  Enabling the simulation grid between cells is also handled in the visualization code, as a single button is used to make this change, and is more accessible to the user than an input file.

The code in the StyleReader class is completely functional to read an XML file with styling data, but is unused because of this data being handled by the visualization.

Most of the simulations are deterministic; however, some simulations such as Segregation, Wator, and Sugar have random elements. In Segregation unhappy cells are moved to a cell randomly selected from a list of empty cells. In order to prevent unhappy cells from moving to the same empty cell, this list is updated after every cell is moved. In Wator, sharks move first as this prevents a fish that should have been eaten by a shark from both moving away and being eaten. Sharks eat a random fish around them or move to a random square around them if there are no fish. Likewise fish also move in a random direction if they are able to move every turn. In sugar the agent is randomly assigned a vision, sugar amount, and sugar metabolism at the beginning of the game. The agents also move towards the closest most sugar-rich location in their vision each turn, but if there is a tie they move in a random direction among the tied locations.

While all simulations can use every shape type, some shapes may cause the simulation rules to be bent. For instance if the user uses triangles for the fire simulation then all twelve neighbors of a flaming triangle with have probability probcatch of catching on fire, not just the three triangles directly next to the burning triangle.

The user would be able to understand why each button's effect was designed the way it was after playing around with our program for a bit.
* All controls on the top row affect any window that is open
* All controls in the middle row affect only the current window (mostly for the grid but changing threshold values will also show up in the piechart)
* All controls on the bottom row relate to adding new windows or simulations (on the same window)

#### Features Affected by Assumptions

Resizing shape: The user would understand that resizing the window allows for the polygons to look equilateral because they always fit to the area they are given so sometimes they appear stretched
* Resizing the window by dragging the size around also may move the cells above all the controls, because sometimes based on a variety of cell heights and widths, the grid covers the first row of buttons.

Play/Pause speed affects all displays: When the user opens many displays, pause, restart, step, and speed change all open displays. We assumed that if the user would want to see many displays at the same time, then they would want to all have them be on the same "clock", so pause pauses everything, speed changes everything, and restart restars everything. If they wanted to pause one but not the other, then they can just use one display at a time or focus on the one they wish to see. Otherwise, if they had wanted to start all simulations at the same time, they would need to carefully press resume on all simulations at the same time, which will be difficult.

Can change shape directly during the simulation, without needing to load a new file

The user would not need to change the language mid-simulation, so the language selection happens once at the start and can not be changed unless the program restarts.

Assumed we did not need a reference to the XMLReaderWriter in the front end, and this required us making calls to the Main class, when we could have had a reference to the reader writer directly in the Visualizer class.


## Significant differences from Original Plan
1. User Input Display class does not communicate with an evolution or a cell
    * Those implementation details are hidden, and all the User Input Display does is communicate to the displayer and visualizer that the user wishes to change something
2. Cell shapes are handled through the grid type, rather than being able to change the cell shape independently from the grid type
3. Did not need to use images for each cell, as the visualization of different colored cells was more than enough to give us a clear sense of how the simulation was changing


## New Features HowTo

#### Easy to Add Features
1. New Evolutions
    * All evolutions inherit from the Evolution abstract class. A new type of simulation can be easily created by inheriting the Evolution class and creating new logic for how each cell updates itself
2. New XML files and file format
    * The XML format is very simple and straightforward, so creating new XML tags or config files is intuitive and makes sense without understanding much of *how* it works.
3. New ways to display the simulation
    * Because Drawer was an interface, if we wanted to add a new type of drawer (way the cells are displayed), we would only need to create a new class that implements the single method of the Drawer intereface. We wouldn't need to make any other changes afterwards in order to see a new displayer

#### Other Features not yet Done
1. The infinite edge type was not implemented in our project as it would have required a significant redesign of the Grid class and neighbor determination methods.
2. Writing a random configuration of any game type by specifically pressing a button on the program window and selecting a game type from a dropdown menu
3. Separate Error Window
    * Currently, if the user does something that results in an error, they will see a message that is displayed on the title part of the window they are on. It may be better to have a separate window that displays all of the errors, so there is a history of them and it does not impede the user if they do not care about the error

