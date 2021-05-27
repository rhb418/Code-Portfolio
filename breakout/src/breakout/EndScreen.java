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
 * This class defines the structure and triggers of the EndScreen which displays
 * the final score and outcome of the game
 */
public class EndScreen extends Pane {

    public static final int WIN = 1;
    public static final int LOSS = 0;
    private Text myScore, myOutcome;
    private Button myButton;
    private ArrayList<Scene> mySceneList;
    private Scene myScene;
    private Stage myStage;
    private Player myPlayer;

    /**
     * @param player This is the player object that has been passed from main to the other levels and now the endscreen
     *               providing information about the score and outcome of the game
     * This constructor sets up the end screen by placing text in the proper places and adding a button to start the game
     * over
     */
    public EndScreen(Player player){
        myPlayer = player;
        myOutcome = new Text();
        myOutcome.setFont(Font.font ("Tahoma", 40));
        myOutcome.setX(Main.GAME_WIDTH/2-80);
        myOutcome.setY(50);
        myOutcome.setFill(Color.WHITE);
        myOutcome.setStroke(Color.BLACK);
        myOutcome.setStrokeWidth(1);

        myScore = new Text();
        myScore.setFont(Font.font ("Tahoma", 40));
        myScore.setX(Main.GAME_WIDTH/2-120);
        myScore.setY(200);
        myScore.setFill(Color.WHITE);
        myScore.setStroke(Color.BLACK);
        myScore.setStrokeWidth(1);

        myButton = new Button("Play Again");
        myButton.setMaxWidth(80);
        myButton.setLayoutX(Main.GAME_WIDTH/2-80/2);
        myButton.setLayoutY(300);
        myButton.setOnAction(event -> handleButton());

        getChildren().add(myButton);
        getChildren().add(myOutcome);
        getChildren().add(myScore);
        addBackground();

    }

    /**
     * @param outcome This is the outcome of the game win/los
     * This method displays the outcome of the game as well as the score
     */
    public void showResults(int outcome){
        if(outcome == LOSS){
            myOutcome.setText("You Lose");
        }
        if(outcome == WIN){
            myOutcome.setText("You Win!");
        }
        myScore.setText("Total Score: "+myPlayer.getMyScore());
    }

    /**
     * This method adds a background to the endscreen
     */
    private void addBackground(){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("startbg.jpeg"),Main.GAME_WIDTH,Main.GAME_HEIGHT,false,true);
        BackgroundImage myBI= new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(myBI));
    }

    /**
     * @param sceneList List of scenes present in the game
     * @param stage Stage that the EndScreen is displayed on
     * This method allows the main class to pass information to the EndScreen so that it can set its own triggers
     * and switch to other levels after a certain objective is reached. This method must be called before the screen is
     * displayed
     */
    public void giveSceneListAndStage(ArrayList<Scene> sceneList, Stage stage){
        mySceneList = sceneList;
        myScene = sceneList.get(Main.NUM_LEVELS+1);
        myStage = stage;
    }

    /**
     * This button sends the player back to the start screen when pressed and resets the player's lives and score
     */
    private void handleButton(){
        myPlayer.setMyLives(Main.NUM_LIVES);
        myPlayer.setMyScore(0);
        myStage.setScene(mySceneList.get(0));
    }

}
