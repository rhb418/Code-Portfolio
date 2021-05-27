package slogo.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.parser.DataHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataHandlerTester {



  private List<String> myInput;
  private File myFile;
  private Scanner myScanner;

  @BeforeEach
  void initialize() throws FileNotFoundException {

  }

  @Test
  void testUrduRepeat(){
    DataHandler myDataHandler = new DataHandler("urdu");
    String urdoCommand = "phirse 2 [ dayein 50 ]";
    String[] translated = myDataHandler.languageConverter(urdoCommand.split(" "));
    String[] expected = {"Repeat","2","[","Right","50","]"};
    assertEquals(translated[0],expected[0]);
    assertEquals(translated[1],expected[1]);
    assertEquals(translated[2],expected[2]);
    assertEquals(translated[3],expected[3]);
    assertEquals(translated[4],expected[4]);
    assertEquals(translated[5],expected[5]);
  }

  @Test
  void testSpanishMathOps(){
    DataHandler myDataHandler = new DataHandler("Spanish");
    String spanishCommand = "diferencia 30 producto 100 10";
    String[] translated = myDataHandler.languageConverter(spanishCommand.split(" "));
    String[] expected = {"Difference","30","Product","100","10"};
    assertEquals(translated[0],expected[0]);
    assertEquals(translated[1],expected[1]);
    assertEquals(translated[2],expected[2]);
    assertEquals(translated[3],expected[3]);
    assertEquals(translated[4],expected[4]);
  }

}
