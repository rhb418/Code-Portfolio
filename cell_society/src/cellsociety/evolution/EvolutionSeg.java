package cellsociety.evolution;

import static cellsociety.Constants.DEFAULT_NUM_STEPS;
import static cellsociety.Constants.EMPTY_SPOT;
import static cellsociety.Constants.PERCENT_SATISFIED;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Robert Barnette This class implements the ruleset for the Segregation simulation. It is
 * reliant on the grid class to function and createNewGeneration method executes the specific rules
 * of the simulation.
 */
public class EvolutionSeg extends Evolution {

  private double myPercentSatisfied;
  private List<Cell> emptyCells;

  /**
   * @param grid    is the grid that gives initial simulation state.
   * @param percent is the percentage of cells that have to be the same type as a particular cell
   *                for it to be satisfied. This constructor makes an EvolutionSeg object by calling
   *                the superclass constructor on grid. Additionally it also sets some initial
   *                parameters for the object.
   */
  public EvolutionSeg(Grid grid, double percent) {
    super(grid);
    myPercentSatisfied = percent;
    emptyCells = new ArrayList<>();
  }

  /**
   * @returns a map with one entry containing the string PERCENT_SATISFIED and a double with the
   * percent satisfied value
   */
  @Override
  public Map<String, Double> getSimulationParameters() {
    Map<String, Double> ret = new HashMap<>();
    ret.put(PERCENT_SATISFIED, myPercentSatisfied);
    return ret;
  }

  /**
   * @param map is a map of simulation parameter names to values that are passed to the simulation
   *            This allows users to change the parameters of the simulation. This method overrides
   *            the superclass method to set PercentSatisfied
   */
  @Override
  public void setSimulationParameters(Map<String, Double> map) {
    myPercentSatisfied = map.get(PERCENT_SATISFIED);
  }

  /**
   * @returns the new grid representing the new generation of cells. This method overrides the
   * superclass method and creates a new grid as per segregation rules
   */
  @Override
  public Grid createNewGeneration() {
    Grid newGen = getCurrentGeneration();
    List<Cell> unhappyCells = new ArrayList<>();
    findEmptyCells(newGen);

    for (int k = 0; k < newGen.getHeight(); k++) {
      for (int j = 0; j < newGen.getWidth(); j++) {
        Cell cell = newGen.getCell(j, k);
        if (!isSatisfied(cell)) {
          unhappyCells.add(cell);
        }
      }
    }
    moveCells(unhappyCells, newGen);
    updateCellMap();
    return newGen;
  }

  /**
   * @param newGen is the grid being modified to create the new generation. This method finds all
   *               empty cells in the current generation's grid
   */
  private void findEmptyCells(Grid newGen) {
    emptyCells = new ArrayList<>();
    for (int k = 0; k < newGen.getHeight(); k++) {
      for (int j = 0; j < newGen.getWidth(); j++) {
        Cell cell = newGen.getCell(j, k);
        if (cell.getValue() == EMPTY_SPOT) {
          emptyCells.add(cell);
        }
      }
    }
  }

  /**
   * @param unhappyCells is the list of cells unhappy with their position
   * @param newGen       is the grid being modified to create the new generation. This method moves
   *                     all unhappyCells to new spots
   */
  private void moveCells(List<Cell> unhappyCells, Grid newGen) {
    for (Cell cell : unhappyCells) {
      findAndSwitchCells(cell, newGen);
    }

  }

  /**
   * @param cell   is the cell being swapped
   * @param newGen is the grid being modified to create the new generation. This method switches the
   *               unhappy cell with a random empty one.
   */
  private void findAndSwitchCells(Cell cell, Grid newGen) {
    Cell EmptyCell = findRandomEmptyCell(newGen);
    EmptyCell.setValue(cell.getValue());
    cell.setValue(EMPTY_SPOT);
    emptyCells.add(cell);
  }

  /**
   * @param newGen is the grid being modified
   * @returns a random empty cell. This method finds and returns a random empty cell.
   */
  private Cell findRandomEmptyCell(Grid newGen) {
    Random rand = new Random();
    int index = rand.nextInt(emptyCells.size());
    Cell rEmptyCell = emptyCells.get(index);
    emptyCells.remove(index);
    return rEmptyCell;
  }


  /**
   * @param cell is the cell that is being examined
   * @return a boolean indicating whether cell is satisfied with its . Determines if a cell is
   * satisfied with its position
   */
  private boolean isSatisfied(Cell cell) {
    if (cell.getValue() == EMPTY_SPOT) {
      return true;
    }
    List<Cell> neighbors = getCurrentGeneration()
        .getNeighbors(cell, getShape(), getEdgeType(), DEFAULT_NUM_STEPS);
    int numGoodNeighbors = 0;
    for (Cell c : neighbors) {
      if (c.getValue() == cell.getValue()) {
        numGoodNeighbors++;
      }
    }
    double percentGood = ((double) numGoodNeighbors) / ((double) neighbors.size());

    return percentGood >= myPercentSatisfied;
  }

}
