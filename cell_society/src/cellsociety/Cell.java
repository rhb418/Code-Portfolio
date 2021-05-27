package cellsociety;

/**
 * @author Robert Barnette This class is the cell class that is stored in the grid and keeps track
 * of particular states
 */
public class Cell {

  private int myValue;
  private int myEnergy;
  private int myTurns;
  private int myX;
  private int myY;
  private int myVision;
  private int myMetabolism;

  /**
   * @param value value of cell
   * @param x     x coordinate of the cell
   * @param y     y coordinate of the cell Constructor that creates a cell object
   */
  public Cell(int value, int x, int y) {
    myValue = value;
    myX = x;
    myY = y;
    myEnergy = 0;
    myTurns = 0;
    myVision = 0;
    myMetabolism = 0;
  }

  /**
   * @returns cell value
   */
  public int getValue() {
    return myValue;
  }

  /**
   * @param myValue is value cell is set to
   */
  public void setValue(int myValue) {
    this.myValue = myValue;
  }

  /**
   * @returns x coordinate of cell
   */
  public int getX() {
    return myX;
  }

  /**
   * @param myX sets x coordinate of cell
   */
  public void setX(int myX) {
    this.myX = myX;
  }

  /**
   * @returns y coordinate of cell
   */
  public int getY() {
    return myY;
  }

  /**
   * @param myY sets y coordinate of cell
   */
  public void setY(int myY) {
    this.myY = myY;
  }

  /**
   * @returns energy of cell
   */
  public int getEnergy() {
    return myEnergy;
  }

  /**
   * @param myEnergy is energy cell is set to
   */
  public void setEnergy(int myEnergy) {
    this.myEnergy = myEnergy;
  }

  /**
   * @returns cells turns
   */
  public int getTurns() {
    return myTurns;
  }

  /**
   * @param myTurns is the turns the cell is being set to
   */
  public void setTurns(int myTurns) {
    this.myTurns = myTurns;
  }


  /**
   * @returns cells vision
   */
  public int getVision() {
    return myVision;
  }

  /**
   * @param myVision is the vision the cell is being set to
   */
  public void setVision(int myVision) {
    this.myVision = myVision;
  }

  /**
   * @returns metabolism of cell
   */
  public int getMetabolism() {
    return myMetabolism;
  }

  /**
   * @param myMetabolism is the metabolism the cell is set to
   */
  public void setMetabolism(int myMetabolism) {
    this.myMetabolism = myMetabolism;
  }
}
