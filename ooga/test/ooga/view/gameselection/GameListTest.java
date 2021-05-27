package ooga.view.gameselection;


import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ooga.view.components.gameselection.GameList;
import ooga.view.util.ObservableResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
public class GameListTest {

  private Stage stage;
  private GameList gameList;
  private Preferences prefs;

  @Start
  private void start(Stage stage) {
    this.stage = stage;
    StackPane rootPane = new StackPane();
    Scene scene = new Scene(rootPane);
    stage.setScene(scene);

    prefs = Preferences.userNodeForPackage(GameList.class);

    ObservableResource resources = new ObservableResource();
    ResourceBundle resBundle = ResourceBundle.getBundle("ooga.view.resources.languages.English");
    resources.setResources(resBundle);
    gameList = new GameList(resources, rootPane);
    rootPane.getChildren().add(gameList);
    stage.show();
  }

  @Test
  void addIncorrectGameDirectory(FxRobot robot) {
    File file = new File("data/Jumping aloon");
    addGame(file.getAbsolutePath());
    FxAssert.verifyThat("Error", NodeMatchers.isVisible());
  }

  @Test
  void selectGameCorrectly(FxRobot robot) {
    //TODO: Make generic game selection method
    File file = new File("data/Jumping Baloons");
    gameList.setOnSelection(e -> {
      assertEquals(file.getAbsolutePath(), new File(e).getAbsolutePath());
    });
    addGame(file.getAbsolutePath());
  }

  @Test
  void addCorrectGameDirectory(FxRobot robot) {
    File file = new File("data/Jumping Baloons");
    addGame(file.getAbsolutePath());
    FxAssert.verifyThat("Jumping Baloons", NodeMatchers.isVisible());
  }

  @Test
  void exitApplicationWorks(FxRobot robot) {
  }

  @Test
  void testGameCaching(FxRobot robot) {
    clearPreferences();
    File file = new File("data/Jumping Baloons");

    String GAME_DIRS_KEY = "game_dirs";
    addGame(file.getAbsolutePath());
    exitApplicationWorks(robot);

    assertEquals(file.getAbsolutePath(), prefs.get(GAME_DIRS_KEY, ""));

    Platform.runLater(() -> start(stage));
    FxAssert.verifyThat("Jumping Baloons", NodeMatchers.isVisible());
  }

  @Test
  void testMultiGameCaching(FxRobot robot) {
    clearPreferences();
    File file1 = new File("data/Jumping Baloons");
    File file2 = new File("data/Ultimate Game");
    String GAME_DIRS_KEY = "game_dirs";

    addGame(file1.getAbsolutePath());
    addGame(file2.getAbsolutePath());
    exitApplicationWorks(robot);

    assertTrue(prefs.get(GAME_DIRS_KEY, "").contains(file1.getAbsolutePath()));
    assertTrue(prefs.get(GAME_DIRS_KEY, "").contains(file2.getAbsolutePath()));

    Platform.runLater(() -> start(stage));
    FxAssert.verifyThat("Jumping Baloons", NodeMatchers.isVisible());
    FxAssert.verifyThat("Ultimate Game", NodeMatchers.isVisible());
  }

  void clearPreferences() {
    try {
      prefs.clear();
    } catch (BackingStoreException e) {
      e.printStackTrace();
    }
    Platform.runLater(() -> start(stage));
  }

  void addGame(String dir) {
    WaitForAsyncUtils.asyncFx(() -> gameList.createItem(dir));
    WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
  }
}
