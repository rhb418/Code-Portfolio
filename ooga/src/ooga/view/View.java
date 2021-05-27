package ooga.view;

import com.jfoenix.controls.JFXDialog;
import fr.brouillard.oss.cssfx.CSSFX;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import javafx.animation.*;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.*;
import ooga.view.components.GenericMenu;

import ooga.view.components.SettingsModule;
import ooga.view.components.SettingsPane;
import ooga.view.components.SplashScreen;
import ooga.view.components.game.GameScene;
import ooga.view.components.gameselection.GSelectionScene;
import ooga.view.util.ConfigurationFactory;
import ooga.view.util.ObservableResource;
import ooga.view.util.ViewConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class View {

  public static final int HEIGHT = 700;
  public static final int WIDTH = 700;
  public static final String RESOURCES = "resources/";
  public static final String DEFAULT_RESOURCES = "ooga.view.resources.languages.";
  private static final double FADE_OPACITY = 0.1;
  private static final int TRANSITION_SPEED = 700; // milliseconds
  private static final Logger logger = LogManager.getLogger(View.class);

  private Runnable exitApplication;
  private ObservableResource resources;
  private Scene currentScene;
  private GameScene currentGame;
  private FadeTransition fadeOutTransition;
  private FadeTransition fadeInTransition;
  private GSelectionScene gameSelection;
  private Stage stage;
  private String cssFile;
  private JFXDialog pauseDialog;
  private SplashScreen splashScreen;
  private ViewConfiguration viewConfiguration;
  private GenericMenu pauseMenu;
  private SettingsPane settings;
  private GenericMenu endScreen;
  private String currentGameDir;

  public View(Stage stage) {
    CSSFX.start();
    this.stage = stage;
    String defaultConfig = "";
    try {

      defaultConfig =
          Paths.get(getClass().getResource("resources/defaultView.json").toURI()).toString();
      this.viewConfiguration = ConfigurationFactory.createViewConfig(defaultConfig);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    this.resources = viewConfiguration.getResources();
    splashScreen = new SplashScreen(HEIGHT, WIDTH, resources);
    gameSelection = new GSelectionScene(HEIGHT, WIDTH, resources);
    // this.modelController = model.getController();
    final URL cssFileURL = getClass().getResource(RESOURCES + "main.css");
    if (cssFileURL != null) {
      this.cssFile = cssFileURL.toExternalForm();
      gameSelection.getStylesheets().add(cssFile);
      splashScreen.getStylesheets().add(cssFile);
    } else {
      logger.warn("Css file could not be loaded");
    }
    createAnimations();
     setScene(splashScreen);
    //startGame("data/Goomba's Revenge/");
    gameSelection.setOnGameSelected(this::startGame);
    exitApplication =
        () -> {
          fadeOutTransition.setNode(currentScene.getRoot());
          fadeOutTransition.play();
          fadeOutTransition.setOnFinished(
              e -> {
                stage.close();
                logger.info("Exited Application");
              });
        };
    setupPauseMenu();
    setupSettings();

    splashScreen.setOnExit(exitApplication);
    splashScreen.setOnPlay(() -> setScene(gameSelection));
    splashScreen.setOnSettings( e -> {
      pauseDialog.show(e);
    });


    logger.info("Displaying Splash Screen");
    stage.show();
  }

  private void setupEndscreen(boolean victory) {
    StringBinding title;
    StringBinding nextPlay;
    StringBinding exit = resources.getStringBinding("Exit");
    if (victory) {
      title = resources.getStringBinding("YouWin");
      nextPlay = resources.getStringBinding("TryAgain");
    } else {
      title = resources.getStringBinding("YouLose");
      nextPlay = resources.getStringBinding("PlayAgain");
    }
    this.endScreen = new GenericMenu(title);
    endScreen.addOption(nextPlay, () -> startGame(this.currentGameDir),
        "secondary", "play");
    endScreen.addOption(exit, () -> setScene(splashScreen),
        "secondary", "play");
  }

  private void setupSettings() {

    SettingsModule systemModule = new SettingsModule(resources.getStringBinding("System"));
    ObservableList<String> list =
        FXCollections.observableArrayList(ViewConfiguration.getSupportedLanguages());
    ReadOnlyObjectProperty<String> prop =
        systemModule.addListSetting(resources.getStringBinding("LanguageSetting"), list);
    prop.addListener((na, old, newVal) -> handleLanguageChange(old, newVal));
    this.settings = new SettingsPane(resources);
    settings.setOnClose(
        () -> {
          pauseDialog.getChildren().remove(settings);
        });
    settings.addModule(systemModule);
    if (currentGame != null) {
      settings.addModule(currentGame.getSettings());
    }
  }

  private void setupPauseMenu() {
    pauseDialog = new JFXDialog();
    pauseMenu = new GenericMenu(resources.getStringBinding("Paused"));
    pauseDialog.setContent(pauseMenu);

    pauseMenu.addOption(
        resources.getStringBinding("Resume"),
        () -> {
          pauseDialog.close();
          if (this.currentGame != null) {
            currentGame.playGame();
          }
        },
        "resume",
        "special",
        "primary");

    pauseMenu.addOption(
        resources.getStringBinding("Settings"),
        () -> {
          setupSettings();
          pauseDialog.getChildren().add(settings);
        });

    pauseMenu.addOption(
        resources.getStringBinding("Exit"),
        () -> {
          setScene(this.splashScreen);
        },
        "exit",
        "special",
        "secondary");
  }

  private void startGame(String directory) {
    logger.info("Game Selected {}", directory);
    this.currentGameDir = directory;
    // TODO: Have a check if a game is currently playing and ask
    // if want to quit
    currentGame = new GameScene(directory, resources);
    currentGame.setOnEnd( b -> {
      setupEndscreen(b);
      currentGame.getRootCover().getChildren().add(endScreen);
    });
    currentGame.setOnResize(
        (height, width) -> {
          stage.setHeight(height);
          stage.setWidth(width);
        });
    if (!cssFile.isBlank()) {
      currentGame.getStylesheets().add(cssFile);
    }

    currentGame.setOnEscape(
        (e) -> {
          currentGame.pauseGame();
          pauseDialog.show(e);
          // e.getChildren().add(settingsModule);
        });

    setScene(currentGame);
  }

  private void handleLanguageChange(String old, String newVal) {
    logger.info("Language Changed from {} to {}", old, newVal);
    viewConfiguration.setLanguage(newVal);
  }

  private void createAnimations() {
    fadeOutTransition = new FadeTransition(Duration.millis(TRANSITION_SPEED));
    fadeOutTransition.setFromValue(1.0);
    fadeOutTransition.setToValue(FADE_OPACITY);

    fadeInTransition = new FadeTransition(Duration.millis(TRANSITION_SPEED));
    fadeInTransition.setFromValue(FADE_OPACITY);
    fadeInTransition.setToValue(1.0);
  }

  private void setScene(Scene scene) {
    if (this.currentScene != null) {
      fadeOutTransition.setNode(currentScene.getRoot());
      fadeOutTransition.play();
      fadeOutTransition.setOnFinished(
          e -> {
            scene.getRoot().setStyle("-fx-opacity: " + FADE_OPACITY);
            fadeInTransition.setNode(scene.getRoot());
            stage.setScene(scene);
            fadeInTransition.play();
          });
    } else {
      stage.setScene(scene);
      fadeInTransition.setNode(scene.getRoot());
      fadeInTransition.play();
    }
    this.currentScene = scene;
  }
}
