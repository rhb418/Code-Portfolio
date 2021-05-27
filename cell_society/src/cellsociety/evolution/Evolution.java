package cellsociety.evolution;


import static cellsociety.Constants.FINITE;
import static cellsociety.Constants.RECTANGULAR;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Robert Barnette This class is an abstract class for the different simulations. It
 * implements some methods that are used by all simulations while some are implemented within the
 * individual simulation classes extending evolution. This class depends on the grid class for its
 * implementation as well as the constants class for some values.
 */
public abstract class Evolution {

  private Grid myCurrentGeneration;
  private Grid myInitialGrid;
  private String title;
  private String description;
  private int myEdgeType;
  private int myShape;
  private HashMap<Integer, Integer> myCellMap;
  private List<Integer> myCellValues;

  /**
   * @param grid is the starting point for the simulation. This constructor makes an evolution
   *             object when given a grid.
   */
  public Evolution(Grid grid) {
    myEdgeType = FINITE;
    myShape = RECTANGULAR;
    myCurrentGeneration = grid;
    myInitialGrid = copyGrid(grid);
    myCellMap = new HashMap<>();
    myCellValues = getCellValues();
    updateCellMap();
  }


  /**
   * @returns a grid representing the new generation of cells This method creates a new grid and
   * then updates each individual cell in the old grid and adds them to the new grid thereby
   * creating a new generation of cell.
   */
  public Grid createNewGeneration() {
    Grid newGen = new Grid(myCurrentGeneration.getWidth(), myCurrentGeneration.getHeight());

    for (int k = 0; k < myCurrentGeneration.getHeight(); k++) {
      for (int j = 0; j < myCurrentGeneration.getWidth(); j++) {
        Cell cell = myCurrentGeneration.getCell(j, k);
        updateCell(cell, newGen);
      }
    }

    myCurrentGeneration = newGen;
    updateCellMap();
    return newGen;
  }

  /**
   * @param cell   is the cell being updated
   * @param newGen is the grid where the new cell is being added This method is implemented in the
   *               subclasses of evolution to implement the specific rules of the simulation
   */
  public void updateCell(Cell cell, Grid newGen) {
    return;
  }


  /**
   * @returns a map of simulation parameter names to doubles representing the simulation parameters
   * This method returns a map containing the simulation parameters, but is overridden in
   * simulations with 1 or more parameters
   */
  public Map<String, Double> getSimulationParameters() {
    Map<String, Double> ret = new HashMap<>();
    return ret;
  }


  /**
   * @param x is the x coordinate of the cell being switched
   * @param y is the y coordinate of the cell being switched This method switches a given cell to a
   *          different type when the user clicks it
   */
  public void switchCellOnClick(int x, int y) {
    List<Integer> exclusiveCellVals = new ArrayList<>(new HashSet<>(myCellValues));
    Cell switchCell = myCurrentGeneration.getCell(x, y);

    int index = exclusiveCellVals.indexOf(switchCell.getValue());
    int newIndex = index + 1;

    if (newIndex >= exclusiveCellVals.size()) {
      newIndex = 0;
    }
    switchCell.setValue(exclusiveCellVals.get(newIndex));
    updateCellMap();
  }


  /**
   * This updates the cell map which is a map of cell type (integer) to the number of cells present
   * in the simulation
   */
  public void updateCellMap() {
    myCellMap.clear();
    for (int k = 0; k < myCurrentGeneration.getHeight(); k++) {
      for (int j = 0; j < myCurrentGeneration.getWidth(); j++) {
        int val = myCurrentGeneration.getCell(j, k).getValue();
        myCellMap.putIfAbsent(val, 0);
        myCellMap.put(val, myCellMap.get(val) + 1);
      }
    }
  }


  /**
   * @param grid is the grid being copied
   * @returns a copy of an input grid This method returns a copy of a given grid
   */
  public Grid copyGrid(Grid grid) {
    Grid ret = new Grid(grid.getWidth(), grid.getHeight(), null);
    for (int k = 0; k < grid.getHeight(); k++) {
      for (int j = 0; j < grid.getWidth(); j++) {
        Cell cell = new Cell(grid.getCell(j, k).getValue(), j, k);
        ret.setCell(j, k, cell);
      }
    }
    return ret;
  }

  /**
   * @returns a list of all the values of the cells present in the simulation This method gives a
   * list of cell values present in the grid
   */
  public List<Integer> getCellValues() {
    List<Integer> cellVals = new ArrayList<>();
    for (int k = 0; k < myCurrentGeneration.getHeight(); k++) {
      for (int j = 0; j < myCurrentGeneration.getWidth(); j++) {
        cellVals.add(myCurrentGeneration.getCell(j, k).getValue());
      }

    }
    return cellVals;
  }

  /**
   * Resets the grid to initial state
   */
  public void reset() {
    myCurrentGeneration = copyGrid(myInitialGrid);
    updateCellMap();

  }

  /**
   * @returns the grid representing the current generation of cells
   */
  public Grid getCurrentGeneration() {
    return myCurrentGeneration;
  }

  /**
   * @returns the simulation title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @returns the simulation description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @returns the initial grid representing the starting point of the simulation
   */
  protected Grid getInitialGrid() {
    return myInitialGrid;
  }

  /**
   * @returns the edge type of the simulation (finite or toroidal)
   */
  public int getEdgeType() {
    return myEdgeType;
  }

  /**
   * @returns the shape of the simulation (rectangular, rectangular close, hexagonal or triangular)
   */
  public int getShape() {
    return myShape;
  }

  /**
   * @returns a map of cell types to the number of cells of that type present in the simulation
   */
  public HashMap<Integer, Integer> getCellMap() {
    return myCellMap;
  }

  /**
   * @returns a list of initial cell values present in the grid
   */
  protected List<Integer> getInitialCellValues() {
    return myCellValues;
  }

  /**
   * @param map is a map of simulation parameter names to values that are passed to the simulation
   *            This allows users to change the parameters of the simulation. This method is
   *            overridden in simulations that have 1 or more parameters
   */
  public void setSimulationParameters(Map<String, Double> map) {
    return;
  }

  /**
   * @param myEdgeType is the edge type (finite or toroidal) that the simulation is being set to
   */
  public void setEdgeType(int myEdgeType) {
    this.myEdgeType = myEdgeType;
  }

  /**
   * @param myShape is the shape that the simulation is being set to
   */
  public void setShape(int myShape) {
    this.myShape = myShape;
  }

  /**
   * @param grid is the grid of cells that the current generation is being set to
   */
  protected void setCurrentGeneration(Grid grid) {
    myCurrentGeneration = grid;
  }

  /**
   * @param t is the title that is being set
   * @param d is the description that is being set
   */
  public void setNames(String t, String d) {
    title = t;
    description = d;
  }


}
