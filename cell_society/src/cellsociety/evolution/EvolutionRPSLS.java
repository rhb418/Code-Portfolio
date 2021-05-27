package cellsociety.evolution;

import static cellsociety.Constants.DEFAULT_NUM_STEPS;
import static cellsociety.Constants.LIZARD;
import static cellsociety.Constants.PAPER;
import static cellsociety.Constants.ROCK;
import static cellsociety.Constants.SCISSORS;
import static cellsociety.Constants.SPOCK;
import static cellsociety.Constants.THRESHOLD;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Robert Barnette This class implements the ruleset for the RPSLS simulation. It is reliant
 * on the grid class to function and updateCell method executes the specific rules of the
 * simulation.
 */
public class EvolutionRPSLS extends Evolution {

  private static HashMap<Integer, ArrayList<Integer>> myRules;
  private int myThreshold;

  /**
   * @param grid is the grid that gives initial simulation state. This constructor makes and
   *             EvolutionRPSLS object with an initial threshold value of 2.
   */
  public EvolutionRPSLS(Grid grid) {
    super(grid);
    myThreshold = 2;
    myRules = new HashMap<>();
    setUpRules();
  }

  /**
   * @returns a map with one entry containing the string THRESHOLD and a double with the threshold
   * value
   */
  @Override
  public Map<String, Double> getSimulationParameters() {
    Map<String, Double> ret = new HashMap<>();
    ret.put(THRESHOLD, (double) myThreshold);
    return ret;
  }

  /**
   * @param map is a map of simulation parameter names to values that are passed to the simulation
   *            This allows users to change the parameters of the simulation. The value changed in
   *            this case is the threshold value
   */
  @Override
  public void setSimulationParameters(Map<String, Double> map) {
    double thresh = map.get(THRESHOLD);
    myThreshold = Math.abs((int) thresh);
  }

  private void setUpRules() {
    myRules.clear();
    myRules.put(ROCK, new ArrayList<>());
    myRules.put(PAPER, new ArrayList<>());
    myRules.put(SCISSORS, new ArrayList<>());
    myRules.put(LIZARD, new ArrayList<>());
    myRules.put(SPOCK, new ArrayList<>());

    myRules.get(ROCK).add(PAPER);
    myRules.get(ROCK).add(SPOCK);

    myRules.get(PAPER).add(SCISSORS);
    myRules.get(PAPER).add(LIZARD);

    myRules.get(SCISSORS).add(ROCK);
    myRules.get(SCISSORS).add(SPOCK);

    myRules.get(LIZARD).add(SCISSORS);
    myRules.get(LIZARD).add(ROCK);

    myRules.get(SPOCK).add(PAPER);
    myRules.get(SPOCK).add(LIZARD);
  }

  /**
   * @param cell   is the cell being updated
   * @param newGen is the grid where the new cell is being added This method implements the rules
   *               for the rock paper scissors lizard spock simulation
   */
  @Override
  public void updateCell(Cell cell, Grid newGen) {
    List<Cell> neighbors = getCurrentGeneration()
        .getNeighbors(cell, getShape(), getEdgeType(), DEFAULT_NUM_STEPS);
    ArrayList<Integer> beatByList = myRules.get(cell.getValue());
    Cell newCell = new Cell(cell.getValue(), cell.getX(), cell.getY());

    int beatBy0 = 0;
    int beatBy1 = 0;

    for (Cell c : neighbors) {
      int index = beatByList.indexOf(c.getValue());
      if (index == 0) {
        beatBy0++;
      } else if (index == 1) {
        beatBy1++;
      }
    }

    if (beatBy0 > myThreshold && beatBy0 > beatBy1) {
      newCell.setValue(beatByList.get(0));
    } else if (beatBy1 > myThreshold) {
      newCell.setValue(beatByList.get(1));
    }

    newGen.setCell(newCell.getX(), newCell.getY(), newCell);
  }

}
