package cellsociety.evolution;

import static cellsociety.Constants.DEFAULT_NUM_STEPS;
import static cellsociety.Constants.ENERGY_PER_PREY;
import static cellsociety.Constants.FISH;
import static cellsociety.Constants.KELP;
import static cellsociety.Constants.RECTANGULAR_CLOSE;
import static cellsociety.Constants.SHARK;
import static cellsociety.Constants.TOROIDAL;
import static cellsociety.Constants.TURNS_TO_REPRODUCE;

import cellsociety.Cell;
import cellsociety.Grid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Robert Barnette This class implements the ruleset for the Wator simulation. It is reliant
 * on the grid class to function and createNewGeneration method executes the specific rules of the
 * simulation.
 */
public class EvolutionWator extends Evolution {

  private int myEnergyPerPrey;
  private int myTurnsToReproduce;
  private int myInitialEnergy;

  /**
   * @param grid             is the grid representing the initial state of the simulation
   * @param initialEnergy    is the energy given to each shark initially
   * @param energyPerPrey    is the energy a shark gets when it eats a fish
   * @param turnsToReproduce is the number of turns it takes a fish or a shark to reproduce. This
   *                         constructor makes an EvolutionSeg object by calling the superclass
   *                         constructor on grid. Additionally it also sets some initial parameters
   *                         for the object. Additionally it gives each shark an initial energy by
   *                         updating the grid.
   */
  public EvolutionWator(Grid grid, int initialEnergy, int energyPerPrey,
      int turnsToReproduce) {
    super(grid);
    myEnergyPerPrey = energyPerPrey;
    myTurnsToReproduce = turnsToReproduce;
    myInitialEnergy = initialEnergy;
    setEdgeType(TOROIDAL);
    setShape(RECTANGULAR_CLOSE);
    giveSharksEnergy();
  }

  /**
   * Resets the simulation to its initial state
   */
  @Override
  public void reset() {
    setCurrentGeneration(copyGrid(getInitialGrid()));
    giveSharksEnergy();
    updateCellMap();
  }

  /**
   * @returns a map of simulation parameter names to doubles representing the simulation parameters
   * This method returns a map containing the simulation parameters and overrides a superclass
   * method allowing the energy per prey and turns to reproduce to be returned
   */
  @Override
  public Map<String, Double> getSimulationParameters() {
    Map<String, Double> ret = new HashMap<>();
    ret.put(ENERGY_PER_PREY, (double) myEnergyPerPrey);
    ret.put(TURNS_TO_REPRODUCE, (double) myTurnsToReproduce);

    return ret;
  }

  /**
   * @param map is a map of simulation parameter names to values that are passed to the simulation
   *            This allows users to change the parameters of the simulation. This method overrides
   *            the superclass method to allow  the energy per prey and turns to reproduce to be
   *            set
   */
  @Override
  public void setSimulationParameters(Map<String, Double> map) {
    double energyPerPrey = map.get(ENERGY_PER_PREY);
    double turnsToReproduce = map.get(TURNS_TO_REPRODUCE);
    myEnergyPerPrey = Math.abs((int) energyPerPrey);
    myTurnsToReproduce = Math.abs((int) turnsToReproduce);
  }

  /**
   * @param x is the x coordinate of the cell being switched
   * @param y is the y coordinate of the cell being switched This method switches a given cell to a
   *          different type of cell in the Wator simulation, overriding the superclass method
   */
  @Override
  public void switchCellOnClick(int x, int y) {
    List<Integer> cellVals = getInitialCellValues();
    List<Integer> exclusiveCellVals = new ArrayList<>(new HashSet<>(cellVals));
    Cell switchCell = getCurrentGeneration().getCell(x, y);

    int index = exclusiveCellVals.indexOf(switchCell.getValue());
    int newIndex = index + 1;

    if (newIndex >= exclusiveCellVals.size()) {
      newIndex = 0;
    }
    switchCell.setValue(exclusiveCellVals.get(newIndex));
    if (switchCell.getValue() == SHARK) {
      switchCell.setEnergy(myInitialEnergy);
      switchCell.setTurns(0);
    } else if (switchCell.getValue() == FISH) {
      switchCell.setTurns(0);
    }
  }

  /**
   * Set the energy of all sharks in the grid to myInitialEnergy
   */
  private void giveSharksEnergy() {
    Grid newGen = getCurrentGeneration();
    int width = newGen.getWidth();
    int height = newGen.getHeight();

    for (int k = 0; k < height; k++) {
      for (int j = 0; j < width; j++) {
        Cell cell = newGen.getCell(j, k);
        if (cell.getValue() == SHARK) {
          cell.setEnergy(myInitialEnergy);
        }
      }
    }

  }

  /**
   * @returns the grid representing the new generation. It does this by modified the current
   * generations grid to update it using the rules of Wa-Tor
   */
  @Override
  public Grid createNewGeneration() {
    Grid newGen = getCurrentGeneration();
    List<Cell> sharks = getAllSharksOrFish(newGen, SHARK);
    moveSharks(sharks, newGen);
    List<Cell> fish = getAllSharksOrFish(newGen, FISH);
    moveFish(fish, newGen);

    updateCellMap();
    return newGen;
  }


  /**
   * @param newGen Is the grid being modified to create the new generation
   * @param type   is the type determining if this method is being called on a shark or fish
   * @returns a list of shark or fish cells This method makes a list of all the shark or fish cells
   * in newgen.
   */
  private List<Cell> getAllSharksOrFish(Grid newGen, int type) {
    List<Cell> sharkOrFishList = new ArrayList<>();
    int width = newGen.getWidth();
    int height = newGen.getHeight();

    for (int k = 0; k < height; k++) {
      for (int j = 0; j < width; j++) {
        Cell cell = newGen.getCell(j, k);
        if (cell.getValue() == type) {
          sharkOrFishList.add(cell);
        }
      }
    }
    return sharkOrFishList;
  }

  /**
   * @param sharks List of all sharks on the grid
   * @param newGen is the grid being updated. This method moves all the sharks to a new grid
   *               location if the sharks can move.
   */
  private void moveSharks(List<Cell> sharks, Grid newGen) {
    for (Cell shark : sharks) {
      findAndEatFish(shark, newGen);
    }
  }

  /**
   * @param fish   List of all fish on the grid
   * @param newGen is the grid being updated. This method moves all the fish to a new grid location
   *               if the sharks can move.
   */
  private void moveFish(List<Cell> fish, Grid newGen) {
    for (Cell f : fish) {
      moveOrStay(f, newGen);
    }
  }

  /**
   * @param fish   is the fish attempting to move
   * @param newGen is the grid being updated. This method determines if the fish can move and the
   *               either executes this move or keeps the fish in the same spot
   */
  private void moveOrStay(Cell fish, Grid newGen) {
    Random rand = new Random();
    List<Cell> neighbors = newGen.getNeighbors(fish, getShape(), getEdgeType(), DEFAULT_NUM_STEPS);
    List<Cell> nearbyKelp = new ArrayList<>();
    for (Cell cell : neighbors) {
      if (cell.getValue() == KELP) {
        nearbyKelp.add(cell);
      }
    }
    fish.setTurns(fish.getTurns() + 1);

    if (nearbyKelp.size() == 0) {
      return;
    } else {
      int index = rand.nextInt(nearbyKelp.size());
      Cell kelp = nearbyKelp.get(index);
      moveToNewCell(fish, kelp);
    }

  }


  /**
   * @param shark  is the shark cell being moved
   * @param newGen is the grid being modified. This methods determines the number of fish and kelp
   *               cells around the shark and then moves the shark or keeps it in place based on
   *               this information.
   */
  private void findAndEatFish(Cell shark, Grid newGen) {
    List<Cell> neighbors = newGen.getNeighbors(shark, getShape(), getEdgeType(), DEFAULT_NUM_STEPS);
    List<Cell> nearbyFish = new ArrayList<>();
    List<Cell> nearbyKelp = new ArrayList<>();

    for (Cell cell : neighbors) {
      if (cell.getValue() == FISH) {
        nearbyFish.add(cell);
      } else if (cell.getValue() == KELP) {
        nearbyKelp.add(cell);
      }
    }
    if (nearbyFish.size() + nearbyKelp.size() == 0) {
      stayPutOrDie(shark);
    } else if (nearbyFish.size() >= 1) {
      eatFish(shark, nearbyFish);
    } else {
      moveToKelp(shark, nearbyKelp);
    }

  }

  /**
   * @param shark      is the shark cell being moved
   * @param nearbyKelp is the list of nearby kelp cells. This method moves the shark to a random
   *                   nearby kelp cell.
   */
  private void moveToKelp(Cell shark, List<Cell> nearbyKelp) {
    Random rand = new Random();
    int index = rand.nextInt(nearbyKelp.size());
    Cell kelp = nearbyKelp.get(index);

    shark.setEnergy(shark.getEnergy() - 1);
    shark.setTurns(shark.getTurns() + 1);
    if (shark.getEnergy() == 0) {
      shark.setTurns(0);
      shark.setValue(KELP);
    } else {
      moveToNewCell(shark, kelp);
    }
  }


  /**
   * @param shark      is the shark being moved
   * @param nearbyFish is a list of nearby fish cells. This method causes the shark to eat one of
   *                   the nearby fish at random.
   */
  private void eatFish(Cell shark, List<Cell> nearbyFish) {
    Random rand = new Random();
    int index = rand.nextInt(nearbyFish.size());
    Cell eatenFish = nearbyFish.get(index);

    shark.setEnergy(shark.getEnergy() + myEnergyPerPrey - 1);
    shark.setTurns(shark.getTurns() + 1);

    moveToNewCell(shark, eatenFish);

  }

  /**
   * @param shark is the shark cell. This method determines if the shark should die or simply state
   *              put based on its energy
   */
  private void stayPutOrDie(Cell shark) {
    shark.setTurns(shark.getTurns() + 1);
    shark.setEnergy(shark.getEnergy() - 1);
    if (shark.getEnergy() == 0) {
      shark.setTurns(0);
      shark.setValue(KELP);
    }
  }

  /**
   * @param sharkOrFish is the shark or fish being moved
   * @param newCell     is the cell where the shark or fish is being moved. This method moves a
   *                    shark or fish to the given new cell.
   */
  private void moveToNewCell(Cell sharkOrFish, Cell newCell) {
    if (sharkOrFish.getTurns() >= myTurnsToReproduce) {
      newCell.setValue(sharkOrFish.getValue());
      newCell.setEnergy(sharkOrFish.getEnergy());
      newCell.setTurns(0);

      sharkOrFish.setTurns(0);
      sharkOrFish.setEnergy(myInitialEnergy);
    } else {
      newCell.setValue(sharkOrFish.getValue());
      newCell.setEnergy(sharkOrFish.getEnergy());
      newCell.setTurns(sharkOrFish.getTurns());

      sharkOrFish.setValue(KELP);
      sharkOrFish.setTurns(0);
      sharkOrFish.setEnergy(0);
    }
  }
}
