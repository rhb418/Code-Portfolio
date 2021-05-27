package slogo.view.component.subview;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.Pane;
import slogo.controller.BackEndController;
import slogo.turtle.TurtleData;
import slogo.view.Observer;

/**
 * The TurtleState represents the states of all turtles in the Workspace.
 *
 * Updates the state's of all turtles by reading the BackEndController whenever a command gets
 * run by observing the CodeEntryBox.
 *
 * @author Bill Guo
 */
public class TurtleState extends ListSubview implements Observer {

  private Pane content;

  /**
   * Constructor of the TurtleState.
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labelBundle Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   */
  public TurtleState(String labelKey, ResourceBundle labelBundle,
      BackEndController backEndController) {
    super(labelKey, labelBundle, backEndController);
    makeContent();
  }

  /**
   * Creates the content of the Subview, which is a ListView of all the turtle states.
   */
  @Override
  protected void makeContent() {
    content = getContent();
    content.getStyleClass().add("turtlestate-content");
    content.getChildren().add(listView);
    update();
  }

  /**
   * Updates the content of the ListView with the new states of the turtles. Runs each time a
   * command is submitted.
   */
  @Override
  public void update() {
    observableList.clear();
    List<TurtleData> turtlesData = backEndController.getTurtleData();

    for (TurtleData turtleData : turtlesData) {
      StringBuilder penInfo = new StringBuilder();
      if (turtleData.isPenDown()) {
        penInfo.append(labelBundle.getString("PenDown"));
      } else {
        penInfo.append(labelBundle.getString("PenUp"));
      }
      StringBuilder turtleInfo = new StringBuilder();
      turtleInfo.append(labelBundle.getString("Turtle") + " ");
      turtleInfo.append(turtleData.getID() + ": ");
      turtleInfo
          .append("(" + turtleData.getXEndPosition() + ", " + turtleData.getYEndPosition() + ") ");
      turtleInfo.append(turtleData.getOrientation() + "\u00B0 ");
      turtleInfo.append(penInfo + " ");
      turtleInfo.append(labelBundle.getString("PenColor") + ": " + (int) turtleData.getPenColor() +
          " ");
      turtleInfo.append(labelBundle.getString("PenWidth") + ": " + turtleData.getPenSize());
      observableList.add(turtleInfo.toString());
    }
  }


}
