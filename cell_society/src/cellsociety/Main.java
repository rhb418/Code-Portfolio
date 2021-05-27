package cellsociety;

import static cellsociety.Constants.BASIC_STARTER_FILE;

import cellsociety.configuration.ConfigFileHandler;
import cellsociety.configuration.StyleFileHandler;
import cellsociety.configuration.XMLReaderWriter;
import cellsociety.display.SplashScreen;
import cellsociety.evolution.Evolution;
import cellsociety.display.Visualizer;
import java.net.URISyntaxException;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.xml.stream.XMLStreamException;

/**
 * Contains main method for program Cell Society to start
 *
 * Loads splash screen, and then loads the BASIC_STARTER_FILE string, creates the Evolution, then
 * passes it into a new Visualizer for main windows to appear
 *
 * Called by IntelliJ to start program
 *
 * @authors Robert Barnette, Kevin Yang, Felix Jiang
 */
public class Main extends Application {

  private Visualizer myVisualizer;
  private ConfigFileHandler myXMLHandler;
  private StyleFileHandler myStyleHandler;
  private XMLReaderWriter myReader;
  private SplashScreen splash;

  /**
   * Method called by Application to start program
   * @param stage initial Stage given by application
   * @throws URISyntaxException If XML has issues reading BASIC_STARTER_FILE
   */
  public void start(Stage stage) throws URISyntaxException {
    // You can call myReader.getEvolution() to get the evolution class of the type of the file
    createLayout(BASIC_STARTER_FILE);
    splash = new SplashScreen(this, stage);
  }

  /**
   * Called by SplashScreen to start actual simulation
   * @param language Language chosen by user
   * @param stage New stage given to us by SplashScreen
   */
  public void startSim(String language, Stage stage) {
    myVisualizer = new Visualizer(stage, myReader.getEvolution(), this, language);
    splash.closeSplash();
  }

  /**
   * Used to change simulation when user loads a file, called by Visualizer
   * @param filename new filename user wants to see
   * @return new Evolution object representing initial state of that config file
   * @throws URISyntaxException if any issues arise when XMLReader parses file
   */
  public Evolution newSimulation(String filename) throws URISyntaxException {
    createLayout(filename);
    return myReader.getEvolution();
  }

  /**
   * Called by Visualizer to save current evolution (given in Elements list) to a file
   *
   * @param filepath where the user wants to save the file
   * @param elements List representation of a state in Evolution
   * @throws XMLStreamException if issues arise when writing to file
   */
  public void saveLayout(String filepath, List<Integer> elements) throws XMLStreamException {
    myReader.writeCurrentConfiguration(filepath, elements);
  }

  private void createLayout(String filename) throws URISyntaxException {
    myXMLHandler = new ConfigFileHandler(filename);
    myStyleHandler = new StyleFileHandler(filename);
    myReader = (XMLReaderWriter) myXMLHandler.getReader();
  }

  /**
   * main method of program
   *
   * Launches the JavaFX Application class
   * @param args command line arguments given by the user running the program, if any
   */
  public static void main(String[] args) {
    launch(args);
  }
}


