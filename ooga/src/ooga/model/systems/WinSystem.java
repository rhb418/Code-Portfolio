package ooga.model.systems;

import java.util.List;
import java.util.function.Consumer;
import ooga.model.annotations.Track;
import ooga.model.components.HealthComponent;
import ooga.model.components.PlayerComponent;
import ooga.model.components.ScoreComponent;
import ooga.model.components.WinComponent;
import ooga.model.components.enemy.HateComponent;
import ooga.model.managers.ECManager;


/**
 * @author Robert Barnette This class checks the win or loss status of a particular level and ends
 * the level when a win or loss condition is met
 */

@Track({HealthComponent.class, ScoreComponent.class, WinComponent.class, HateComponent.class})
public class WinSystem extends ComponentBasedSystem {

  private static final String LOSE_GAME_ACTION = "lose_game";
  private static final String WIN_GAME_ACTION = "win_game";
  private static final String SCORE_CONDITION_NAME = "score";
  private static final String HEALTH_CONDITION_NAME = "health";
  private static final String ENEMY_CONDITION_NAME = "enemy";


  private ComponentMapper<HealthComponent> healthMapper;
  private ComponentMapper<ScoreComponent> scoreMapper;
  private ComponentMapper<WinComponent> winMapper;
  private ComponentMapper<HateComponent> hateMapper;
  private Consumer<Boolean> setOnLevelEnd;

  private boolean hasWon = false;

  /**
   * @param ecManager is the ecManager used to create the WinSystem This constructor creates a
   *                  WinSystem object from an ECManager
   */
  public WinSystem(ECManager ecManager) {
    super(ecManager);

    healthMapper = getComponentMapper(HealthComponent.class);
    scoreMapper = getComponentMapper(ScoreComponent.class);
    winMapper = getComponentMapper(WinComponent.class);
    hateMapper = getComponentMapper(HateComponent.class);

    addCollisionMapping(
        LOSE_GAME_ACTION,
        event -> loseGame()
    );

    addCollisionMapping(
        WIN_GAME_ACTION,
        event -> winGame()
    );
  }

  /**
   * @param deltaTime is the time elapsed from the last game loop This method is called every cycle
   *                  of the game loop and checks the level to see if a win or loss condition has
   *                  been met
   */
  @Override
  public void update(double deltaTime) {
    List<WinComponent> winComps = winMapper.getComponents();

    if (winComps.size() == 0) {
      return;
    }

    for (WinComponent w : winComps) {
      checkWin(w);
    }

  }

  /**
   * @param w is the WinComponent whose win conditions are being checked This method checks all win
   *          conditions present in a win component and will end the level if a condition is
   *          triggered
   */
  private void checkWin(WinComponent w) {
    List<WinCondition> winConditions = w.getWinConds();
    for (WinCondition wCond : winConditions) {


      if (wCond.getCondition().equals(SCORE_CONDITION_NAME)) {
        double score = scoreMapper.get(w.getOwner().getId()).getScore();
        boolean comp = wCond.checkCondition(score);
        executeWinOrLose(comp, wCond);
        return;

      } else if (wCond.getCondition().equals(HEALTH_CONDITION_NAME)) {
        double health = healthMapper.get(w.getOwner().getId()).getHealth();
        boolean comp = wCond.checkCondition(health);
        executeWinOrLose(comp, wCond);
        return;
      } else if (wCond.getCondition().equals(ENEMY_CONDITION_NAME)) {
        boolean comp = wCond.checkCondition(hateMapper.getComponents().size());
        executeWinOrLose(comp, wCond);
        return;
      }
    }

  }


  /**
   * @param setOnLevelEnd is the callback that is called when a win or lose condition is triggered
   *                      This method allows the user to pass a callback that will be executed when
   *                      a win or loss condition is triggered
   */
  public void setSetOnLevelEnd(Consumer<Boolean> setOnLevelEnd) {
    this.setOnLevelEnd = setOnLevelEnd;
  }

  /**
   * @param comp  is the boolean returned by the checkcondition method of wCond
   * @param wCond is the win condition that has been checked If comp is true then a win or loss
   *              condition is triggered based on the type of condition wCond is
   */
  private void executeWinOrLose(boolean comp, WinCondition wCond) {
    if (comp && wCond.isWin()) {
      winGame();
    }
    if (comp && !wCond.isWin()) {
      loseGame();
    }
  }


  /**
   * Ends the level when a lose condition is triggered
   */
  private void loseGame() {
    if (!hasWon) {
      hasWon = true;
      setOnLevelEnd.accept(false);
    }
  }
  /**
   * Ends the level when a win condition is triggered
   */
  private void winGame() {
    if (!hasWon) {
      hasWon = true;
      setOnLevelEnd.accept(true);
    }
  }


}
