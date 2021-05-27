//FrontEndController: Can be called by the frontend to send the next command inputted in the ConsoleDisplay

//Responsibilities: sending commands inputted in the console to be parsed by the backend

//Dependencies: View, Parser


public interface FrontEndController{
  //Called by the Display to send the next command to the parser
  public void getNextCommand(String command);
  
  //Called by the Display to change the language that commands are parsed in
  public String getLanguage();
}
