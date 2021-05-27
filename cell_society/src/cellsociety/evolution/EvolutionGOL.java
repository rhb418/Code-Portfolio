package cellsociety.evolution;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.List;

import static cellsociety.Constants.ALIVE;
import static cellsociety.Constants.DEAD;
import static cellsociety.Constants.DEFAULT_NUM_STEPS;

/**
 * @author Robert Barnette This class implements the ruleset for the Game of Life simulation. It is
 * reliant on the grid class to function and the updateCell method executes the specific rules of
 * the simulation.
 */
public class EvolutionGOL extends Evolution {

  /**
   * @param grid is the grid that gives initial simulation state. This constructor makes an
   *             EvolutionGOL object by calling the superclass constructor on grid
   */
  public EvolutionGOL(Grid grid) {
    super(grid);
  }

  /**
   * @param cell   is the cell being updated
   * @param newGen is the grid where the new cell is being added. This method updates the cell based
   *               on the surrounding cells which in this case includes cells diagonal to the
   *               original cell according to game of life rules.
   */
  @Override
  public void updateCell(Cell cell, Grid newGen) {
    List<Cell> neighbors = getCurrentGeneration()
        .getNeighbors(cell, getShape(), getEdgeType(), DEFAULT_NUM_STEPS);
    Cell newCell = new Cell(cell.getValue(), cell.getX(), cell.getY());
    int numAlive = 0;

    for (int k = 0; k < neighbors.size(); k++) {
      if (neighbors.get(k).getValue() == 1) {
        numAlive++;
      }
    }

    if (numAlive == 3 || numAlive == 2 && cell.getValue() == ALIVE) {
      newCell.setValue(ALIVE);

    } else {
      newCell.setValue(DEAD);
    }

    newGen.setCell(newCell.getX(), newCell.getY(), newCell);
  }
}
