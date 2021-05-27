# API Lab Discussion
# Cell Society API Discussion

## Names and NetIDs
Billy Luqiu (wyl6)
Bill Guo (wg78)
Robert Barnette (rhb19)
Tomas Esber (tee9)

### Controller API Motivation/Analogies 
provide an API to access the controller
 

#### External
  The controller was responsible for updating the simulation state from the slogo.model and providing the view with an interface in which to access the simulation state.

#### Internal

  The internal API didn't have much functionality, but it did need a method to access the old grid so each individual controller could access the old grid state.


### Controller API Classes/Methods

#### External
public Controller() 
  public void setInitialGrid(Grid oldGrid) 
  public Controller(Grid oldGrid) 
  public void resetController() 
  public boolean simulationEnded() 
  public Grid getNewGrid() 

public class WatorController extends Controller
public class GameOfLifeController extends Controller 
public class FireController extends Controller
public class SegregationController extends Controller
public class PercolationController extends Controller


#### Internal

 protected Grid getOldGrid()