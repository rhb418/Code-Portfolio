package slogo.view;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import slogo.controller.BackEndController;

import static org.junit.jupiter.api.Assertions.*;
import static slogo.Main.CSS_FILE;
import static slogo.Main.HEIGHT;
import static slogo.Main.WIDTH;

public class FrontEndTest extends util.DukeApplicationTest {

  // keep any useful elements whose values you want to test directly in multiple tests
  private TextArea codeEntryBox;
  private Button enterCommandButton;

  private TextArea consoleTextBox;

  private Node newTabButton;

  private TabPane sceneBuilder;
  private Pane turtleDisplay;

  private MenuButton languageMenuButton;
  private MenuButton penMenuButton;
  private MenuButton lineColorMenuButton;
  private MenuButton backgroundColorMenuButton;
  private MenuButton turtleShapeMenuButton;
  private MenuButton turtleMenuButton;

  private BackEndController backEndController;

  /**
   * Start test version of application (note this is basically just a copy of the Main class's start() method).
   */
  @Override
  public void start (Stage stage) {

    Parent root = new SceneBuilder().initialize();
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    scene.getStylesheets().add(CSS_FILE);
    stage.setScene(scene);
    stage.show();

    newTabButton = lookup("#NewTabButton").query();
    sceneBuilder = lookup("#SceneBuilder").query();

    turtleDisplay = lookup("#TurtleDisplayContent").query();
    consoleTextBox = lookup("#ConsoleTextBox").query();
    codeEntryBox = lookup("#CodeEntryBox").query();
    enterCommandButton = lookup("#SubmitCommandButton").query();

    languageMenuButton = lookup("#LanguageMenuButton").query();
    penMenuButton = lookup("#PenMenuButton").query();
    turtleShapeMenuButton = lookup("#TurtleShapeMenuButton").query();
    lineColorMenuButton = lookup("#LineColorMenuButton").query();
    backgroundColorMenuButton = lookup("#BackGroundColorMenuButton").query();
    turtleMenuButton = lookup("#TurtleMenuButton").query();
  }




  @Test
  // scenario: Creates a new tab
  void openAndCloseTab() {
    clickOn(newTabButton);
    assertEquals(sceneBuilder.getTabs().size(), 3);
  }

  @Test
  // scenario: check language menu button creation
  void checkLanguageMenuButtonOptions() {
    ResourceBundle myBundle = ResourceBundle.getBundle("slogo/resources/toolbar/menuButton/Language");
    int size = myBundle.keySet().size();
    assertEquals(size,languageMenuButton.getItems().size());
  }

  @Test
  // scenario: check pen menu button creation
  void checkPenMenuButtonOptions(){
    ResourceBundle myBundle = ResourceBundle.getBundle("slogo/resources/toolbar/menuButton/pen/English");
    int size = myBundle.keySet().size();
    assertEquals(size,penMenuButton.getItems().size());
  }

  @Test
  // scenario: check id of menu button selections
  void checkLanguageMenuSelection() {
    String expected = "0";
    String actual = languageMenuButton.getItems().get(0).getId();
    assertEquals(expected,actual);
  }

  @Test
  // scenario: send spanish command
  void testClickLanguage() {
    clickOn(languageMenuButton);
    clickOn("#1");
    writeTo(codeEntryBox, "ava 50");
    clickOn(enterCommandButton);
    assertEquals(turtleDisplay.getChildren().size(),2 );
  }

  @Test
  // scenario: check turtle shape menu button creation
  void checkTurtleShapeMenuButton() {
    clickOn(turtleShapeMenuButton);
    clickOn("#0");
    MenuItem item = turtleShapeMenuButton.getItems().get(1);
    assertEquals("Circle",item.getText());
  }

  @Test
  // scenario: check line color contents and order
  void lineColorMenuButton() {
    clickOn(lineColorMenuButton);
    assertEquals(5,lineColorMenuButton.getItems().size());
    assertEquals("3",lineColorMenuButton.getItems().get(3).getText());
  }

  @Test
  // scenario: change background color to index 2
  void changeBackgroundColor() {
    clickOn(backgroundColorMenuButton);
    clickOn("#BackgroundColorMenuButton2");
    assertEquals(turtleDisplay.getStyle(), "-fx-background-color: #00ff00");
  }

  @Test
    // scenario: change only one turtle to a different shape and check the shape
  void changeTurtleShape() {
    writeTo(codeEntryBox, "tell [ 1 ]");
    clickOn(enterCommandButton);
    clickOn(turtleShapeMenuButton);
    clickOn("#2");
    assertNotEquals(turtleDisplay.getChildren().get(0), turtleDisplay.getChildren().get(1));
  }

  @Test
  // scenario: test pen and turtle menu button sending commands
  void useTurtleAndPenCommand() {
    clickOn(penMenuButton);
    clickOn("#0");
    clickOn(turtleMenuButton);
    clickOn("#0");
    assertEquals(turtleDisplay.getChildren().size(), 1);
  }

  @Test
  // scenario: open and close turtlestate view
  void openAndCloseTurtleState() {
    clickOn("#CloseButton");
    assertEquals(turtleDisplay.isVisible(), false);
    clickOn("#OpenButton");
    assertEquals(turtleDisplay.isVisible(), true);
  }

  @Test
  // scenario: open help menu
  void openHelpMenu() {
    clickOn("#HelpButton");
    clickOn("#HelpList");
    Text helpDescription = lookup("#HelpDescription").query();
    String description = "Control Structure Commands:\n"
        + "DOTIMES [ variable limit ] [ command(s) ]\n"
        + "\n"
        + "Description:\n"
        + "Runs command(s) for each value specified in the range, i.e., from (1 - limit) inclusive;\n"
        + "variable is assigned to each succeeding value so that it can be accessed by the command(s)\n"
        + "\n"
        + "Parameters:\n"
        + "variable - any variable name\n"
        + "limit - any expression\n"
        + "command(s) - any command\n"
        + "\n"
        + "Returns:\n"
        + "Result of last command in the list executed; 0 if no commands are executed";

    assertEquals(helpDescription.getText(), description);
  }

  @Test
  // show command history
  void commandHistory(){
    writeTo(codeEntryBox, "fd 50");
    clickOn(enterCommandButton);
    clickOn("#HistoryButton");
    assertEquals(consoleTextBox.getText(), "fd 50\n");
  }

  @Test
  void sendToCommand(){
    writeTo(codeEntryBox, "to dash [ :count ]\n"
        + "[\n"
        + "  repeat :count \n"
        + "  [\n"
        + "    pu fd 4 pd fd 4\n"
        + "  ]      \n"
        + "]");
    clickOn(enterCommandButton);
    clickOn("#SendToCommandButton");
    moveTo("#ItemList");
    moveBy(0, -180);
    clickOn(MouseButton.PRIMARY);
    TextField textField = lookup("#TextField").query();
    writeTo(textField, "5");
    clickOn("#Submit");
    clickOn("#HistoryButton");
    assertEquals(consoleTextBox.getText(), "to dash [ :count ]\n"
        + "[\n"
        + "  repeat :count \n"
        + "  [\n"
        + "    pu fd 4 pd fd 4\n"
        + "  ]      \n"
        + "]\n"
        + "dash 5\n");
  }

  @Test
  void editVariable(){
    writeTo(codeEntryBox, "set :a 3");
    clickOn(enterCommandButton);
    clickOn("#EditVariableButton");
    moveTo("#ItemList");
    moveBy(0, -180);
    clickOn(MouseButton.PRIMARY);
    TextField textField = lookup("#TextField").query();
    writeTo(textField, "5");
    clickOn("#SubmitVariableButton");
    clickOn("#HistoryButton");
    assertEquals(consoleTextBox.getText(), "set :a 3\n"
        + "make :a 5.0\n");
  }


}
