# Simulation Lab Discussion

## Cell Society

## Names and NetIDs
Robert Barnette rhb19
Kevin Yang kyy2
Felix Jiang fj32

### High Level Design Ideas
**Cell Class**: Make cell class basic, and all the logic of updating a cell into evolution. Has some sort of state indicator of how it should be represented. Perhaps it contains images of the shapes it should represent in each state

**Evolution Class**: handles the stepping through of the simulation given cells. If rules change for the simulation, it would occur here, and the cell class that determines its next state only calls an evolution object. 

**XML Reader Class**: reads an XML file and turns it into a Grid data structure. (Should it be able to read the rules of the simulation as well?)

**User Input Class**: gives a way for user input to change cells' parameters

**Display Class**: Given a layout of cells, this class displays the cells to the use. Is not responsible for updating the states - simply displays what it is given.

**Grid Class**: A class that encapsulates some sort of data structure for holding all the cells. other classes only care about the grid and its methods, not how it is implemented internally. Has an update method that is called to update the grid, which will call each cell's update method. 

### CRC Card Classes

This class's purpose or value is to manage something:
```java
public class Cell {
     public Image getStateImate()
     // returns the image to be displayed by the diplayer based on the cell's state
     
     public void update ()
     // updates this cell's state, implementation details are hidden by the caller. Perhaps it would call an evolution object
 }
```

This class's purpose or value is to organize the data from Something:
```java
public class XML_Reader {
    
     public Grid processFile (String filename)
    //reads file to create the initial grid of states 
 }

 public class Display {
    
     public void displayCells(Grid grid)
    // takes the grid of cells and displays them in an interesting way.
 }

  public class Evolution {
    
     public void update(Cell)
     // updates the cell's state, so it has access to the grid and has some rule for updating state. Other evolution classes can be created in order to change the rules, but they should all have the method update
 }

 public class Grid {
    
     public List<Cell> getNeighbors(Cell)
     // returns the neighbors of the cell

     public void update()
     // updates the state of the entire grid by one evolution
 }

 public class User_Input {
    
     public void displayParameters ()
    // shows sliders/other method of changing the parameters of the simulation
 }
```


### Use Cases

* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML file
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Switch simulations: use the GUI to change the current simulation from Game of Life to Wa-Tor
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```
