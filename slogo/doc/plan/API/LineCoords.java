///LineCoords: keeps track of the characteristics that define a line drawn by the turtle

///Responsibilities: Knowing the start and endpoint of a line drawn by the turtle and giving that information to the view 

///Dependencies: None


public interface LineCoords{
  //returns the x starting position of the line
  public double getXStartPosition();
  //returns the y starting position of the line
  public double getYStartPosition();
  //returns the x ending position of the line
  public double getXEndPosition();
  //returns the y ending position of the line
  public double getYEndPosition();
}
