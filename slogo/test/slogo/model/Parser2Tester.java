package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.commands.Command;
import slogo.model.parser.Parser2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Parser2Tester {

  private static final String FILE_PATH = System.getProperty("user.dir") + "/test/slogo/model/Parser2Tests.txt";
  private static final String FILE_DIR = System.getProperty("user.dir") + "/data/examples/procedures_with_parameters/";

  private List<String> myInput;
  private File myFile;
  private Scanner myScanner;


  @BeforeEach
  void initialize() throws FileNotFoundException {
    myFile = new File(FILE_PATH);
    myInput = new ArrayList<>();
    myScanner = new Scanner(myFile);
    while (myScanner.hasNextLine()){
      myInput.add(myScanner.nextLine());
    }
  }

  @Test
  void checkCommandParseWithoutError() {
    boolean exceptionCaught = false;
    try {
      for (String input : myInput) {
        Parser2 parserA = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
        List<Command> myCommands = parserA.newParser();
      }
    }
    catch (Exception e) {
      exceptionCaught = true;
    }

    assertFalse(exceptionCaught);
  }

  @Test
  void checkCommandParse() {
    for (String input : myInput) {
      Parser2 parserA = new Parser2(input, new HashMap<>(), new HashMap<>(), new HashMap<>(), "English" );
      List<Command> myCommands = parserA.newParser();
      assertNotEquals(myCommands.size(), 0);
      assertNotNull(myCommands.get(0));
    }

  }


  @Test
  void checkExampleFileParseWithoutError() {
    boolean exceptionCaught = false;
    try {
      File dir = new File(FILE_DIR);
      File[] directoryListing = dir.listFiles();
      if (directoryListing != null) {
        for (File child : directoryListing) {
          myInput = new ArrayList<>();
          myScanner = new Scanner(child);
          StringBuilder fileStream = new StringBuilder();
          while (myScanner.hasNextLine()){
            fileStream.append(myScanner.nextLine()).append("\n");
          }
          Parser2 parserA = new Parser2(fileStream.toString(), new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
          List<Command> myCommands = parserA.newParser();
        }
      }

    }
    catch (Exception e) {
      exceptionCaught = true;
    }

    assertFalse(exceptionCaught);
  }

  @Test
  void checkExampleFileParse() {
    try{

      File dir = new File(FILE_DIR);
      File[] directoryListing = dir.listFiles();
      if (directoryListing != null) {
        for (File child : directoryListing) {
          myInput = new ArrayList<>();
          myScanner = new Scanner(child);
          StringBuilder fileStream = new StringBuilder();
          while (myScanner.hasNextLine()){
            fileStream.append(myScanner.nextLine()).append("\n");
          }
          Parser2 parserA = new Parser2(fileStream.toString(), new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
          List<Command> myCommands = parserA.newParser();
          assertNotEquals(myCommands.size(), 0);
          assertNotNull(myCommands.get(0));
        }
      }
    }
    catch (Exception e) {
      return;
    }

  }


  @Test
  void checkErrorForGrouping()  {
    Parser2 parserA = new Parser2(" ( ) ", new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
    List<Command> myCommands = parserA.newParser();
    assertNull(myCommands);
    assertEquals(parserA.getErrorMessages(), "Error parsing group commands");
  }

  @Test
  void checkErrorForIfCommand() {
    Parser2 parserA = new Parser2("if", new HashMap<>(), new HashMap<>(), new HashMap<>(), "English");
    List<Command> myCommands = parserA.newParser();
    assertNull(myCommands);
    assertEquals(parserA.getErrorMessages(), "Error parsing control commands");
  }
}
