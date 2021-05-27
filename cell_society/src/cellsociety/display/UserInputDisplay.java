package cellsociety.display;

import static cellsociety.Constants.CONFIG_FILE_PATH;
import static cellsociety.Constants.DARK_COMMAND;
import static cellsociety.Constants.DEFAULT_COMMAND;
import static cellsociety.Constants.FIELD_HEIGHT;
import static cellsociety.Constants.FIELD_WIDTH;
import static cellsociety.Constants.FINITE;
import static cellsociety.Constants.FINITE_PROMPT;
import static cellsociety.Constants.GRAPH_PROMPT;
import static cellsociety.Constants.GRIDLINES_PROMPT;
import static cellsociety.Constants.HEX_DROP;
import static cellsociety.Constants.LIGHT_COMMAND;
import static cellsociety.Constants.MAX_FPS;
import static cellsociety.Constants.MIN_FPS;
import static cellsociety.Constants.RECT_CLOSE_DROP;
import static cellsociety.Constants.RECT_DROP;
import static cellsociety.Constants.SAME_WINDOW_NEW_FILE;
import static cellsociety.Constants.ADD_SIM_PROMPT;
import static cellsociety.Constants.ITEM_SPACING;
import static cellsociety.Constants.PAUSE;
import static cellsociety.Constants.RESTART;
import static cellsociety.Constants.RESUME;
import static cellsociety.Constants.SAVE_PROMPT;
import static cellsociety.Constants.STEP;
import static cellsociety.Constants.NEW_WINDOW_NEW_FILE;
import static cellsociety.Constants.CHANGE_SIM_PROMPT;
import static cellsociety.Constants.TOROIDAL;
import static cellsociety.Constants.TOROIDAL_PROMPT;
import static cellsociety.Constants.TRI_DROP;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Displays all buttons and fields that the User can change for the program
 *
 * Is both the view and controller, as each button has an event handler that calls Displayer or
 * Visualizer methods
 *
 * Calls to Displayer are for window specific changes, while calls to Visualizer change all open
 * Displays
 *
 *    myControlDisplayer = new UserInputDisplay(this, vis, myResources);
 *
 * @author Felix Jiang
 */
public class UserInputDisplay {

  private VBox root;
  private HBox top;
  private HBox middle;
  private HBox bottom;
  private Displayer myDisplayer;
  private Visualizer myVisualizer;
  private Button pauseButton;
  private Button restartButton;
  private Button stepButton;
  private Slider slider;
  private Button addSimButton;
  private Button changeSimButton;
  private Button graphButton;
  private Button edgeButton;
  private Button gridlinesButton;
  private Button saveButton;
  private ComboBox shapeDropdown;
  private ComboBox styleDropdown;
  private double initialDelay;
  private ResourceBundle myResources;
  private Map<String, TextField> paramFields;
  private Map<String, Double> currParams;

  /**
   * Create a UserInputDisplay for one stage
   *
   * @param myDisplayer displayer for these buttons to be on
   * @param myVisualizer main visualizer of the program
   * @param r to get text for the buttons
   */
  public UserInputDisplay(Displayer myDisplayer, Visualizer myVisualizer, ResourceBundle r) {
    this.myDisplayer = myDisplayer;
    this.myVisualizer = myVisualizer;
    this.myResources = r;
  }

  /**
   * Set the JavaFX items that actually display the buttons
   *
   * Called by Displayer which gives the VBox to this method
   *
   * @param vb given by displayer for all the buttons to go
   * @param d initial delay for this slider
   */
  public void setUserDisplay(VBox vb, double d) {
    root = vb;
    top = new HBox();
    middle = new HBox();
    bottom = new HBox();
    initialDelay = d;
    setControls();
    root.getChildren().clear();
    drawControls();
  }

  /**
   * Set delay of slider so all displays have the same delay
   *
   * delay: time between each change in simulation
   *
   * Slider displays FPS so that as you drag to the right, the speed increases
   * @param delay value to be set on the slider first
   */
  public void setDelay(double delay) {
    slider.setValue(1 / delay);
  }

  /**
   * what happens when a user presses the pause button
   * @param isPaused what value of pause the button should say
   */
  public void setPause(boolean isPaused) {
    if (isPaused) {
      pauseButton.setText(myResources.getString(RESUME));
    } else {
      pauseButton.setText(myResources.getString(PAUSE));
    }
  }

  /**
   * sets default value for shape dropdown
   * @param value default value on the shape dropdown to be set
   */
  public void setShapeDropdownValue(int value) {
    shapeDropdown.getSelectionModel().select(value);
  }

  /**
   * sets default value for style dropdown
   * @param value default value on the style dropdown to be set
   */
  public void setStyleDropdownValue(int value) {
    styleDropdown.getSelectionModel().select(value);
  }

  /**
   * sets button text for edge button
   *
   * @param value corresponds to constant for finite or toroidal edge type
   */
  public void setEdgeButtonValue(int value) {
    if (value == TOROIDAL) {
      edgeButton.setText(myResources.getString(TOROIDAL_PROMPT));
    }
    if (value == FINITE) {
      edgeButton.setText(myResources.getString(FINITE_PROMPT));
    }
  }

  /**
   * remove controls that a graph does not need
   */
  public void setAsGraph() {
    middle.getChildren().remove(shapeDropdown);
    bottom.getChildren().remove(graphButton);
    middle.getChildren().remove(gridlinesButton);
    middle.getChildren().remove(edgeButton);
    middle.getChildren().remove(saveButton);
  }

  /**
   * Given param fields to create all the text fields for each parameter
   *
   * Adds each new text field to the middle HBox
   *
   * Does not apply to all simulations, only ones that have parameters
   *
   * @param params map of parameter name to its value for the simulation
   */
  public void setParamFields(Map<String, Double> params) {
    paramFields = new HashMap<>();
    currParams = params;
    for (Entry<String, Double> e : currParams.entrySet()) {
      paramFields.put(e.getKey(), setTextField(e.getKey()));
      middle.getChildren().add(paramFields.get(e.getKey()));
    }
  }

  private void setControls() {
    setPauseButton();
    setRestartButton();
    setStepButton();
    setSlider();
    setAddSimButton();
    setChangeSimButton();
    setGraphButton();
    setShapeDropdown();
    setEdgeButton();
    setStyleDropdown();
  }

  private void drawControls() {
    root.getChildren().add(top);
    root.getChildren().add(middle);
    root.getChildren().add(bottom);
    top.getChildren().add(pauseButton);
    top.getChildren().add(restartButton);
    top.getChildren().add(stepButton);
    middle.getChildren().add(shapeDropdown);
    middle.getChildren().add(edgeButton);
    middle.getChildren().add(setGridlinesButton());
    middle.getChildren().add(setSaveButton());
    middle.getChildren().add(styleDropdown);
    bottom.getChildren().add(addSimButton);
    bottom.getChildren().add(changeSimButton);
    bottom.getChildren().add(graphButton);
    top.getChildren().add(slider);
    top.setAlignment(Pos.CENTER);
    middle.setAlignment(Pos.CENTER);
    bottom.setAlignment(Pos.CENTER);
    handleMargins();
  }

  private void setPauseButton() {
    pauseButton = new Button();
    pauseButton.setText(myResources.getString(PAUSE));
    pauseButton.setOnAction(e -> pause());
  }

  private void setRestartButton() {
    restartButton = new Button();
    restartButton.setText(myResources.getString(RESTART));
    restartButton.setOnAction(e -> myVisualizer.restartSimulation());
  }

  private void setStepButton() {
    stepButton = new Button();
    stepButton.setText(myResources.getString(STEP));
    stepButton.setOnAction(e -> step());
  }

  private TextField setTextField(String prompt) {
    TextField field = new TextField();
    field.setPromptText(myResources.getString(prompt));
    field.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
    field.setOnKeyPressed(e -> getText(e.getCode()));
    HBox.setMargin(field, ITEM_SPACING);
    return field;
  }

  private void setSlider() {
    slider = new Slider();
    // slider value is the fps
    slider.setMin(MIN_FPS);
    slider.setMax(MAX_FPS);
    slider.setValue(1 / initialDelay);
    slider.valueProperty().addListener((obs, oldVal, newVal) -> updateSpeed(newVal));
  }

  private void setAddSimButton() {
    addSimButton = new Button();
    addSimButton.setText(myResources.getString(ADD_SIM_PROMPT));
    addSimButton.setOnAction(e -> openFileChooser(NEW_WINDOW_NEW_FILE));
  }

  private void setChangeSimButton() {
    changeSimButton = new Button();
    changeSimButton.setText(myResources.getString(CHANGE_SIM_PROMPT));
    changeSimButton.setOnAction(e -> openFileChooser(SAME_WINDOW_NEW_FILE));
  }

  private void setGraphButton() {
    graphButton = new Button();
    graphButton.setText(myResources.getString(GRAPH_PROMPT));
    graphButton.setOnAction(e -> myVisualizer.addGraph(myDisplayer));
  }

  private void setShapeDropdown() {
    String[] shapes = {myResources.getString(RECT_DROP), myResources.getString(RECT_CLOSE_DROP),
        myResources.getString(TRI_DROP), myResources.getString(HEX_DROP)};
    shapeDropdown = new ComboBox(FXCollections.observableArrayList(shapes));
    shapeDropdown.setOnAction(
        e -> myDisplayer.changeShape(shapeDropdown.getSelectionModel().getSelectedIndex()));
  }

  private void setStyleDropdown() {
    String[] styles = {myResources.getString(DEFAULT_COMMAND), myResources.getString(DARK_COMMAND),
        myResources.getString(LIGHT_COMMAND)};
    styleDropdown = new ComboBox(FXCollections.observableArrayList(styles));
    styleDropdown.setOnAction(
        e -> myDisplayer.setStyle(styleDropdown.getSelectionModel().getSelectedIndex()));
  }

  private void setEdgeButton() {
    edgeButton = new Button();
    edgeButton.setOnAction(e -> myDisplayer.switchEdgeType());
  }

  // first button that doesn't have a private instance variable
  private Button setGridlinesButton() {
    gridlinesButton = new Button();
    gridlinesButton.setText(myResources.getString(GRIDLINES_PROMPT));
    gridlinesButton.setOnAction(e -> myDisplayer.gridlines());
    HBox.setMargin(gridlinesButton, ITEM_SPACING);
    return gridlinesButton;
  }

  private Button setSaveButton() {
    saveButton = new Button();
    saveButton.setText(myResources.getString(SAVE_PROMPT));
    saveButton.setOnAction(e -> saveFileChooser());
    HBox.setMargin(saveButton, ITEM_SPACING);
    return saveButton;
  }

  private void saveFileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(CONFIG_FILE_PATH));
    File chosen = fileChooser.showSaveDialog(new Stage());
    if (chosen != null) {
      myDisplayer.saveState(chosen.getAbsolutePath());
    }
  }

  private void openFileChooser(int type) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(CONFIG_FILE_PATH));
    File chosen = fileChooser.showOpenDialog(new Stage());
    if (chosen != null) {
      if (type == NEW_WINDOW_NEW_FILE) {
        myVisualizer.addDisplay(chosen.getName());
      }
      if (type == SAME_WINDOW_NEW_FILE) {
        myVisualizer.changeDisplay(chosen.getName(), myDisplayer);
      }
    }
  }

  private void updateSpeed(Number newVal) {
    myVisualizer.setSpeed(1 / newVal.doubleValue());
  }

  private void getText(KeyCode e) {
    if (e == KeyCode.ENTER) {
      for (Entry<String, TextField> entry : paramFields.entrySet()) {
        TextField field = entry.getValue();
        if ((field.getText() != null && !field.getText().isEmpty())) {
          try {
            currParams.put(entry.getKey(), Double.parseDouble(field.getText()));
          } catch (NumberFormatException ex) {
            myDisplayer.displayError(ex.getMessage());
          }
        }
      }
      myDisplayer.setParameters(currParams);
    }
  }

  private void handleMargins() {
    HBox.setMargin(pauseButton, ITEM_SPACING);
    HBox.setMargin(restartButton, ITEM_SPACING);
    HBox.setMargin(stepButton, ITEM_SPACING);
    HBox.setMargin(slider, ITEM_SPACING);
    HBox.setMargin(addSimButton, ITEM_SPACING);
    HBox.setMargin(changeSimButton, ITEM_SPACING);
    HBox.setMargin(graphButton, ITEM_SPACING);
    HBox.setMargin(shapeDropdown, ITEM_SPACING);
    HBox.setMargin(edgeButton, ITEM_SPACING);
    HBox.setMargin(styleDropdown, ITEM_SPACING);
  }

  // what happens when user clicks pause/unpause
  private void pause() {
    myVisualizer.pause();
    setPause(myVisualizer.getPause());
  }

  private void step() {
    if (!myVisualizer.getPause()) {
      pause();
    } else {
      myVisualizer.stepForward();
    }
  }

}
