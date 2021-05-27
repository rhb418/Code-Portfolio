package ooga.view.components.game;

import com.jfoenix.controls.JFXButton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import ooga.model.observables.ObservableLevel;
import ooga.model.observables.ObservableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameArea extends AnchorPane {

  private static final Logger logger = LogManager.getLogger(GameArea.class);
  private static final double LEFT_EDGE = 20;
  private static final double BOTTOM_EDGE = 0;
  private List<ObjectView> objects;
  private StackPane objectsPane;

  public GameArea(ObservableLevel level, StatsView stats) {
    this.objectsPane = new StackPane();
    getStyleClass().add("game-area");
    this.objects = new ArrayList<>();
    // Needed so pane doesn't shrink
    JFXButton placeholder = new JFXButton();
    placeholder.setVisible(false);
    objectsPane.getChildren().add(placeholder);
    objectsPane.setTranslateX(LEFT_EDGE);
    getChildren().addAll(objectsPane, stats);
    stats.clear();
    stats.getTrackedStats().forEach( stat -> {

      level.setOnStatsUpdate(stat, 
          list -> {
          String value = list.stream()
          .map( item -> item.value())
          .collect(Collectors.joining(","));
        stats.updateStat(stat, value);
      });
    });

    AnchorPane.setTopAnchor(stats, 0.0);
    AnchorPane.setLeftAnchor(stats, 0.0);
    AnchorPane.setRightAnchor(stats, 0.0);
    AnchorPane.setBottomAnchor(objectsPane, BOTTOM_EDGE);
    AnchorPane.setLeftAnchor(objectsPane, LEFT_EDGE);
  }

  public void setLevel(ObservableLevel level) {
    level.setOnFocusUpdate(
        id -> {
          objects.stream()
              .filter(obj -> obj.isObject(id))
              .findFirst()
              .ifPresent(this::setCameraCenter);
        });
  }

  public void setCameraCenter(ObjectView center) {
    center.translateXProperty().addListener((xProp, oldVal, newVal) -> offsetX(newVal));
    center.translateYProperty().addListener((yProp, oldVal, newVal) -> offsetY(newVal));
  }

  private void offsetX(Number objectValue) {
    double position = objectValue.doubleValue() * -1;
    if (position < -200) {
      objectsPane.translateXProperty().set(position + 200);
    }
  }

  private void offsetY(Number objectValue) {
    double position = objectValue.doubleValue() * -1;
    logger.debug("obj position: {}", position);
    if (position > 250) {
      objectsPane.translateYProperty().set(position - 200);
    }
  }

  public void addObject(ObjectView object) {
    if (objectsPane.getChildren().size() == 1) {
      setCameraCenter(object);
    }
    objects.add(object);
    objectsPane.getChildren().add(object);
  }

  public void removeObject(ObservableObject object) {
    ObjectView viewToRemove = null;
    for (ObjectView o : objects) {
      if (o.isObject(object)) {
        objectsPane.getChildren().remove(o);
      }
    }
    objects.remove(viewToRemove);
  }
}
