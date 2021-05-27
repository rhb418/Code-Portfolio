package ooga.model.systems;

/**
 * @author Robert Barnette This class is used to represent a particular win or loss condition for a
 * WinComponent
 */
public class WinCondition {

  private String condition;
  private double threshold;
  private boolean win;
  private String operator;

  /**
   * This constructor is used by MOSHI to create WinCondition objects
   */
  public WinCondition() {

  }

  /**
   * @param condition is the type of win condition being created (score, health, enemy)
   * @param threshold is the threshold value defining the condition
   * @param operator  is the operator being used to compare the current value and the threshold (==,
   *                  <, >)
   * @param win       is the boolean specifying if this is a win or loss condition
   */
  public WinCondition(String condition, double threshold, String operator, boolean win) {
    this.condition = condition;
    this.threshold = threshold;
    this.operator = operator;
    this.win = win;
  }

  /**
   * @param input is the current value of the condition
   * @returns a boolean determining if the win or loss condition has been satisfied
   */
  public boolean checkCondition(double input) {
    if (operator.equals("equal")) {
      return input == threshold;
    } else if (operator.equals("lt")) {
      return input < threshold;
    } else if (operator.equals("gt")) {
      return input > threshold;
    }

    return false;
  }

  /**
   * @returns the current condition
   */
  public String getCondition() {
    return condition;
  }


  /**
   * @returns a boolean describing if this is a win condition
   */
  public boolean isWin() {
    return win;
  }

}
