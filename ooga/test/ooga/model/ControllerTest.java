package ooga.model;

import ooga.view.Controller;
import ooga.view.ModelController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {

  Model model;
  ModelController controller;
  private int levelChanges;

  @BeforeEach
  void setup() {
    levelChanges = 0;

    model = new Model();
    model.setOnLevelChange((level) -> levelChanges++);

    controller = new Controller(model);
  }

  @Test
  void creatingGameTest() {
    assertDoesNotThrow(() -> controller.setGame(new File("data/example")));
    assertEquals(1, levelChanges);
    // FIXME: Use a dedicated game config to test this, otherwise number of game objects keeps
    //  changing during development
    //  assertEquals(42, numObjects);
  }
}
