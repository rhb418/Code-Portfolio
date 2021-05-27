package ooga.view.components;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import ooga.model.ModelController;
import ooga.model.ObservableModel;
import ooga.view.util.ObservableResource;

/** A scene in which games are actually tracked and played. */
public class GameScene extends Scene {
  private ModelController controller;
  private ObservableModel game;

  public GameScene(ObservableModel game, ObservableResource resources) {
    super(new Pane());
    this.game = game;
    this.controller = game.getController();
  }

  public void pauseGame() {}

  public void playGame() {}
}
