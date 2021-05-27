package ooga.view.components.game;

import java.util.function.Consumer;
import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

  private Consumer<Double> callback;
  private long prevTime = System.nanoTime();
  private double delta;
  private boolean isPaused = false;

  @Override
  public void handle(long currentNanoTime) {
    delta = currentNanoTime - prevTime;
    delta /= 1e9;
    if (callback != null && !isPaused) {
      callback.accept(delta);
    }
    prevTime = currentNanoTime;
  }


  public void pause() {
    this.isPaused = true;
  }

  public void play() {
    this.isPaused = false;
  }

  public void setUpdateTime(long timeInNano) {}

  public void setOnUpdate(Consumer<Double> run) {
    this.callback = run;
  }
}
