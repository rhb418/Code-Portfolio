package cellsociety.evolution;

import static cellsociety.Constants.BURNED;
import static cellsociety.Constants.BURNING;
import static cellsociety.Constants.DEFAULT_NUM_STEPS;
import static cellsociety.Constants.PROBCATCH;
import static cellsociety.Constants.RECTANGULAR_CLOSE;
import static cellsociety.Constants.TREE;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Robert Barnette This class implements the ruleset for the Fire simulation. It is reliant
 * on the grid class to function and the updateCell method executes the specific rules of the
 * simulation.
 */
public class EvolutionFire extends Evolution {

  private double myProbCatch;

  /**
   * @param grid      the grid object representing the initial state of the simulation
   * @param probCatch the probability a tree catches fire if it is near a burning tree This
   *                  constructor makes an evolutionFire object given a grid of initial states and a
   *                  probability of catching fire
   */
  public EvolutionFire(Grid grid, double probCatch) {
    super(grid);
    setShape(RECTANGULAR_CLOSE);
    myProbCatch = probCatch;
  }

  @Override
  public Map<String, Double> getSimulationParameters() {
    Map<String, Double> ret = new HashMap<>();
    ret.put(PROBCATCH, myProbCatch);
    return ret;
  }

  @Override
  public void setSimulationParameters(Map<String, Double> map) {
    myProbCatch = map.get(PROBCATCH);
  }

  /**
   * @param cell   is the cell being updated
   * @param newGen is the grid where the updated cell is placed. This method implements the rules of
   *               the Fire simulation by updating a cell based on the surrounding values of the
   *               cells directly above below and to either side of the cell.
   */
  @Override
  public void updateCell(Cell cell, Grid newGen) {
    List<Cell> neighbors = getCurrentGeneration()
        .getNeighbors(cell, getShape(), getEdgeType(), DEFAULT_NUM_STEPS);
    Cell newCell = new Cell(cell.getValue(), cell.getX(), cell.getY());
    Random rand = new Random();

    double val = rand.nextDouble();
    boolean nearFire = false;

    for (Cell c : neighbors) {
      if (c.getValue() == BURNING) {
        nearFire = true;
      }
    }

    if (newCell.getValue() == BURNING) {
      newCell.setValue(BURNED);
    } else if (newCell.getValue() == TREE && nearFire && val <= myProbCatch) {
      newCell.setValue(BURNING);
    }
    newGen.setCell(newCell.getX(), newCell.getY(), newCell);
  }

}
