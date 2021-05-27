public interface instruction{
  //This method takes in a turtle object and then modifies it based on the instruction that is being implemented
  //It also returns an integer after executing the instruction
  public int executeInstruction(Turtle turtle); 

  //Returns a string representation of the instruction
  public String getInstructionAsString()

  //Returns the number of arguments the instruction can take
  public int getNumArguments();

}