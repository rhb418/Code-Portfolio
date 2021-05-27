package cellsociety.display;

import static cellsociety.Constants.CONTROL_HEIGHT;
import static cellsociety.Constants.LANGUAGES;
import static cellsociety.Constants.SIZE;
import static cellsociety.Constants.SPLASH;

import cellsociety.Main;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Displays Splash Screen for users to choose a language for their program
 *
 * @author Felix Jiang
 */
public class SplashScreen {

  private Main sim;
  private Stage splash;

  /**
   * Constructor for creating the screen
   *
   * @param sim reference to main in order to start the actual program after language chosen
   * @param stage passed in from Main that Application gave, initial stage of program
   */
  public SplashScreen(Main sim, Stage stage) {
    this.sim = sim;
    this.splash = stage;
    VBox root = new VBox();
    root.getChildren().add(new Text(SPLASH));
    root.getChildren().add(languageSelection());
    root.setAlignment(Pos.CENTER);
    Scene myScene = new Scene(root, SIZE, SIZE + CONTROL_HEIGHT);
    splash.setScene(myScene);
    splash.show();
  }

  /**
   * Closes this stage/window after a user selects a language and the new window pops up
   *
   * Called by Main
   */
  public void closeSplash() {
    splash.close();
  }

  private ComboBox languageSelection() {
    ComboBox languageDropdown = new ComboBox(FXCollections.observableArrayList(LANGUAGES));
    languageDropdown.setOnAction(
        e -> sim.startSim(languageDropdown.getSelectionModel().getSelectedItem().toString(),
            new Stage()));
    return languageDropdown;
  }
}
