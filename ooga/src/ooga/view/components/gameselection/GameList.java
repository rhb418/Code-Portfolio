package ooga.view.components.gameselection;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.prefs.Preferences;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import ooga.model.ModelFactory;
import ooga.view.components.DialogFactory;
import ooga.view.util.ObservableResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

public class GameList extends FlowPane {

  private static final Logger logger = LogManager.getLogger(GameList.class);
  private final String GAME_DIRS_KEY = "game_dirs";
  private StackPane dialogPane;
  private Set<String> presentDirectories;
  private Consumer<String> selectionCallback;
  private ObservableResource resources;
  private Preferences prefs;
  private StringBinding directoryTitle;
  private Consumer<String> onRun;

  public GameList(ObservableResource resources, StackPane dialogPane) {
    presentDirectories = new HashSet<>();
    getStyleClass().add("game-selection");
    this.dialogPane = dialogPane;
    this.resources = resources;
    this.directoryTitle = resources.getStringBinding("SelectGameDirectory");
    this.prefs = Preferences.userNodeForPackage(GameList.class);
    VBox addGameItem = new VBox();
    JFXButton addGame = new JFXButton();
    addGame.setGraphic(new FontIcon());
    addGame.getStyleClass().addAll("add-game", "primary");
    initSelection(addGame);
    addGame.setId("add-game-button");
    Label addGameLabel = new Label();
    addGameLabel.textProperty().bind(resources.getStringBinding("AddGame"));
    addGameLabel.getStyleClass().addAll("game-item-label");
    addGameItem.getStyleClass().addAll("add-game", "game-item");

    addGameItem.getChildren().addAll(addGame, addGameLabel);
    getChildren().add(addGameItem);
  }

  private void initSelection(Button addGame) {
    DirectoryChooser dirChooser = new DirectoryChooser();

    String gameDirs = prefs.get(GAME_DIRS_KEY, "");
    logger.debug("Loaded Prev_Games as {}", gameDirs);

    if (!gameDirs.isBlank()) {
      for (String game : gameDirs.split(":")) {
        logger.debug("Adding game {}", game);
        createItem(game);
      }
    }

    addGame.setOnAction(
        e -> {
          String dirPreset = prefs.get("last_selection_dir", System.getProperty("user.home"));
          File file = new File(dirPreset);
          if (file.exists()) {
            dirChooser.setInitialDirectory(file);
          }
          dirChooser.setTitle(directoryTitle.getValue());
          File selectedDir = dirChooser.showDialog(getScene().getWindow());
          if (selectedDir != null) {
            String newDir = selectedDir.getPath() + "/";
            prefs.put("last_selection_dir", newDir);
            createItem(newDir);
          }
        });
  }

  public void createItem(String directory) {
    // TODO: Add check for directories without games.
    if (presentDirectories.contains(directory)) {
      return;
    }
    File gameDir = new File(directory);
    boolean correctDir = ModelFactory.verifyGameDirectory(gameDir);
    if (!correctDir) {
      JFXDialog dialog = DialogFactory.createErrorDialog("Error Loading Directory", resources);
      // TODO: Move to setOnError Callback
      dialog.show(dialogPane);
      logger.info("Invalid directory specified {}", directory);
      return;
    }
    GameItem newGame = new GameItem(directory);

    getChildren().add(0, newGame);
    newGame.setOnAction(this::notifySelection);
    newGame.setOnDelete((path) -> {
      presentDirectories.remove(path);

      String game_dirs = prefs.get(GAME_DIRS_KEY, "");
      game_dirs = game_dirs.replace(path, "");

      while (!game_dirs.replaceAll("::", ":").equals(game_dirs)) {
        game_dirs = game_dirs.replaceAll("::", ":");
      }

      game_dirs = game_dirs.replaceAll("^:|:$", "");

      logger.debug("Game Directories is now: {}", game_dirs);

      prefs.put(GAME_DIRS_KEY, game_dirs);

      getChildren().remove(newGame);
    });
    newGame.setOnRun(this::notifyRun);
    presentDirectories.add(directory);
    notifySelection(directory);

    String gameDirs = prefs.get(GAME_DIRS_KEY, "");
    if (gameDirs.isBlank()) {
      gameDirs = directory;
    } else {
      if (!gameDirs.contains(directory)) {
        gameDirs += ":" + directory;
      }
    }
    prefs.put(GAME_DIRS_KEY, gameDirs);
    logger.debug("Updated Prev_Games to {}", gameDirs);

    logger.info("Adding Game: {}", directory);
  }

  private void notifySelection(String path) {
    if (selectionCallback != null) {
      selectionCallback.accept(path);
    } else {
      logger.warn("Callback not set on GameList selection notification");
    }
  }

  private void notifyRun(String path) {
    if (onRun != null) {
      onRun.accept(path);
    } else {
      logger.warn("Callback not set on GameList run notification");
    }
  }

  public void setOnSelection(Consumer<String> selectionCallback) {
    this.selectionCallback = selectionCallback;
  }

  public void setOnRun(Consumer<String> runCallback) {
    this.onRun = runCallback;
  }
}
