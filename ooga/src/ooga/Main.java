package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.view.View;

public class Main extends Application {

  public static void main(String[] args) {
    launch();
  }

  /**
   * A method to test (and a joke :).
   */
  public double getVersion() {
    return 0.001;
  }

  @Override
  public void start(Stage stage) {
    View view = new View(stage);
  }
}
