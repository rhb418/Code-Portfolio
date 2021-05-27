package ooga.view.components;

import com.jfoenix.controls.JFXButton;
import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ooga.view.util.ObservableResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

public class SplashScreen extends Scene {

  private static final Logger logger = LogManager.getLogger(SplashScreen.class);
  private StackPane root;
  private Runnable exitCallback;
  private Button exitGame;
  private Runnable runCallback;
  private Consumer<StackPane> settingsCallback;

  public SplashScreen(int width, int height, ObservableResource resources) {
    super(new StackPane(), width, height, Color.BLACK);
    this.root = (StackPane) getRoot();
    this.root.setId("splash-screen");
    VBox vbox = new VBox();

    Label title = new Label("Liam & Friends");
    title.getStyleClass().add("splash_title");
    vbox.getStyleClass().add("splashscreen");

    JFXButton resume = new JFXButton();
    resume.setId("load-game-splash");
    resume.textProperty().bind(resources.getStringBinding("LoadGame"));
    resume.getStyleClass().addAll("special", "primary", "load_library");
    resume.setGraphic(new FontIcon());

    JFXButton settings = new JFXButton();
    settings.setId("settings-splash");
    settings.textProperty().bind(resources.getStringBinding("Settings"));
    settings.getStyleClass().addAll("primary", "settings_button");
    settings.setGraphic(new FontIcon());

    exitGame = new JFXButton();
    exitGame.setId("exit-splash");
    exitGame.textProperty().bind(resources.getStringBinding("Exit"));
    exitGame.getStyleClass().addAll("secondary", "exit");
    exitGame.setGraphic(new FontIcon());

    VBox.setVgrow(resume, Priority.ALWAYS);
    VBox.setVgrow(exitGame, Priority.ALWAYS);
    vbox.getChildren().addAll(title, resume, settings, exitGame);

    exitGame.setOnAction(
        e -> {
          if (exitCallback != null) {
            exitCallback.run();
          }
        });

    resume.setOnAction(
        e -> {
          if (runCallback != null) {
            runCallback.run();
          }
        });

    settings.setOnAction(e -> {
      if(settingsCallback != null) {
        settingsCallback.accept(root);
      }
    });

    root.getChildren().add(vbox);
  }

  public void setOnExit(Runnable run) {
    this.exitCallback = run;
  }

  public void setOnPlay(Runnable run) {
    this.runCallback = run;
  }

  public void setOnSettings(Consumer<StackPane> callback) {
    this.settingsCallback = callback;
  }
}
