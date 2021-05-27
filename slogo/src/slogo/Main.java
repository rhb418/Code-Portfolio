package slogo;

import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import slogo.commands.Command;
import slogo.model.parser.Parser2;
import slogo.view.SceneBuilder;

/**
 * Driver to run the SLogo IDE.
 *
 * @author Bill Guo
 */
public class Main extends Application {

  private static final String TITLE = "SLogo";
  public static final String CSS_FILE = "slogo/resources/css/mainconfig.css";
  public static final int WIDTH = 1280;
  public static final int HEIGHT = 800;

  /**
   * Creates the SceneBuilder and displays it on the stage.
   */
  @Override
  public void start(Stage stage) {
    Parent root = new SceneBuilder().initialize();
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    scene.getStylesheets().add(CSS_FILE);
    stage.setTitle(TITLE);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Entry point.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}

