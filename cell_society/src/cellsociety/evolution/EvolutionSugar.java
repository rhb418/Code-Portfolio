package cellsociety.evolution;

import static cellsociety.Constants.AGENT;
import static cellsociety.Constants.LARGE_SUGAR;
import static cellsociety.Constants.MEDIUM_SUGAR;
import static cellsociety.Constants.NO_SUGAR;
import static cellsociety.Constants.RECTANGULAR_CLOSE;
import static cellsociety.Constants.SMALL_SUGAR;
import static cellsociety.Constants.SUGAR_GROW_BACK_INTERVAL;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Robert Barnette This class implements the ruleset for the Sugar simulation. It is reliant
 * on the grid class to function and createNewGeneration method executes the specific rules of the
 * simulation.
 */
public class EvolutionSugar extends Evolution {

  private int mySugarGrowBackInterval;
  private int intervalCounter;

  /**
   * @param grid is the grid containing the initial configuration for the simulation This
   *             constructor creates an evolutionsugar object from the starting grid
   */
  public EvolutionSugar(Grid grid) {
    super(grid);
    setShape(RECTANGULAR_CLOSE);
    mySugarGrowBackInterval = 6;
    intervalCounter = 0;
    setUpAgentVision();
  }

  private void setUpAgentVision() {
    Random rand = new Random();
    int width = getCurrentGeneration().getWidth();
    int height = getCurrentGeneration().getHeight();

    for (int k = 0; k < height; k++) {
      for (int j = 0; j < width; j++) {
        int vision = rand.nextInt(3) + 1;
        int energy = rand.nextInt(21) + 5;
        int metabolism = rand.nextInt(3) + 1;
        Cell cell = getCurrentGeneration().getCell(j, k);
        if (cell.getValue() == AGENT) {
          cell.setVision(vision);
          cell.setEnergy(energy);
          cell.setMetabolism(metabolism);
        }
      }
    }
  }

  /**
   * Resets the simulation to its initial state
   */
  @Override
  public void reset() {
    setCurrentGeneration(copyGrid(getInitialGrid()));
    setUpAgentVision();
    intervalCounter = 0;
    updateCellMap();

  }

  /**
   * @returns a map of simulation parameter names to doubles representing the simulation parameters
   * This method returns a map containing the simulation parameters and overrides a superclass
   * method allowing the sugar grow back interval to be returned
   */
  @Override
  public Map<String, Double> getSimulationParameters() {
    Map<String, Double> ret = new HashMap<>();
    ret.put(SUGAR_GROW_BACK_INTERVAL, (double) mySugarGrowBackInterval);
    return ret;
  }

  /**
   * @param map is a map of simulation parameter names to values that are passed to the simulation
   *            This allows users to change the parameters of the simulation. This method overrides
   *            the superclass method to allow the sugarGrowBackInterval to be set
   */
  @Override
  public void setSimulationParameters(Map<String, Double> map) {
    double sugarGrowBackInterval = map.get(SUGAR_GROW_BACK_INTERVAL);
    mySugarGrowBackInterval = Math.abs((int) sugarGrowBackInterval);
  }

  /**
   * @param x is the x coordinate of the cell being switched
   * @param y is the y coordinate of the cell being switched This method switches a given cell to a
   *          different type of cell in the sugar simulation, overriding the superclass method
   */
  @Override
  public void switchCellOnClick(int x, int y) {
    Random rand = new Random();
    List<Integer> cellVals = getInitialCellValues();
    List<Integer> exclusiveCellVals = new ArrayList<>(new HashSet<>(cellVals));
    Cell switchCell = getCurrentGeneration().getCell(x, y);

    int index = exclusiveCellVals.indexOf(switchCell.getValue());
    int newIndex = index + 1;

    if (newIndex >= exclusiveCellVals.size()) {
      newIndex = 0;
    }
    switchCell.setValue(exclusiveCellVals.get(newIndex));

    if (switchCell.getValue() == AGENT) {
      int vision = rand.nextInt(3) + 1;
      int energy = rand.nextInt(21) + 5;
      int metabolism = rand.nextInt(3) + 1;
      switchCell.setVision(vision);
      switchCell.setEnergy(energy);
      switchCell.setMetabolism(metabolism);
    }
  }

  /**
   * @returns the new grid representing the new generation of cells. This method overrides the
   * superclass method and creates a new grid as per sugar rules
   */
  @Override
  public Grid createNewGeneration() {
    Grid newGen = getCurrentGeneration();
    List<Cell> agents = getListOfAgents(newGen);
    moveAgents(agents, newGen);

    if (intervalCounter >= mySugarGrowBackInterval) {
      growSugar(newGen);
      intervalCounter = 0;
    }

    intervalCounter++;
    updateCellMap();
    return newGen;
  }

  private void growSugar(Grid newGen) {
    for (int k = 0; k < newGen.getHeight(); k++) {
      for (int j = 0; j < newGen.getWidth(); j++) {

        Cell sugarCell = newGen.getCell(j, k);
        int val = sugarCell.getValue();

        switch (val) {
          case NO_SUGAR:
            sugarCell.setValue(SMALL_SUGAR);
            continue;
          case SMALL_SUGAR:
            sugarCell.setValue(MEDIUM_SUGAR);
            continue;
          case MEDIUM_SUGAR:
            sugarCell.setValue(LARGE_SUGAR);
            continue;
          case LARGE_SUGAR:
            continue;
        }


      }
    }
  }

  private void moveAgents(List<Cell> agents, Grid newGen) {
    Random rand = new Random();
    while (agents.size() > 0) {
      int index = rand.nextInt(agents.size());
      acquireSugar(agents.get(index), newGen);
      agents.remove(index);
    }

  }

  private void acquireSugar(Cell agent, Grid newGen) {
    int vision = agent.getVision();
    List<Cell> neighbors = newGen.getNeighbors(agent, getShape(), getEdgeType(), vision);
    List<Cell> maxList = new ArrayList<>();
    int max = 0;

    for (Cell c : neighbors) {
      if (c.getValue() > max && c.getValue() != AGENT) {
        maxList.clear();
        maxList.add(c);
        max = c.getValue();
      } else if (c.getValue() == max && c.getValue() != AGENT) {
        maxList.add(c);
      }
    }
    Cell cell = findClosestCell(agent, maxList);
    determineAgentOutcome(agent, cell, newGen);
  }

  private void determineAgentOutcome(Cell agent, Cell sugar, Grid newGen) {
    if (agent.getEnergy() <= 0) {
      agent.setValue(NO_SUGAR);
      agent.setEnergy(0);
      agent.setVision(0);
      agent.setMetabolism(0);
      return;
    }

    agent.setEnergy(agent.getEnergy() + Math.max(sugar.getValue() - 6, 0) - agent.getMetabolism());
    sugar.setValue(AGENT);
    sugar.setVision(agent.getVision());
    sugar.setMetabolism(agent.getMetabolism());
    sugar.setEnergy(agent.getEnergy());

    agent.setEnergy(0);
    agent.setValue(NO_SUGAR);
    agent.setMetabolism(0);
    agent.setVision(0);
  }

  private Cell findClosestCell(Cell agent, List<Cell> maxList) {
    int xPos = agent.getX();
    int yPos = agent.getY();
    Random rand = new Random();
    List<Cell> minList = new ArrayList<>();
    int minDist = 99999;

    for (Cell cell : maxList) {
      int dist = Math.abs(cell.getX() - xPos) + Math.abs(cell.getY() - yPos);
      if (dist < minDist) {
        minDist = dist;
        minList.clear();
        minList.add(cell);
      } else if (dist == minDist) {
        minList.add(cell);
      }
    }
    int index = rand.nextInt(minList.size());
    return minList.get(index);
  }

  private List<Cell> getListOfAgents(Grid newGen) {
    List<Cell> agentList = new ArrayList<>();

    for (int k = 0; k < newGen.getHeight(); k++) {
      for (int j = 0; j < newGen.getWidth(); j++) {
        Cell cell = getCurrentGeneration().getCell(j, k);
        if (cell.getValue() == AGENT) {
          agentList.add(cell);
        }
      }
    }
    return agentList;
  }
}
