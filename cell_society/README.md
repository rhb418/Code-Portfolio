Cell Society
====

This project implements a cellular automata simulator.

Names: Robert Barnette, Felix Jiang, and Kevin Yang

### Timeline

Start Date: 2/7/21

Finish Date: 2/21/21

Hours Spent: 87 total (among us 3)

### Primary Roles
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
  

### Resources Used
https://www.stackoverflow.com
https://stackoverflow.com/questions/38216268/how-to-listen-resize-event-of-stage-in-javafx
https://www.tutorialspoint.com/java_xml/java_sax_parse_document.htm  
https://www.tutorialspoint.com/java_xml/java_stax_create_document.
https://www.tutorialspoint.com/how-to-wrap-the-text-within-the-width-of-the-window-in-javafx
https://docs.oracle.com/javafx/2/css_tutorial/jfxpub-css_tutorial.htm 

### Running the Program

Main class: Main 

Data files needed: The XML configuration files in the data folder are needed to run the simulation as well as the properties and CSS files present in the resources folder.  

Features implemented:

* Implemented seven different simulations including: Game of Life, Fire, Percolation, Wator, Segregation, Rock Paper Scissors Lizard Spock, and Sugar.
* Created distinct neighborhood determination algorithms that correspond to different shapes that could be displayed. This includes rectangular, close rectangular, hexagonal, and triangular neighborhoods. 
* Created different simulation modes including finite and toroidal where finite means that cells at the corners of the grid have smaller neighborhoods based on the cells directly around them and toroidal means that all cells have the same neighborhood size and cells in the sides and corners have neighborhoods blending to the other side of the screen. 
* Allows each simulation to be propagated infinitely with any possible shape and mode combination. 
* Allowed the user to click on cells during the simulation in order to change their functionality to that of a different cell present in the simulation. 
* The user can also update the mode and shape of the simulation while it is in progress 
* Provided visualization for current amounts of certain cell types using JavaFX piechart
  * User could see side by side the graph and grid for any simulation they wished
  * Random simulations would show accurate data between piechart and grid
  * Clicking on cells also showed accurate data between piechart and grid
  * Piechart and grid can always be independently displayed or displayed together
* User could open as many simulations and graphs as they wanted at the same time
  * Pause, restart, step, speed up were all implemented and linked for all open displays
  * Display specific changes did not affect other open displays
* User could dynamically change the grid frontend or backend
  * Different shape grid cells
  * Backend simulation parameters
  * Color of displayed
  * change cell color/state
* Implemented XMLReading/Writing functionality, including creating grid layouts from a data file and saving the current configuration or a random configuration for a certain game.
* Provided a way to create a random configuration of a certain game using the evolution game type that was previously run.
* Manages error handling, such as correcting invalid file input, insufficient input amounts, and invalid input data by automatically completing the input with randomly generated data or filling in invalid data points with valid base values, based on the game type.
* Provided language selection from dropdown menu on launch from the splash screen, including English, Mandarin, and Gibberish.
* Enabled window color styling through simulation options, including light, dark, and default versions.


### Notes/Assumptions

Assumptions or Simplifications:
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

When the user opens many displays, pause, restart, step, and speed change all open displays. We assumed that if the user would want to see many displays at the same time, then they would want to all have them be on the same "clock", so pause pauses everything, speed changes everything, and restart restars everything. If they wanted to pause one but not the other, then they can just use one display at a time or focus on the one they wish to see. Otherwise, if they had wanted to start all simulations at the same time, they would need to carefully press resume on all simulations at the same time, which will be difficult. 

The user would understand that resizing the window allows for the polygons to look equilateral because they always fit to the area they are given so sometimes they appear stretched
  * Resizing the window by dragging the size around also may move the cells above all the controls, because sometimes based on a variety of cell heights and widths, the grid covers the first row of buttons.

If the user missed a crucial point of a simulation they can always restart, pause, or step to their liking.

Interesting data files:  
Filenames that end in "Exception" test invalid inputs, and handles them accordingly.  
Filenames that end in "Demo" show certain aspects of the different simulations.  
Filenames that end in "Config" show grids created through random generation.  
Most configurations show an interesting situation in the game that they are made for, often giving edge cases for the simulation's limitations or very unnatural starting configurations.

Known Bugs:
When making changes to a file while the program is running, the file cannot be used as an input for a new simulation. This is fixed when the program is restarted. 

Visual bug: Some configurations force the grids to display over the top line of buttons, although a slight fix was introduced recently.



Extra credit:
1. User can design their own file and save it. Open up one of the XML files meant for level design (in the simulation the user would like), then pause the simulation and click around the cells to your liking, and save it to a name you would recognize and you can always run that simulation again. 
2. The simulation shape and edge type can be changed as the simulation is running and all configuration files can support any edge type or shape. 
3. Buttons for the user are organized by row, and their effect goes from all displays, current display, and potentially new display
4. User can alter pretty much any simulation parameter/display parameter directly on the screen in real time, rather than needing to read a new file in
  * For example, they can switch shapes, switch grid edge types, colors, graph, etc. as the simulation is running, without needing to open another XML file
5. User can use file chooser to select and save files, rather than typing out names themselves
6. The grid dynamically changes size based on the size of the window, and the user can drag it to their liking
7. A title and description is present to the viewer, not only in the XML file
8. Errors display on the screen (if user types bad input to simulation parameters, or if a file was not opened correctly) so user can understand what happened
9. New styles and languages can be easily added by just creating a single new file and changing one line of constants.java


### Impressions
Overall this assignment was quite fun to put together. The different simulations were all interesting to write and watching them propogate infinitely with different shapes and edge types was quite cool at times. 

Quite a bit harder working with a team on a project, but I'm very happy we were able to separate out the code early on, so each person really focused on their section and just needed a method header when trying to communicate between their sections. I liked how we organized the project during the first planning day to have an Evolution class, because it made front end very easy and all implementation details were hidden from the front. 60+ files are in the data folder and I don't know how a single one of them works or how the format is specified, but all I need to know is a method call and everything works.
