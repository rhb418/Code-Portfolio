//This class parses the input string into a command
public interface Parser{

  //Attempts to parse a string into a given command
  public void parseCommand(String command);

  //Returns the command that was parsed
  public Command getCommand();

  //Returns a string describing the error if an error occured during parsing
  public String getError(); 
}