package ooga.view;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
class TestViewManaging {

  private View view;
  private Stage stage;
  private static final Predicate<Node> verifyFade = node -> node.getOpacity() < 1;
  private static final Predicate<Node> isPresent = Node::isVisible;

  @Start
  private void start(Stage stage) {
    this.stage = stage;
    this.view = new View(stage);
  }

  @Test
  void splashScreenButtonsVisible(FxRobot robot) {
    FxAssert.verifyThat("#load-game-splash", (Predicate<Button>) ButtonBase::isVisible);
    FxAssert.verifyThat("#load-game-splash", LabeledMatchers.hasText("Load Game"));
    FxAssert.verifyThat("#settings-splash", (Predicate<Button>) ButtonBase::isVisible);
    FxAssert.verifyThat("#settings-splash", LabeledMatchers.hasText("Settings"));
  }

  @Test
  void gameSelectAppears(FxRobot robot) {
    robot.clickOn("#load-game-splash");
    WaitForAsyncUtils.sleep(300, TimeUnit.MILLISECONDS);
    FxAssert.verifyThat("#splash-screen", verifyFade);
    WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
    FxAssert.verifyThat("#selection-scene", isPresent);
    FxAssert.verifyThat("#add-game-button", isPresent);
  }

  @Test
  void exitApplicationWorks(FxRobot robot) {
    robot.clickOn("#exit-splash");
    WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
    FxAssert.verifyThat("#splash-screen", verifyFade);
  }
}
