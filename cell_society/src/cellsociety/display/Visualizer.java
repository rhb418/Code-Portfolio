package cellsociety.display;

import static cellsociety.Constants.DUMMY_HEIGHT;
import static cellsociety.Constants.DUMMY_WIDTH;
import static cellsociety.Constants.SECOND_DELAY;

import cellsociety.evolution.Evolution;
import cellsociety.evolution.EvolutionGOL;
import cellsociety.Grid;
import cellsociety.Main;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.xml.stream.XMLStreamException;

/**
 * Handle displaying cells and/or graph in separate windows
 * Visualizer keeps track of all Displayer objects (which represent an open window)
 *
 * Assumes each Evolution object passed in was correctly initiated
 * New calls back to Main (and eventually to XMLReader) are in try-catch blocks to catch
 * various errors such as bad filename or file
 *
 * Depends on Main, Displayer, and Timeline classes directly
 * Depends on XMLReader and UserInputDisplay indirectly
 *
 *    In Main:
 *        myVisualizer = new Visualizer(stage, myReader.getEvolution(), this, language);
 *    In Displayer:
 *        myVisualizer.saveState(filepath, myEvolution);
 *
 * Visualizer is responsible for implementing all desires of the user that correspond to
 * changes that all Displays have, while Displayer is solely responsible for its own display
 *
 * For example, pause will pause all displays, so that is a visualizer task, while switching
 * shapes is displayer-specific
 *
 * @author Felix Jiang
 */
public class Visualizer {

  private List<Displayer> myDisplays;
  private Main sim;
  private boolean isPaused;
  private Timeline animation;
  private String language;
  private double delay;

  /**
   * Constructor for Visualizer
   *
   * Only one created for the entire program at this point
   *
   * Holds all open windows
   *
   * Displays the starter file given by Main first, but new displays open and close throughout it's
   * life cycle
   * @param s Stage for initial Displayer
   * @param e Evolution for initial Displayer
   * @param m Reference back to Main class for calling methods related to XML
   * @param l Language of these Displays
   */
  public Visualizer(Stage s, Evolution e, Main m, String l) {
    this.language = l;
    myDisplays = new ArrayList<>();
    myDisplays.add(new Displayer(s, e, this, language));
    sim = m;
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    delay = SECOND_DELAY;
    setSpeed(delay); // sets animation KeyFrame and plays animation
  }

  /**
   * restart every display we have
   */
  public void restartSimulation() {
    for (Displayer d : myDisplays) {
      d.resetEvolution();
    }
  }

  /**
   * Flip pause boolean and apply it to all displays we have
   */
  public void pause() {
    isPaused = !isPaused;
    for (Displayer d : myDisplays) {
      d.setPause(isPaused);
    }
  }

  /**
   * Get paused state
   * @return boolean if all Displays are paused or not
   */
  public boolean getPause() {
    return isPaused;
  }

  /**
   * Steps forward each Display
   */
  public void stepForward() {
    // assumes userInputDisplayer has paused the visualization
    // will pause just in case
    if (!isPaused) {
      pause();
    }
    for (Displayer d : myDisplays) {
      d.stepForward();
    }
  }

  /**
   * need to change the actual speed and show this change on both sliders
   * @param delay new delay between updating frames
   */
  public void setSpeed(double delay) {
    this.delay = delay;
    for (Displayer d : myDisplays) {
      d.setDelay(delay);
    }
    animation.stop();
    animation.getKeyFrames().clear();
    KeyFrame temp = new KeyFrame(Duration.seconds(delay), e -> updateVisualizer());
    animation.getKeyFrames().add(temp);
    animation.play();
  }

  /**
   * main method to update the visualizer, called by KeyFrame every delay seconds
   *
   * Then calls each Displayer's update method
   */
  public void updateVisualizer() {
    for (Displayer d : myDisplays) {
      d.updateScene();
    }
  }

  /**
   * remove the passed in displayer object from the visualizer
   *
   * Calls Displayer removed method to handle any linking removals
   *
   * @param r Displayer to be removed
   */
  public void removeDisplayer(Displayer r) {
    myDisplays.remove(r);
    r.remove();
  }

  /**
   * makes a new evolution object in a new window
   *
   * @param filename config file with evolution to display
   */
  public void addDisplay(String filename) {
    createNewDisplay(getEvolution(filename));
  }

  /**
   * makes a new evolution object for current window, caller is displayer that initiated the call
   *
   * @param filename for new evolution
   * @param caller Displayer that called to switch display, needs to set initial values the same
   *               as current values
   */
  public void changeDisplay(String filename, Displayer caller) {
    caller.newEvolution(getEvolution(filename));
    setInitialDisplayValues(caller);
  }

  /**
   * Add graph to the displayer that called it
   * @param caller Displayer that wants to have a graph linked to it
   */
  public void addGraph(Displayer caller) {
    Displayer graph = createNewDisplay(caller.getEvolution());
    graph.setAsGraph(caller);
  }

  /**
   * Save filepath and evolution, calls Main class for communication with XML reader writer
   *
   * @param filepath filepath to save file
   * @param toSave Evolution in its current state that wants to be saved
   * @throws XMLStreamException if issues with writing to this file or translating Evolution
   */
  public void saveState(String filepath, Evolution toSave) throws XMLStreamException {
    sim.saveLayout(filepath, toSave.getCellValues());
  }

  private Evolution getEvolution(String filename) {
    try {
      return sim.newSimulation(filename);
    } catch (URISyntaxException e) {
      System.out.println("Failed to open new file");
    }
    return new EvolutionGOL(new Grid(DUMMY_WIDTH, DUMMY_HEIGHT)); // default evolution
  }

  // not all callers will need the returned displayer, only for adding a graph now
  private Displayer createNewDisplay(Evolution e) {
    Stage tempStage = new Stage();
    Displayer tempDisplay = new Displayer(tempStage, e, this, language);
    myDisplays.add(tempDisplay);
    setInitialDisplayValues(tempDisplay);
    return tempDisplay;
  }

  // do everything a new display should have that is same as other displays, such as speed and pause
  private void setInitialDisplayValues(Displayer d) {
    d.setDelay(delay);
    d.setPause(isPaused);
  }

}
