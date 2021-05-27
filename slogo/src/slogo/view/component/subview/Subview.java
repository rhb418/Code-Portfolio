package slogo.view.component.subview;

import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import slogo.controller.BackEndController;
import slogo.view.component.Component;
import slogo.view.component.clickable.buttonclickable.ButtonClickable;
import slogo.view.component.clickable.buttonclickable.CloseButton;
import slogo.view.component.clickable.buttonclickable.OpenButton;


/**
 * Abstract class that represents a component view on each Workspace.
 *
 * Each Subview has a header that labels the Subview, as well as buttons to open and close the
 * content of the Subview. Subviews need to implement the makeContent() method to put UI objects
 * into the content pane. Both the header and the content are contained in a VBox that represents
 * the entire Subview.
 *
 * @author Bill Guo
 */
public abstract class Subview extends Component {

  private static final String CLOSE_PNG = "slogo/resources/images/buttons/close.png";
  private static final String OPEN_PNG = "slogo/resources/images/buttons/open.png";
  private static final String LABEL_FILE_ROOT = "slogo/resources/languages/";

  protected ResourceBundle labelBundle;
  protected BackEndController backEndController;

  private VBox view;
  private HBox header;
  private Pane content;

  /**
   * Constructor of the Subview.
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labelBundle Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   */
  public Subview(String labelKey, ResourceBundle labelBundle, BackEndController backEndController) {
    view = new VBox();
    view.getStyleClass().add("subview-vbox");
    this.labelBundle = labelBundle;
    this.backEndController = backEndController;

    header = makeHeader(labelKey);
    content = new Pane();

    view.getChildren().addAll(header, content);
  }

  private HBox makeHeader(String labelKey) {
    HBox returnHBox = new HBox();
    returnHBox.getStyleClass().add("subview-hbox");
    Text label = new Text(labelBundle.getString(labelKey));
    label.getStyleClass().add("subview-text");

    Region region = new Region();
    HBox.setHgrow(region, Priority.ALWAYS);

    ButtonClickable openButton = new OpenButton(new Image(OPEN_PNG), this);
    openButton.getButton().setId("OpenButton");
    ButtonClickable closeButton = new CloseButton(new Image(CLOSE_PNG), this);
    closeButton.getButton().setId("CloseButton");

    returnHBox.getChildren().addAll(label, region, openButton.getButton(), closeButton.getButton());
    return returnHBox;
  }

  /**
   * Returns a button with the correct and event to be run. Can be used by all Subviews should
   * they need to create a button that is a part of their content.
   * @param label key value that represents the label of the button
   * @param styleClass name that represents the css to be run on the button
   * @param eventHandler event that happens when the button is clicked
   * @return Button
   */
  protected Button makeButton(String label, String styleClass, EventHandler eventHandler) {
    Button returnButton = new Button(labelBundle.getString(label));
    returnButton.getStyleClass().add(styleClass);
    returnButton.setOnAction(eventHandler);
    returnButton.setId(label);
    return returnButton;
  }

  /**
   * Creates the content of the Subview. Content appears under the header of the subview. All
   * concrete implementations must implement this method to construct the content of the subview.
   */
  protected abstract void makeContent();

  /**
   * Returns the VBox that represents the entire Subview. Used by the Workspace to grab the
   * entire object.
   * @return VBox that represents the entire Subview
   */
  public VBox getView() {
    return view;
  }

  /**
   * Returns the Pane that represents the content of the Subview. Used by the OpenButton and
   * CloseButton to show and hide the content, respectively.
   * @return Pane that representes the content of the Subview.
   */
  public Pane getContent() {
    return content;
  }

  /**
   * Sets the language of the label bundle to be read. Used by the Workspace to change the
   * language of the Subviews.
   * @param language String that represents the new language
   */
  public void setLanguage(String language) {
    labelBundle = ResourceBundle.getBundle(LABEL_FILE_ROOT + language);
  }
}
