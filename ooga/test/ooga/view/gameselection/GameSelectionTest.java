package ooga.view.gameselection;

import static ooga.view.TestUtils.isPresent;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

@ExtendWith(ApplicationExtension.class)
class TestGameSelectionScene {

  private Stage stage;

  @Start
  private void start(Stage stage) {
    this.stage = stage;
  }

  @Test
  void neededComponentsVisible(FxRobot robot) {
    FxAssert.verifyThat("#add-game-button", NodeMatchers.isVisible());
    FxAssert.verifyThat("#add-game-button", NodeMatchers.isVisible());
  }
}
