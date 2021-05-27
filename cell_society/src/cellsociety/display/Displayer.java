package cellsociety.display;

import static cellsociety.Constants.CELL_STROKE_PROPORTION;
import static cellsociety.Constants.COLOR_MAP;
import static cellsociety.Constants.CONTROL_HEIGHT;
import static cellsociety.Constants.DEFAULT;
import static cellsociety.Constants.FINITE;
import static cellsociety.Constants.HEXAGONAL;
import static cellsociety.Constants.ITEM_SPACING;
import static cellsociety.Constants.PROPERTIES;
import static cellsociety.Constants.SECOND_DELAY;
import static cellsociety.Constants.SIZE;
import static cellsociety.Constants.STYLES;
import static cellsociety.Constants.STYLESHEET;
import static cellsociety.Constants.TOROIDAL;
import static cellsociety.Constants.TRIANGULAR;

import cellsociety.evolution.Evolution;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.xml.stream.XMLStreamException;

/**
 * Represents a single window that is open in the program
 *
 * Assumes Visualizer will update parameters as needed
 *
 * Depends on Evolution, UserInputDisplay, Drawer and Visualizer
 *
 *      myDisplays.add(new Displayer(s, e, this, language));
 *
 * Handles all changes specific to a single window, not for changing state of all windows (that is
 * visualizer's job)
 *
 * Communicates to both UserInputDisplay and Visualizer in order to create the changes the user
 * desires
 *
 * @author Felix Jiang
 */
public class Displayer {

  private Evolution myEvolution;
  private Displayer linked;
  private boolean isMain; // the one that calls update is main if two display objects share an evolution
  private boolean isRemoved; // true if displayer has been removed from visualizer
  private boolean firstDisplay; // resize display on launch so all buttons are seen
  private BorderPane root;
  private StackPane top;
  private Pane cellPane;
  private VBox controlPane;
  private Stage myStage;
  private Scene myScene;
  private UserInputDisplay myControlDisplayer;
  private boolean isPaused;
  private final Visualizer vis;
  private double cellPaneHeight;
  private double cellPaneWidth;
  private ResourceBundle myResources;
  private Drawer myDrawer;
  private boolean hasGridlines;

  /**
   * Constructor for creating new windows
   *
   * Assumes stage is not being used currently
   *
   * @param s Stage for showing the current scene
   * @param e Evolution object holding data on cell's
   * @param i Visualizer object to reference for communication
   * @param language language of text that displays on each node
   */
  public Displayer(Stage s, Evolution e, Visualizer i, String language) {
    linked = null;
    isMain = false;
    isRemoved = false;
    hasGridlines = true;
    firstDisplay = true;
    myStage = s;
    vis = i;
    myStage.setOnCloseRequest(closeEvent -> vis.removeDisplayer(this));
    cellPaneHeight = SIZE;
    cellPaneWidth = SIZE;
    myResources = ResourceBundle.getBundle(PROPERTIES + language);

    root = new BorderPane();
    myScene = new Scene(root, SIZE, SIZE + CONTROL_HEIGHT);
    myStage.setScene(myScene);
    myStage.show();

    myControlDisplayer = new UserInputDisplay(this, vis, myResources);

    newEvolution(e); // depends on root and animation being set first
  }

  /**
   * Sets style for this Display
   *
   * Assumes control displayer has already been set, since it calls a UserInputDisplay method
   *
   * @param index index into STYLES array referring to CSS file names for each style
   */
  public void setStyle(int index) {
    myScene.getStylesheets().clear();
    myScene.getStylesheets()
        .add(getClass().getResource(STYLESHEET + STYLES[index]).toExternalForm());
    myControlDisplayer.setStyleDropdownValue(index);
  }

  /**
   * Getter for evolution object
   *
   * This was needed for syncing a graph, and the Visualizer class is the only place that calls it
   * Visualizer needs the same reference to the evolution object so graph is in sync right away
   *
   * The method behavior could not be changed to achieve the same function since Visualizer needs
   * to reference this Displayer to know when to unlink if it's deleted
   *
   * @return Evolution object that will be used to link this display to a graphDrawer Display
   */
  public Evolution getEvolution() {
    return myEvolution;
  }

  /**
   * Used to restart simulation
   *
   * When user clicks restart, this method called in order to put evolution back to initial config
   */
  public void resetEvolution() {
    myEvolution.reset();
  }

  /**
   * Called by visualizer to step the simulation forward by creating next generation
   *
   * Update method will handle redrawing
   */
  public void stepForward() {
    if (shouldUpdate()) {
      myEvolution.createNewGeneration(); // steps one ahead
    }
  }

  /**
   * set methods help set the display so they all look the same (set pause and set delay)
   *
   * Called by Visualizer to pause each display at the same time
   *
   * @param pause represents if display should be paused or not
   */
  public void setPause(boolean pause) {
    isPaused = pause;
    myControlDisplayer.setPause(isPaused);
  }

  /**
   * Set delay of the corresponding UserInputDisplay so all speeds on the slider are matched
   *
   * @param delay equal to 1/FPS for how fast the scene should update
   */
  public void setDelay(double delay) {
    myControlDisplayer.setDelay(delay);
  }

  /**
   * What changes when a new simulation starts or restarts
   *
   * Things in the constructor stay the same, while things in here change between new simulations
   *
   * @param e new simulation to be displayed
   */
  public void newEvolution(Evolution e) {
    myEvolution = e;
    drawDisplay(); // throws away old panes so no need to worry about clearing all items currently on root
    myControlDisplayer.setUserDisplay(controlPane, SECOND_DELAY);
    myControlDisplayer.setParamFields(myEvolution.getSimulationParameters());
    myControlDisplayer.setEdgeButtonValue(e.getEdgeType());
    setDrawer(e.getShape()); // default shape of evolution
    setStyle(DEFAULT); // default style of display
  }

  /**
   * Change shape of simulation
   *
   * Called by UserInputDisplay
   *
   * @param shape new shape in integer form, mapped to a specific shape such as hexagon
   */
  public void changeShape(int shape) {
    setDrawer(shape); // visually change shape
    myEvolution.setShape(shape); // backend change shape
  }

  /**
   *  UserInputDisplay calls to switch edge type both backend and frontend
   */
  public void switchEdgeType() {
    int newType = FINITE;
    if (myEvolution.getEdgeType() == FINITE) {
      newType = TOROIDAL;
    }
    myEvolution.setEdgeType(newType);
    myControlDisplayer.setEdgeButtonValue(newType);
  }

  /**
   * UserInputDisplay calls this method to change gridlines
   */
  public void gridlines() {
    hasGridlines = !hasGridlines;
  }

  /**
   * Called by UserInputDisplay to save current board to a file
   * @param filepath where saved file goes
   */
  public void saveState(String filepath) {
    try {
      vis.saveState(filepath, myEvolution);
    } catch (XMLStreamException e) {
      displayError(e.getMessage());
    }
  }

  /**
   * Sets this displayer as a graph, while nonGraph is the one displaying cells
   *
   * @param nonGraph Displayer that will be displaying cells, the main display
   */
  public void setAsGraph(Displayer nonGraph) {
    myDrawer = new GraphDrawer();
    myControlDisplayer.setAsGraph();
    this.makeSecondary(nonGraph);
    nonGraph.makeMain(this);
  }

  /**
   * Primary update method called by KeyFrame every delay amount of time
   *
   * Handles whether this Display is linked or not and what to do in each case
   */
  public void updateScene() {
    if (linked == null) {
      // only need to display itself
      cellPane.getChildren().clear();
      myDrawer
          .displayCurrentGeneration(myEvolution, cellPaneWidth, cellPaneHeight, this);
    } else {
      // has a link
      linkedUpdateScene();
    }
    if (!isPaused && shouldUpdate()) {
      myEvolution.createNewGeneration();
    }
    if (firstDisplay) {
      firstDisplay = false;
      changeSize();
    }
  }

  // if has a link, need to call this update method
  private void linkedUpdateScene() {
    if (isMain) {
      // display itself and its link
      cellPane.getChildren().clear();
      myDrawer.displayCurrentGeneration(myEvolution, cellPaneWidth, cellPaneHeight, this);
      linked.linkedDisplayCurrentGeneration(myEvolution);
    }
    // link will not update itself
  }

  // pass in the exact same evolution object at the same time, so hopefully they are in sync
  private void linkedDisplayCurrentGeneration(Evolution same) {
    cellPane.getChildren().clear();
    myDrawer.displayCurrentGeneration(same, cellPaneWidth, cellPaneHeight, this);
  }

  // remove link on this displayer if it has one
  // one of the displayers is being removed, doesn't really matter if it's main or not, we need to remove the link between them so the other one can update

  /**
   * Remove link on this displayer if it has one
   *
   * One of the displays is being removed, doesn't really matter if it's main or not, we need to
   * remove the link between them so the other one can update
   *
   * Also need to prevent dead ones from calling update evolution
   *
   */
  public void remove() {
    // remove this displayer and check for linkers to unlink
    isRemoved = true;
    if (linked != null) {
      if (isMain) {
        linked.makeSolo(myEvolution);
      } else {
        linked.makeSolo(null);
      }
      // don't need to set linked to null because this displayer will not have any references to it soon
    }
  }

  /**
   * paints the shape drawn by rectangle, triangle, and hexagon drawers
   *
   * sets mouse click event handlers
   *
   * @param cellLength for proportion of the stroke width
   * @param k row index for evolution to change when user clicks on this cell being drawn
   * @param j col index for evolution to change when user clicks on this cell being drawn
   * @param shape Used to add eventHandler to it, superclass of Rectangle and Polygon
   */
  public void paintShape(double cellLength, int k, int j, Shape shape) {
    int cellValue = myEvolution.getCurrentGeneration().getCell(j, k).getValue();
    shape.setFill(COLOR_MAP[cellValue]);
    if (hasGridlines) {
      shape.setStroke(Color.BLACK);
      shape.setStrokeWidth(cellLength / CELL_STROKE_PROPORTION);
    }
    int finalK = k;
    int finalJ = j;
    shape.setOnMouseClicked(e -> clickedCell(finalK, finalJ));
    cellPane.getChildren().add(shape);
  }

  /**
   * Called by UserInputDisplay to set parameters of an evolution
   *
   * @param newParams map of new parameters that backend understands
   */
  public void setParameters(Map<String, Double> newParams) {
    myEvolution.setSimulationParameters(newParams);
  }

  /**
   * Adds a node to the center pane
   * Used by graphDrawer to add a pie chart to center pane
   *
   * @param add pieChart to be added
   */
  public void addToCenterPane(Node add) {
    cellPane.getChildren().add(add);
  }

  /**
   * display error message to user if something isn't working
   *
   * Currently only displays to the top part of a display, not to a separate window
   *
   * @param error the message to be displayer
   */
  public void displayError(String error) {
    top.getChildren().clear();
    top.getChildren().add(new Text(error));
  }

  private void clickedCell(int row, int column) {
    myEvolution.switchCellOnClick(column, row);
  }

  // simply sets the drawer type, needs to handle graph eventually
  private void setDrawer(int shape) {
    cellPane.getChildren().clear();
    myControlDisplayer.setShapeDropdownValue(shape);
    switch (shape) {
      case HEXAGONAL -> myDrawer = new HexagonDrawer();
      case TRIANGULAR -> myDrawer = new TriangleDrawer();
      default -> myDrawer = new RectangleDrawer(); // handles rectangle and rectangle close
    }
  }

  // check if this displayer should update based on if it is linked or not and if it is main or not
  // and if the display has been removed or not
  private boolean shouldUpdate() {
    return !isRemoved && (linked == null || isMain);
  }

  // no longer linked (could be graph or cells) so update will update evolution
  private void makeSolo(Evolution e) {
    if (e != null) {
      myEvolution = e; // so whatever evolution is changed, the graph will still have the same evolution
    }
    if (linked != null) {
      linked = null;
      isMain = false;
    }
  }

  // linker is the non main, while the object this is called on is the main
  private void makeMain(Displayer linker) {
    isMain = true;
    linked = linker;
  }

  // for handling graphs that share an evolution with a displayer, but later being able to run on its own
  private void makeSecondary(Displayer linker) {
    isMain = false;
    linked = linker;
  }

  private void changeSize() {
//    System.out.println("Window:  Height " + root.getHeight() + " Width " + root.getWidth());
//    System.out.println("CellPane:  Height " + cellPane.getHeight() + " Width " + cellPane.getWidth());

    // cellPaneWidth sometimes is greater than window width, so need to use root width for more
    // accurate display. However, cellPane height is accurate when shrunk
    cellPaneWidth = root.getWidth();
    cellPaneHeight = cellPane.getHeight();
    cellPane.getChildren().clear();
  }

  private void drawDisplay() {
    setUpPanes();
    setUpStageListeners();
  }

  // assumes evolution already set
  private void setUpPanes() {
    top = new StackPane();
    cellPane = new Pane();
    controlPane = new VBox();
    root.setBottom(controlPane);
    root.setCenter(cellPane);
    root.setTop(top);
    Text name = new Text(myEvolution.getDescription());
    name.setWrappingWidth(SIZE);
    StackPane.setMargin(name, ITEM_SPACING);
    top.setAlignment(Pos.CENTER);
    top.getChildren().add(name);
    myStage.setTitle(myEvolution.getTitle());
  }

  // tell cells to update size based on changes in stage size by user
  private void setUpStageListeners() {

    // https://stackoverflow.com/questions/38216268/how-to-listen-resize-event-of-stage-in-javafx
    ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
      changeSize();
    };

    myStage.widthProperty().addListener(stageSizeListener);
    myStage.heightProperty().addListener(stageSizeListener);
  }
}
