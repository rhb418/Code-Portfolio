package slogo.view.component.subview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import slogo.controller.BackEndController;
import slogo.turtle.LineCoords;
import slogo.turtle.TurtleData;
import slogo.view.Observer;

/**
 * The TurtleGraphicsDisplay class represents the visuals of the turtles.
 *
 * Updates when a command gets run by observing the CodeEntryBox.
 *
 * @author Bill Guo, Billy Luqiu
 */
public class TurtleGraphicsDisplay extends Subview implements Observer {

  private static final double VIEW_WIDTH = 500.0;
  private static final double HALF_VIEW_WIDTH = VIEW_WIDTH / 2;
  private static final double VIEW_HEIGHT = 500.0;
  private static final double HALF_VIEW_HEIGHT = VIEW_HEIGHT / 2;
  private static final double TURTLE_WIDTH = 50.0;
  private static final double TURTLE_HEIGHT = 50.0;
  private static final String WHITE_HEXCODE = "#FFFFFF";
  private static final double DEACTIVE_OPACITY = .3;

  private Pane content;
  private ResourceBundle shapeBundle;
  private Map<Double, String> shapePalette;
  private List<ImageView> turtleImages;
  private List<Line> currentlyDrawnLines;

  /**
   * Constructor of the TurtleGraphicsDisplay
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labels Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   * @param shapes Resource file that contains the shape palette
   */
  public TurtleGraphicsDisplay(String labelKey,
      ResourceBundle labels, BackEndController backEndController, ResourceBundle shapes) {
    super(labelKey, labels, backEndController);
    shapeBundle = shapes;
    turtleImages = new ArrayList<>();
    this.currentlyDrawnLines = new ArrayList<>();
    makeShapePalette();
    makeContent();
  }

  /**
   * Creates the content of the Subview, which is a Pane where the turtle and lines are drawn.
   */
  @Override
  protected void makeContent() {
    content = getContent();
    content.getStyleClass().add("turtlegraphicsdisplay-content");
    content.setId("TurtleDisplayContent");
    update();
  }

  /**
   * Updates the position of turtles and lines, as well as background color, based on the
   * BackEndController.
   */
  @Override
  public void update() {
    clearTurtlesFromGrid();
    for (TurtleData turtleData : backEndController.getTurtleData()) {
      if (turtleData.isShown()) {
        addTurtleImage(turtleData);
      }
      addLinesToPane(turtleData);
    }
    setBackgroundColor(backEndController.getTurtleData().get(0));
  }

  private void makeShapePalette() {
    shapePalette = new TreeMap<>();
    for (String shape : shapeBundle.keySet()) {
      String[] value = shapeBundle.getString(shape).split("\\|");
      shapePalette.put(Double.valueOf(value[0]), value[1]);
    }
  }

  private void addTurtleImage(TurtleData turtleData) {
    Image image = new Image(shapePalette.get(turtleData.getShape()), TURTLE_WIDTH,
        TURTLE_HEIGHT, false, false);
    ImageView sprite = new ImageView(image);
    if (!outOfBounds(turtleData.getXEndPosition(), turtleData.getYEndPosition())) {
      if (!backEndController.getActiveTurtles().contains(turtleData.getID())) {
        sprite.setOpacity(DEACTIVE_OPACITY);
      }
      setModelXPositionToViewXPosition(turtleData.getXEndPosition(), sprite);
      setModelYPositionToViewYPosition(-turtleData.getYEndPosition(), sprite);
      setModelOrientationToViewOrientation(turtleData.getOrientation(), sprite);
      content.getChildren().add(sprite);
      turtleImages.add(sprite);
    }
  }

  private void setModelXPositionToViewXPosition(double modelXPos, ImageView sprite) {
    double newXPos = modelXPos + HALF_VIEW_WIDTH - TURTLE_WIDTH / 2;
    sprite.setX(newXPos);
  }

  private void setModelYPositionToViewYPosition(double modelYPos, ImageView sprite) {
    double newYPos = modelYPos + HALF_VIEW_HEIGHT - TURTLE_HEIGHT / 2;
    sprite.setY(newYPos);
  }

  private void setModelOrientationToViewOrientation(double orientation, ImageView sprite) {
    if (orientation <= 90) {
      sprite.setRotate(90 - orientation);
    } else {
      sprite.setRotate(450 - orientation);
    }
  }

  private boolean outOfBounds(double xPosition, double yPosition) {
    return xPosition > HALF_VIEW_WIDTH ||
        xPosition < -HALF_VIEW_WIDTH ||
        yPosition > HALF_VIEW_HEIGHT ||
        yPosition < -HALF_VIEW_HEIGHT;
  }

  private void addLinesToPane(TurtleData turtleData) {
    content.getChildren().removeAll(currentlyDrawnLines);
    currentlyDrawnLines.clear();
    for (LineCoords lineCoord : turtleData.pathToDraw()) {
      if (!outOfBounds(lineCoord.getStartX(), lineCoord.getStartY()) &&
          !outOfBounds(lineCoord.getEndX(), lineCoord.getEndY())) {
        Line line = new Line(lineCoord.getStartX() + HALF_VIEW_WIDTH,
            -lineCoord.getStartY() + HALF_VIEW_HEIGHT,
            lineCoord.getEndX() + HALF_VIEW_WIDTH, -lineCoord.getEndY() + HALF_VIEW_HEIGHT);
        line.setStroke(getColor(turtleData));
        line.setStrokeWidth(turtleData.getPenSize());
        currentlyDrawnLines.add(line);
        content.getChildren().add(line);
      }
    }
  }

  private Color getColor(TurtleData turtleData) {
    if (turtleData.getPalettes().get(turtleData.getPenColor()) == null) {
      return Color.rgb(0, 0, 0);
    } else {
      double[] rgb = turtleData.getPalettes().get(turtleData.getPenColor());
      return Color.rgb((int) rgb[0], (int) rgb[1], (int) rgb[2]);
    }
  }

  private void setBackgroundColor(TurtleData turtleData) {
    if (turtleData.getPalettes().get(turtleData.getPenColor()) == null) {
      content.setStyle("-fx-background-color: " + WHITE_HEXCODE);
    } else {
      double[] rgb = turtleData.getPalettes().get(turtleData.getBackground());
      String hexcode = String.format("#%02x%02x%02x", (int) rgb[0], (int) rgb[1], (int) rgb[2]);
      content.setStyle("-fx-background-color: " + hexcode);
    }
  }

  private void clearTurtlesFromGrid() {
    if (!content.getChildren().isEmpty()) {
      content.getChildren().removeAll(turtleImages);
      turtleImages.clear();
    }
  }
}
