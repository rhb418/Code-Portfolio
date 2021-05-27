package ooga.model.systems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ooga.model.components.ScoreComponent;
import ooga.model.components.WinComponent;
import ooga.model.managers.ECManager;
import ooga.model.objects.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class WinSystemTest {
  private ECManager myECManager;
  private WinSystem myWS;
  private WinComponent wc1;
  private ScoreComponent sc1;
  private WinComponent wc2;
  private GameObject obj1;
  private GameObject obj2;

  private int checker;

  @BeforeEach
  void setUp(){
    checker = 0;
    Consumer<Boolean> setOnLevelEnd = this::checkWinLoss;
    myECManager = new ECManager(null);
    obj1 = myECManager.createEntity("obj1");
    sc1 = myECManager.createComponent(obj1, ScoreComponent.class);
    wc1 = myECManager.createComponent(obj1, WinComponent.class);
    obj1.addComponent(sc1);
    obj1.addComponent(wc1);


    myWS = new WinSystem(myECManager);
    myWS.setSetOnLevelEnd(setOnLevelEnd);



  }

  @Test
  void testWin(){
    List<WinCondition> winCondWC1 = wc1.getWinConds();
    winCondWC1.add(new WinCondition("score",0,"equal", true));
    myWS.update(.01);
    assertEquals(1,checker);
  }

  @Test
  void testLose(){
    List<WinCondition> winCondWC1 = wc1.getWinConds();
    winCondWC1.add(new WinCondition("score",72,"equal", false));
    sc1.changeScore(72,false);
    myWS.update(.01);
    assertEquals(2, checker);
  }

  void checkWinLoss(boolean win){
    if(win){
      checker = 1;
    }
    else{
      checker = 2;
    }

  }


}
