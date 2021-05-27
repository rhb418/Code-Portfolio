package ooga.model.managers;

import java.util.concurrent.atomic.AtomicInteger;

public class IDManager extends BaseManager {

  // a unique ID generator based on https://stackoverflow.com/a/5563932/7730917

  private AtomicInteger counter = new AtomicInteger();

  public IDManager() {
  }

  /**
   * Return a new unique ID
   */
  public int getNewId() {
    return counter.getAndIncrement();
  }

}
