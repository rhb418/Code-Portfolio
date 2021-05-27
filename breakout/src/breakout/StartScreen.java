package breakout;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Robert Hamilton Barnette
 * This class defines the behavior for the start screen present in this game
 */
public class StartScreen extends Pane {
    private Button myButton;
    private ArrayList<Scene> mySceneList;
    private Scene myScene;
    private Stage myStage;
    private Text myTitle, myRules, myRulesTitle;


    /**
     * Constructor for StartScreen objects that creates and positions text and a button that
     * is displayed when a StartScreen is shown
     */
    public StartScreen(){
        myTitle = new Text("Ultimate Breakout");
        myTitle.setFont(Font.font ("Tahoma", 40));
        myTitle.setX(95);
        myTitle.setY(50);
        myTitle.setFill(Color.WHITE);
        myTitle.setStroke(Color.BLACK);
        myTitle.setStrokeWidth(1);

        myRulesTitle = new Text("Rules");
        myRulesTitle.setUnderline(true);
        myRulesTitle.setFont(Font.font ("Tahoma", 30));
        myRulesTitle.setX(Main.GAME_WIDTH/2-40);
        myRulesTitle.setY(150);
        myRulesTitle.setFill(Color.WHITE);
        myRulesTitle.setStroke(Color.BLACK);
        myRulesTitle.setStrokeWidth(.5);

        myRules = new Text("\u2022 The player starts with 5 lives. Lives are lost when the\n" +
                           "ball falls out of the bottom of the screen.\n"+
                           "\u2022 The paddle can be used to prevent the ball from falling.\n" +
                           "Pressing the space bar when the ball is near the paddle\n" +
                           "allows the player to catch it and launch it in a chosen\n" +
                           "direction using the mouse\n" +
                           "\u2022 PowerUps can be destroyed to give the player bonuses\n" +
                           "\u2022 The level ends when the player runs out of lives\n " +
                           "or all colored blocks are destroyed");

        myRules.setFont(Font.font ("Tahoma", 20));
        myRules.setX(Main.GAME_WIDTH/2-250);
        myRules.setY(180);
        myRules.setFill(Color.WHITE);
        myRules.setStroke(Color.BLACK);
        myRules.setStrokeWidth(.25);

        myButton = new Button("Start Game");
        myButton.setMaxWidth(80);
        myButton.setLayoutX(Main.GAME_WIDTH/2-80/2);
        myButton.setLayoutY(450);
        myButton.setOnAction(event -> handleButton());

        getChildren().add(myRulesTitle);
        getChildren().add(myButton);
        getChildren().add(myTitle);
        getChildren().add(myRules);
        addBackground();

    }

    /**
     * @param sceneList List of scenes present in the game
     * @param stage Stage that the StartScreen is displayed on
     * This method allows the main class to pass information to the StartScreen so that it can set its own triggers
     * and switch to other levels after a certain objective is reached. This method must be called before the screen is
     * displayed
     */
    public void giveSceneListAndStage(ArrayList<Scene> sceneList, Stage stage){
        mySceneList = sceneList;
        myScene = sceneList.get(0);
        myStage = stage;
    }

    /**
     * Launches the first level when the button is pressed
     */
    private void handleButton(){
        myStage.setScene(mySceneList.get(1));
    }

    /**
     * Adds a background image to the start screen
     */
    private void addBackground(){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("startbg.jpeg"),Main.GAME_WIDTH,Main.GAME_HEIGHT,false,true);
        BackgroundImage myBI= new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(myBI));
    }
}
