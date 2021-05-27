package breakout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * @author Robert Hamilton Barnette
 * This is the Main class for the breakout game which generates
 * the levels as well as the start and end screens.
 */
public class Main extends Application {

  public static final int GAME_WIDTH = 522;
  public static final int GAME_HEIGHT = 600;
  public static final int NUM_LEVELS = 4;
  public static final int NUM_LIVES = 5;
  private ArrayList<Scene> myScenes;


  /**
   * @param stage is the stage on which all the scenes of the game are displayed
   * This method sets up each of the levels and adds them to a particular scene. These
   * scenes are then added to a list and passed to each level object so they can use
   * and change their scenes. This method depends on all the other classes used in this
   * package as they all are components of the game.
   */
  @Override
  public void start(Stage stage) {
    Player myPlayer = new Player(NUM_LIVES);
    myScenes = new ArrayList<>();

    StartScreen myStartScreen = new StartScreen();
    EndScreen myEndScreen = new EndScreen(myPlayer);

    Level level_1 = new Level("level_1.txt", "level1bg.jpg",myPlayer,1);
    Level level_2 = new Level("level_2.txt", "level2bg.jpg", myPlayer,2);
    Level level_3 = new Level("level_3.txt", "level3bg.jpg", myPlayer,3);
    Level level_4 = new Level("level_4.txt", "level4bg.jpg", myPlayer,4);

    Scene start = new Scene(myStartScreen,GAME_WIDTH,GAME_HEIGHT);
    Scene end = new Scene(myEndScreen,GAME_WIDTH,GAME_HEIGHT);

    Scene level_1_scene = new Scene(level_1, GAME_WIDTH, GAME_HEIGHT);
    Scene level_2_scene = new Scene(level_2, GAME_WIDTH, GAME_HEIGHT);
    Scene level_3_scene = new Scene(level_3, GAME_WIDTH, GAME_HEIGHT);
    Scene level_4_scene = new Scene(level_4, GAME_WIDTH, GAME_HEIGHT);

    myScenes.add(start);
    myScenes.add(level_1_scene);
    myScenes.add(level_2_scene);
    myScenes.add(level_3_scene);
    myScenes.add(level_4_scene);
    myScenes.add(end);

    myStartScreen.giveSceneListAndStage(myScenes,stage);
    myEndScreen.giveSceneListAndStage(myScenes,stage);

    level_1.giveSceneListAndStageAndEnd(myScenes,stage,myEndScreen);
    level_2.giveSceneListAndStageAndEnd(myScenes,stage,myEndScreen);
    level_3.giveSceneListAndStageAndEnd(myScenes,stage,myEndScreen);
    level_4.giveSceneListAndStageAndEnd(myScenes,stage,myEndScreen);

    level_1.levelSetUp(true);
    level_2.levelSetUp(true);
    level_3.levelSetUp(true);
    level_4.levelSetUp(true);

    StartScreen startScreen = new StartScreen();
    startScreen.giveSceneListAndStage(myScenes,stage);


    stage.setScene(start);


    stage.show();

  }


  /**
   * @param args Command Line arguments, none are used beyond the default
   * Main method that launches the application
   */
  public static void main(String[] args) {
    launch(args);

  }

}