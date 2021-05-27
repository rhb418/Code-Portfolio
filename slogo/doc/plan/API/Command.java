//A command is a single line of code consisting of one or more instructions

public interface command{

  //Executes the command on a turtle and then returns the result of a command
  public int executeCommand(Turtle turtle);

  //Returns a command in string form
  public String getCommandString();

}