package slogo.commands;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.turtle.Turtle;
import static org.junit.jupiter.api.Assertions.*;
import static slogo.commands.CommandConstants.YCOR;

public class TurtleQueryTest {
  Turtle myTurtle;
  HashMap<String, Double> vars;
  @BeforeEach
  void setUp(){
    myTurtle = new Turtle(0);
    myTurtle.setPosition(23, 80);
    myTurtle.rotate(35);
    myTurtle.setPenState(false);
    myTurtle.setShowing(false);
    vars = new HashMap<>();

  }

  @Test
  void testQueries(){
    int optype = YCOR;

    TurtleQueries tq = new TurtleQueries(new String[]{},optype);
    double val = tq.executeCommand(myTurtle,vars);

    assertEquals(val, 80);
  }

}
