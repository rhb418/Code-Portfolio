package cellsociety;

import static cellsociety.Constants.EVEN;
import static cellsociety.Constants.GOL;
import static cellsociety.Constants.HEXAGONAL_FINITE;
import static cellsociety.Constants.HEXAGONAL_TOROIDAL;
import static cellsociety.Constants.ODD;
import static cellsociety.Constants.RECTANGULAR_CLOSE_FINITE;
import static cellsociety.Constants.RECTANGULAR_CLOSE_TOROIDAL;
import static cellsociety.Constants.RECTANGULAR_FINITE;
import static cellsociety.Constants.RECTANGULAR_TOROIDAL;
import static cellsociety.Constants.TRIANGULAR_FINITE;
import static cellsociety.Constants.TRIANGULAR_TOROIDAL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Robert Barnette This class represents the grid data structure used in this project. It
 * keeps track of cells and provides neighbor calculation methods to be used in simulation. It is
 * dependent on the cell class and is meant to be used as a part of an evolution class
 */
public class Grid {

  private Cell[][] myGrid;
  private int myWidth;
  private int myHeight;


  public Grid(int width, int height) {
    this(width, height, null);
  }


  /**
   * @param width  width of the grid
   * @param height height of the grid
   * @param values values of cells in the grid. This constructor creates a new grid using a 2D array
   *               of cells with a given width and height
   */
  public Grid(int width, int height, List<Integer> values) {
    myWidth = width;
    myHeight = height;
    myGrid = new Cell[width][height];
    setUpGrid(values);
  }

  /**
   * @param values are the initial values for the cells in the grid. This method sets up the grid by
   *               passing initial values to the cells in the grid.
   */
  private void setUpGrid(List<Integer> values) {
    int counter = 0;
    int cellVal = 0;

    for (int k = 0; k < myHeight; k++) {
      for (int j = 0; j < myWidth; j++) {

        if (values != null) {
          cellVal = values.get(counter);
        }

        Cell newCell = new Cell(cellVal, j, k);
        myGrid[j][k] = newCell;

        counter++;
      }
    }

  }

  /**
   * @param cell      is the cells whose neighbors are being determined
   * @param shape     is the shape of the simulation
   * @param edgeType  is the edge type of the simulation
   * @param num_steps is the number of steps in a given direction that the neighbors go in the
   *                  rectangular close case
   * @returns a list of cells that are neighbors to the input cell based on the shape and edge type
   * of the simulation This method does neighbor calculation for any cell in the grid
   */
  public List<Cell> getNeighbors(Cell cell, int shape, int edgeType, int num_steps) {
    int neighborType = shape + 10 * edgeType;

    switch (neighborType) {
      case RECTANGULAR_FINITE:
        return getRectangularFiniteNeighbors(cell);
      case RECTANGULAR_TOROIDAL:
        return getRectangularToroidalNeighbors(cell);
      case RECTANGULAR_CLOSE_FINITE:
        return getRectangularCloseFiniteNeighbors(cell, num_steps);
      case RECTANGULAR_CLOSE_TOROIDAL:
        return getRectangularCloseToroidalNeighbors(cell, num_steps);
      case TRIANGULAR_FINITE:
        return getTriangularFiniteNeighbors(cell);
      case TRIANGULAR_TOROIDAL:
        return getTriangularToroidalNeighbors(cell);
      case HEXAGONAL_FINITE:
        return getHexagonalFiniteNeighbors(cell);
      case HEXAGONAL_TOROIDAL:
        return getHexagonalToroidalNeighbors(cell);
    }

    return null;
  }


  /**
   * @param cell is the cell whose neighbors are being found.
   * @returns a list of up to eight neighbor cells. This method returns a list of up to 8 neighbor
   * cells including cells that are directly diagonal to the given cell
   */
  private List<Cell> getRectangularFiniteNeighbors(Cell cell) {
    List<Cell> neighbors = new ArrayList<>();

    int xCord = cell.getX();
    int yCord = cell.getY();

    //This for loop checks every possible neighbor and omits those out of bounds or equal to the current cell
    for (int k = xCord - 1; k <= xCord + 1; k++) {
      for (int j = yCord - 1; j <= yCord + 1; j++) {
        if (checkBounds(k, j) || k == xCord && j == yCord) {
          continue;
        }
        neighbors.add(myGrid[k][j]);
      }
    }

    return neighbors;
  }

  /**
   * @param cell is the cell whose neighbors are being retrieved.
   * @returns a list of up to 4 neighbor cells. Gets the neighbors that are directly above or below
   * or to the left and right of the given cell.
   */
  private List<Cell> getRectangularCloseFiniteNeighbors(Cell cell, int num_steps) {
    List<Cell> neighbors = new ArrayList<>();

    int xCord = cell.getX();
    int yCord = cell.getY();

    for (int i = xCord - num_steps; i <= xCord + num_steps; i++) {
      if (i == xCord || i < 0 || i > myWidth - 1) {
        continue;
      }
      neighbors.add(myGrid[i][yCord]);
    }

    for (int k = yCord - num_steps; k <= yCord + num_steps; k++) {
      if (k == yCord || k < 0 || k > myHeight - 1) {
        continue;
      }
      neighbors.add(myGrid[xCord][k]);
    }

    return neighbors;
  }

  private List<Cell> getHexagonalFiniteNeighbors(Cell cell) {
    List<Cell> neighbors = new ArrayList<>();
    int xCord = cell.getX();
    int yCord = cell.getY();
    int evenOrOdd = xCord % 2;

    for (int k = xCord - 1; k <= xCord + 1; k++) {
      for (int j = yCord - 1; j <= yCord + 1; j++) {
        if (checkBounds(k, j) || k == xCord && j == yCord
            || evenOrOdd == EVEN && j < yCord && k != xCord
            || evenOrOdd == ODD && j > yCord && k != xCord) {
          continue;
        }
        neighbors.add(myGrid[k][j]);
      }
    }

    return neighbors;
  }

  private List<Cell> getTriangularFiniteNeighbors(Cell cell) {
    List<Cell> neighbors = new ArrayList<>();
    int xCord = cell.getX();
    int yCord = cell.getY();
    int evenOrOdd = (xCord + yCord) % 2;

    for (int k = xCord - 2; k <= xCord + 2; k++) {
      for (int j = yCord - 1; j <= yCord + 1; j++) {
        if (checkBounds(k, j) || k == xCord && j == yCord
            || evenOrOdd == EVEN && j < yCord && (k > xCord + 1 || k < xCord - 1)
            || evenOrOdd == ODD && j > yCord && (k > xCord + 1 || k < xCord - 1)) {
          continue;
        }
        neighbors.add(myGrid[k][j]);
      }
    }

    return neighbors;
  }

  private List<Cell> getRectangularToroidalNeighbors(Cell cell) {
    List<Cell> neighbors = new ArrayList<>();
    int xCord = cell.getX();
    int yCord = cell.getY();

    //This for loop checks every possible neighbor and omits those out of bounds or equal to the current cell
    for (int k = xCord - 1; k <= xCord + 1; k++) {
      for (int j = yCord - 1; j <= yCord + 1; j++) {
        if (k == xCord && j == yCord) {
          continue;
        }
        neighbors.add(getToroidalNeighborCell(k, j));
      }
    }

    return neighbors;
  }

  private List<Cell> getRectangularCloseToroidalNeighbors(Cell cell, int num_steps) {
    List<Cell> neighbors = new ArrayList<>();

    int xCord = cell.getX();
    int yCord = cell.getY();

    for (int i = xCord - num_steps; i <= xCord + num_steps; i++) {
      if (i == xCord) {
        continue;
      }

      neighbors.add(getToroidalNeighborCell(i, yCord));
    }

    for (int k = yCord - num_steps; k <= yCord + num_steps; k++) {
      if (k == yCord) {
        continue;
      }
      neighbors.add(getToroidalNeighborCell(xCord, k));
    }

    return neighbors;
  }

  private List<Cell> getHexagonalToroidalNeighbors(Cell cell) {
    List<Cell> neighbors = new ArrayList<>();
    int xCord = cell.getX();
    int yCord = cell.getY();
    int evenOrOdd = xCord % 2;

    for (int k = xCord - 1; k <= xCord + 1; k++) {
      for (int j = yCord - 1; j <= yCord + 1; j++) {
        if (k == xCord && j == yCord
            || evenOrOdd == EVEN && j < yCord && k != xCord
            || evenOrOdd == ODD && j > yCord && k != xCord) {
          continue;
        }
        neighbors.add(getToroidalNeighborCell(k, j));
      }
    }

    return neighbors;
  }

  private List<Cell> getTriangularToroidalNeighbors(Cell cell) {
    List<Cell> neighbors = new ArrayList<>();
    int xCord = cell.getX();
    int yCord = cell.getY();
    int evenOrOdd = (xCord + yCord) % 2;

    for (int k = xCord - 2; k <= xCord + 2; k++) {
      for (int j = yCord - 1; j <= yCord + 1; j++) {
        if (k == xCord && j == yCord
            || evenOrOdd == EVEN && j < yCord && (k > xCord + 1 || k < xCord - 1)
            || evenOrOdd == ODD && j > yCord && (k > xCord + 1 || k < xCord - 1)) {
          continue;
        }
        neighbors.add(getToroidalNeighborCell(k, j));
      }
    }

    return neighbors;
  }

  private Cell getToroidalNeighborCell(int x, int y) {
    int xCord = x;
    int yCord = y;

    if (x < 0) {
      xCord = myWidth + x;
    } else if (x > myWidth - 1) {
      xCord = x - myWidth;
    }
    if (y < 0) {
      yCord = myHeight + y;

    } else if (y > myHeight - 1) {
      yCord = y - myHeight;
    }

    return myGrid[xCord][yCord];
  }


  private boolean checkBounds(int x, int y) {
    return (x > myWidth - 1 || x < 0 || y > myHeight - 1 || y < 0);
  }

  /**
   * @returns width of grid
   */
  public int getWidth() {
    return myWidth;
  }

  /**
   * @returns height of grid
   */
  public int getHeight() {
    return myHeight;
  }

  /**
   * @param x is the x coordinate of the cell
   * @param y is the y coordinate of the cell
   * @returns a cell at a certain location in the grid
   */
  public Cell getCell(int x, int y) {
    return myGrid[x][y];
  }

  /**
   * @param x    is the x coordinate of the cell
   * @param y    is the y coordinate of the cell
   * @param cell is the cell being set at a specific location in the grid
   */
  public void setCell(int x, int y, Cell cell) {
    myGrid[x][y] = cell;
  }

}
