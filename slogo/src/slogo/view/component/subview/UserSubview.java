package slogo.view.component.subview;

import static slogo.Main.CSS_FILE;

import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.controller.BackEndController;

/**
 * The UserSubview represents a ListSubview whose content not only contains a ListView, but a
 * button that can open a new window where commands can be sent that are related to the ListSubview.
 *
 * Each UserSubview creates a ListView and a Button for their content, where the button opens a
 * new window that contains the list of things in the ListView, as well as fields to send
 * commands related to the chosen items in the list. Concrete implementations must implement what
 * happens when a button created in the window is clicked in order to send the correct
 * information for the command.
 *
 * @author Bill Guo
 */
public abstract class UserSubview extends ListSubview {

  protected static final String ERROR_MESSAGE = "Bad input";
  protected Stage stage;
  protected ListView itemListInNewWindow;
  protected VBox infoVBoxInNewWindow;

  /**
   * Constructor of the UserSubview.
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labelBundle Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   */
  public UserSubview(String labelKey, ResourceBundle labelBundle,
      BackEndController backEndController) {
    super(labelKey, labelBundle, backEndController);
  }

  /**
   * Returns a TextField in the new window that can only take in numerals and '.'
   * @return TextField that represents information related to a command
   */
  protected TextField makeTextField() {
    TextField textField = new TextField();
    textField.setId("TextField");
    UnaryOperator<Change> filter = change -> {
      String text = change.getText();
      if (text.matches("\\d*(\\.\\d*)?")) {
        return change;
      }
      return null;
    };
    TextFormatter textFormatter = new TextFormatter(filter);
    textField.setTextFormatter(textFormatter);
    return textField;
  }

  /**
   * Returns a button to open a new window.
   * @param keys Set of items to put in the ListView in the new window
   */
  protected void handleOpenWindowButton(Set<String> keys) {
    HBox newWindowHBox = makeNewWindow(keys);
    Scene scene = new Scene(newWindowHBox);
    scene.getStylesheets().add(CSS_FILE);
    stage = new Stage();
    stage.setScene(scene);
    stage.show();
  }

  private HBox makeNewWindow(Set<String> keys) {
    HBox returnBox = new HBox();
    infoVBoxInNewWindow = new VBox();
    infoVBoxInNewWindow.getStyleClass().add("usersubview-hbox");

    ObservableList items = makeItems(keys);
    itemListInNewWindow = new ListView(items);
    itemListInNewWindow.setId("ItemList");
    itemListInNewWindow.getStyleClass().add("usersubview-listview");
    itemListInNewWindow.setOnMouseClicked(listViewEventHandler());
    returnBox.getChildren().addAll(itemListInNewWindow, infoVBoxInNewWindow);

    return returnBox;
  }

  private ObservableList makeItems(Set<String> keys) {
    ObservableList commands = FXCollections.observableArrayList();
    commands.addAll(keys);
    Collections.sort(commands);
    return commands;
  }

  /**
   * Returns an EventHandler that performs an action when a button in the detailed info about the
   * subclass is pressed. Subclasses must implement this method to set their instance variables
   * up so that the CodeEntryBox mediator can read directly from those instance variables to send
   * the appropriate command.
   * @return EventHandler to send commands
   */
  protected abstract EventHandler listViewEventHandler();

}
