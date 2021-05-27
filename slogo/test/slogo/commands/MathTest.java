package slogo.commands;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static slogo.commands.CommandConstants.COS;
import static slogo.commands.CommandConstants.SIN;
import static slogo.commands.CommandConstants.SUM;
import static slogo.commands.CommandConstants.TAN;

public class MathTest {

  private String [] args;
  private HashMap<String, Double> myVars;


  @BeforeEach
  void setUp(){
    myVars = new HashMap<>();


  }

  @Test
  void testSumWithVariables(){
    args = new String[]{":arg1", ":arg2"};
    myVars.put(args[0], 33.0);
    myVars.put(args[1], 21.0);


    MathOps myMathOps = new MathOps(args, SUM);
    double test = myMathOps.executeCommand(null, myVars);

    assertEquals(test, (double) 33+21);
  }

  @Test
  void testTrig(){
    args = new String[]{"45"};
    MathOps math1 = new MathOps(args, COS);
    MathOps math2 = new MathOps(args, SIN);
    MathOps math3 = new MathOps(args, TAN);

    double result1 = math1.executeCommand(null,myVars);
    double result2 = math2.executeCommand(null,myVars);
    double result3 = math3.executeCommand(null,myVars);


    assertEquals(result1, Math.cos(Math.toRadians(45)));
    assertEquals(result2, Math.sin(Math.toRadians(45)));
    assertEquals(result3, Math.tan(Math.toRadians(45)));

  }


}
