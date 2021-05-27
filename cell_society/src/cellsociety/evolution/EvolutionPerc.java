package cellsociety.evolution;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.List;

import static cellsociety.Constants.*;

/**
 * @author Robert Barnette This class implements the ruleset for the Percolation simulation. It is
 * reliant on the grid class to function and the updateCell method executes the specific rules of
 * the simulation.
 */
public class EvolutionPerc extends Evolution {

  /**
   * @param grid is the grid that gives initial simulation state. This constructor makes an
   *             EvolutionPerc object by calling the superclass constructor on grid
   */
  public EvolutionPerc(Grid grid) {
    super(grid);
  }

  /**
   * @param cell   is the cell being updated
   * @param newGen is the grid where the new cell is being added This method updates the cell based
   *               on the surrounding cells which in this case includes cells diagonal to the
   *               original cell according to percolation rules.
   */
  @Override
  public void updateCell(Cell cell, Grid newGen) {
    List<Cell> neighbors = getCurrentGeneration()
        .getNeighbors(cell, getShape(), getEdgeType(), DEFAULT_NUM_STEPS);
    Cell newCell = new Cell(cell.getValue(), cell.getX(), cell.getY());

    boolean Percolates = false;

    for (int k = 0; k < neighbors.size(); k++) {
      if (neighbors.get(k).getValue() == PERCOLATED) {
        Percolates = true;
      }
    }
    if (Percolates && cell.getValue() == OPEN) {
      newCell.setValue(PERCOLATED);
    }
    newGen.setCell(newCell.getX(), newCell.getY(), newCell);
  }
}
