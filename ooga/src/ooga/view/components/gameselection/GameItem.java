package ooga.view.components.gameselection;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.function.Consumer;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class GameItem extends VBox {

  private Consumer<String> onClick;
  private String directory;
  private String encodedPath;
  private String gameLabel;
  private Consumer<String> onDelete;
  private Consumer<String> onRun;

  public GameItem(String gamePath) {
    this.directory = gamePath;
    try {
      File gameFile = new File(gamePath);
      this.gameLabel = gameFile.getName();
      this.encodedPath = new File(gamePath).toURI().toURL().toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    getStyleClass().addAll("game-item");
    JFXButton game = new JFXButton();
    game.getStyleClass().addAll("game-item-button", "primary");
    game.setGraphic(new FontIcon());
    VBox.setVgrow(game, Priority.ALWAYS);
    Label label = new Label();
    if (!gamePath.isEmpty()) {
      game.setStyle(new StringBuilder().append("-fx-background-image: url('")
          .append(encodedPath.replaceAll("'", "%27"))
          .append("thumbnail.jpg');").toString());
      label.setText(gameLabel);
    }
    game.setOnAction(e -> notifyAction());

    ContextMenu menu = new ContextMenu();
    menu.getStyleClass().addAll("game-context-menu");
    menu.getItems().add(createMenuItem("Run"));
    menu.getItems().add(createMenuItem("Delete"));
    game.setContextMenu(menu);

    label.getStyleClass().add("game-item-label");
    getChildren().addAll(game, label);
    setOnMouseClicked(e -> notifyAction());
  }

  private MenuItem createMenuItem(String run) {
    MenuItem item = new MenuItem(run);
    item.getStyleClass().addAll("game-context-menu-item");
    item.setOnAction((e) -> {
      try {
        Method handler = getClass().getDeclaredMethod("handle" + run);
        handler.setAccessible(true);
        handler.invoke(this);
      } catch (NoSuchMethodException noSuchMethodException) {
        noSuchMethodException.printStackTrace();
      } catch (IllegalAccessException illegalAccessException) {
        illegalAccessException.printStackTrace();
      } catch (InvocationTargetException invocationTargetException) {
        invocationTargetException.printStackTrace();
      }
    });
    return item;
  }

  public void setOnAction(Consumer<String> callback) {
    this.onClick = callback;
  }

  public void setOnDelete(Consumer<String> callback) {
    onDelete = callback;
  }

  public void setOnRun(Consumer<String> callback) {
    onRun = callback;
  }

  private void notifyDelete() {
    if (this.onDelete != null) {
      onDelete.accept(directory);
    }
  }

  private void notifyAction() {
    if (this.onClick != null) {
      onClick.accept(this.directory);
    }
  }

  private void notifyRun() {
    if (this.onRun != null) {
      onRun.accept(this.directory);
    }
  }

  private void handleRun() {
    notifyRun();
  }

  private void handleDelete() {
    notifyDelete();
  }


  private String getDirectory() {
    return this.directory;
  }
}
