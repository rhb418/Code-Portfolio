/**
 * displays the console and attributes relating to the console
 */
public interface ConsoleDisplay {

  /**
   * returns string that console currently has displayed
   * @return console display
   */

  protected String getConsoleText();

  /**
   * prints error message
   * @param errorMessage to print
   */
  protected void printError(String errorMessage);

  /**
   * has to show previous commands
   * @param commands
   */
  protected void showPreviousCommmands(List<String> commands);

  /**
   * has to show list of previous functions defined
   * @param functions
   */
  protected void showPreviousFunctionsDefined(Map<String, String> functions);
}