
//Model: Receives commands from the parser and updates turtle/variables accordingly


//Dependencies: Turtle, Command

public interface Model {
  //Stores variables of Turtle as a Pair
  protected void storeVariable(Pair<String, String> variable);

  //Stores commands of user as a Pair
  protected void storeUserDefinedCommand(Pair<String, String> userCommand);

  //Stores user command as a string
  protected void storeCommand(String command);

  //Executes the user command after changing from a String to a Command object
  protected void executeCommand(Command command);
}