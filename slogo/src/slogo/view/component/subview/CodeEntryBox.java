package slogo.view.component.subview;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import slogo.controller.BackEndController;
import slogo.controller.FrontEndController;
import slogo.view.Observable;
import slogo.view.Observer;

/**
 *  The CodeEntryBox class allows commands to be sent to the parser.
 *
 *  This class has a mediator that allows components to run commands without having an explicit
 *  reference to this object to avoid coupling and increase encapsulation.
 *
 * @author Bill Guo, Billy Luqiu
 */
public class CodeEntryBox extends Subview implements Observable {

  public static final String CODE_ENTRY_TEXT_AREA = "CodeEntryTextArea";

  private TextArea textArea;
  private Pane content;

  private List<Observer> observers;
  private FrontEndController frontEndController;
  private ResourceBundle labelBundle;
  private String language;

  /**
   * Constructor of the CodeEntryBox.
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labelBundle Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   * @param frontEndController FrontEndController to send commands to parser
   * @param workspaceLanguage Current language of the Workspace
   */
  public CodeEntryBox(String labelKey, ResourceBundle labelBundle,
      BackEndController backEndController, FrontEndController frontEndController,
      String workspaceLanguage) {
    super(labelKey, labelBundle, backEndController);
    observers = new ArrayList<>();
    this.frontEndController = frontEndController;
    this.labelBundle = labelBundle;
    language = workspaceLanguage;
    makeContent();
  }

  /**
   * Creates the content of the subview. Content is a TextArea to enter commands and a submit
   * button.
   */
  @Override
  protected void makeContent() {
    content = getContent();
    content.getStyleClass().add("codeentrybox-content");
    HBox codeEntryHBox = makeCodeEntryHBox();
    textArea = makeTextArea();
    textArea.setId("CodeEntryBox");
    Button submitButton = makeButton("SubmitCommandButton", e -> handleSubmitButton());
    codeEntryHBox.getChildren().addAll(textArea, submitButton);
    content.getChildren().add(codeEntryHBox);
  }

  /**
   * Updates the language of the simulation.
   * @param newLanguage String that represents new language
   */
  public void setLanguage(String newLanguage) {
    language = newLanguage;
    notifyMediator();
  }

  /**
   * Returns the language of the simulation as a String. Used by SavePreferenceButton mediator to
   * find the current language of the Workspace.
   * @return String that represents current language
   */
  public String getLanguage() {
    return language;
  }

  private HBox makeCodeEntryHBox() {
    HBox returnHBox = new HBox();
    returnHBox.getStyleClass().add("codeentrybox-hbox");
    return returnHBox;
  }

  private TextArea makeTextArea() {
    TextArea returnTextArea = new TextArea();
    returnTextArea.getStyleClass().add("codeentrybox-textarea");
    returnTextArea.setId(CODE_ENTRY_TEXT_AREA);
    return returnTextArea;
  }

  private Button makeButton(String label, EventHandler<ActionEvent> handler) {
    Button returnButton = new Button(labelBundle.getString(label));
    returnButton.getStyleClass().add("codeentrybox-button");
    returnButton.setOnAction(handler);
    returnButton.setId(label);
    return returnButton;
  }

  private void handleSubmitButton() {
    submitCommand(textArea.getText());
    textArea.clear();
  }

  /**
   * Submits a command in String format to the parser through the FrontEndController. Used by its
   * mediator when other components want to submit commands.
   * @param command Command to be sent as a String
   */
  public void submitCommand(String command) {
    frontEndController.updateCommandHistory(command);
    notifyObservers();
  }

  /**
   * Adds an observer to this object.
   * @param observer Observer that updates when this object updates
   */
  @Override
  public void attach(Observer observer) {
    observers.add(observer);
  }

  /**
   * Updates all observers in the observers List.
   */
  @Override
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }
}
