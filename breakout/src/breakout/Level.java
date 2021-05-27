package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Robert Hamilton Barnette
 * This class is the blueprint from which levels can be created from text files and
 * displayed on the screen. This class dictates the behavior of levels when they are
 * displayed and handles the user interactions/physics of the game.
 */
public class Level extends Pane {
    private String myFilename;
    private static final int PADDLE_SPEED = 3;
    private static final double REFRESH_RATE = 1.0/60;

    private FileReader myFileReader;
    private Paddle myPaddle;
    private Scene myScene;
    private ArrayList<Scene> mySceneList;
    private HashMap<Pair<Integer, Integer>, Block> myBlocks;
    private HashMap<Pair<Integer,Integer>, PowerUp> myPowerUps;
    private Bouncer myBouncer;
    private Line myTargetLine;
    private String myBackground;
    private Player myPlayer;
    private TextField myCheats;
    private int myLevelID;
    private Text myLevel, myScore, myCheatLabel, myLives;
    private Stage myStage;
    private EndScreen myEndScreen;
    private double bouncerScaling;
    private double paddleScaling;

    /**
     * @param filename This is the name of the text file from which the level is created. This file consists of
     * space delimited integers that each correspond to a particular block or powerup
     * @param background This is the name of the image file that is used for the background
     * @param player This is the player object that keeps track of the player's score and lives across levels
     * @param ID This is the level number that defines this particular level
     * This constructor takes the above arguments and uses them to create a particular level. It also initializes
     * several instance variables which are used as a part of the level
     */
    public Level(String filename, String background, Player player,int ID){
        bouncerScaling = 1;
        paddleScaling = 1;
        myFilename = filename;
        myLevelID = ID;
        myBackground = background;
        myPlayer = player;
        myFileReader = new FileReader(myFilename);
        myPaddle = new Paddle();
        myBouncer = new Bouncer();
        myTargetLine = new Line();

    }

    /**
     * @param sceneList This is a list of the scenes of all the levels and screens used in this game
     * @param stage This is the stage the game is played on
     * @param end This is the endScreen object that displays the results when the game is finished
     * This method allows the main class to pass information to the level so that it can set its own triggers
     * and switch to other levels after a certain objective is reached. If this is not called before levelSetup
     * in main then the game will not work
     */
    public void giveSceneListAndStageAndEnd(ArrayList<Scene> sceneList, Stage stage, EndScreen end){
        myEndScreen = end;
        mySceneList = sceneList;
        myScene = sceneList.get(myLevelID);
        myStage = stage;
    }

    /**
     * @param start This determines whether this method is being called to start the level or reset it
     * This method sets up all of the objects displayed in the game as well as their event handlers
     * and should be called after the constructor and giveSceneListAndStageAndEnd have been called.
     * This method can also be called after the level has been completed to reset it.
     */
    public void levelSetUp(boolean start){
        addBlocksAndPowerUps();
        addBackground();
        setUpTargetLine();
        addTextField();
        addText();
        setUpPaddleAndBouncer();


        if(start){
            myScene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
            myScene.setOnKeyReleased(event -> handleKeyRelease(event.getCode()));
            myScene.setOnMouseMoved(event -> handleMouseMove(event.getSceneX(),event.getSceneY()));
            myScene.setOnMouseClicked(event-> handleMouseClick(event.getSceneX(),event.getSceneY()));

            //The 5 below lines were taken from lab_bounce authored by Robert Duvall
            KeyFrame frame = new KeyFrame(Duration.seconds(REFRESH_RATE), e -> updateScene());
            Timeline animation = new Timeline();
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.getKeyFrames().add(frame);
            animation.play();
        }


    }

    /**
     * This method runs constantly with a refresh rate of 60 Hz to update the level in response to
     * user input
     */
    private void updateScene(){

        myPaddle.setX(myPaddle.getX()+myPaddle.getDirection()*PADDLE_SPEED*paddleScaling);
        myLives.setText("Lives: "+ myPlayer.getMyLives());
        myScore.setText("Score: "+myPlayer.getMyScore());
        checkPaddleBounds();
        myBouncer.updateBouncerPosition(myPaddle,myTargetLine,bouncerScaling);
        checkCollisions();


    }

    /**
     * @param code This is the keycode that is passed to the event handler to determine what key was pressed.
     * This method alters the position of the bouncer and paddle in response to actions by the player.
     */
    private void handleKeyPress(KeyCode code){
        if(code == KeyCode.RIGHT){
            myPaddle.setDirection(1);

        }
        if(code == KeyCode.LEFT){
            myPaddle.setDirection(-1);

        }
        if(code == KeyCode.SPACE){
            if(myBouncer.getCenterY()+myBouncer.getRadius() <= Paddle.PADDLE_Y && myBouncer.getCenterY()+myBouncer.getRadius() >=Paddle.PADDLE_Y-30){
                if(myBouncer.getCenterX() >= myPaddle.getX() && myBouncer.getCenterX() <= myPaddle.getX()+myPaddle.getWidth()){
                    myBouncer.setMyStatus(Bouncer.STATUS_WAIT);
                }

            }

        }

    }

    /**
     * @param code This is the keycode that is passed to the event handler to determine what key was pressed.
     * This method stops the motion of the paddle when an arrow key is released
     */
    private void handleKeyRelease(KeyCode code){
        if(code == KeyCode.RIGHT || code == KeyCode.LEFT){
            myPaddle.setDirection(0);

        }

    }

    /**
     * @param x This is the x position of the mouse
     * @param y This is the y position of the mouse
     * This method makes a dotted line follow the mouse cursor when the ball is in the targeting phase
     */
    private void handleMouseMove(double x, double y){
        if(myBouncer.getMyStatus() == Bouncer.STATUS_WAIT){
            myTargetLine.setEndX(x);
            myTargetLine.setEndY(y);
        }

    }

    /**
     * @param x This is the x position of the mouse click
     * @param y This is the y position of the mouse click
     * This method shoots the bouncer in a particular direction when the mouse is clicked while the
     * bouncer is in targeting mode
     */
    private void handleMouseClick(double x, double y){

        if(myBouncer.getMyStatus() == Bouncer.STATUS_WAIT){
            double xcor = x - myBouncer.getCenterX();
            double ycor = myBouncer.getCenterY()-y;
            double magnitude = Math.sqrt(Math.pow(xcor,2)+Math.pow(ycor,2));
            myBouncer.setMyXDirection(xcor/magnitude);
            myBouncer.setMyYDirection(-ycor/magnitude);
            myBouncer.setMyStatus(Bouncer.STATUS_IN_PLAY);
        }
    }

    /**
     * This method checks the paddles position and moves it to the opposite side of the
     * screen if if goes too far in one direction
     */
    private void checkPaddleBounds(){
        double location = myPaddle.getX();
        if(location < -40){
            myPaddle.setX(Main.GAME_WIDTH-40);
        }
        if(location > Main.GAME_WIDTH-40){
            myPaddle.setX(-40);
        }
    }


    /**
     * This method checks to see if the bouncer has collided with the walls, the paddle
     * the powerups or the blocks
     */
    private void checkCollisions(){
        if(myBouncer.checkCollisionsWithWall() == Bouncer.PLAYER_DEATH){
            myPlayer.setMyLives(myPlayer.getMyLives()-1);
            myLives.setText("Lives: "+ myPlayer.getMyLives());
            if(myPlayer.getMyLives() == 0){
                youLose();
            }
            myBouncer.setMyStatus(Bouncer.STATUS_WAIT);
        }
        myBouncer.checkCollisionsWithPaddle(myPaddle);
        Block collidedBlock = myBouncer.checkCollisionsWithBlocks(myBlocks);
        PowerUp collidedPowerUp = myBouncer.checkCollisionsWithPowerUps(myPowerUps);

        if(collidedBlock!=null){
            adjustBlockAndBouncer(collidedBlock);
            collidedPowerUp = null;
        }
        if(collidedPowerUp!=null){
            adjustPowerUpAndBouncer(collidedPowerUp);
        }

    }

    /**
     * @param block This is the block that is collided with the bouncer that will have its status altered by this method
     * This method alters the number of hits remaining on the block and the direction of the bouncer. It also checks to
     * see if there are any blocks remaining and invokes a win condition if there are none
     */
    private void adjustBlockAndBouncer(Block block){
        if(myBouncer.getCenterX()>block.getX()+FileReader.blockWidth || myBouncer.getCenterX() < block.getX()){
            myBouncer.setMyXDirection(-myBouncer.getMyXDirection());
        }
        if(myBouncer.getCenterY()>block.getY()+FileReader.blockHeight || myBouncer.getCenterY()<block.getY()){
            myBouncer.setMyYDirection(-myBouncer.getMyYDirection());
        }
        myPlayer.setMyScore(myPlayer.getMyScore()+Math.min(block.getMyHits(),myBouncer.getMyPower()));
        myScore.setText("Score: " + myPlayer.getMyScore());

        block.updateHits(myBouncer.getMyPower());

        if(block.getMyHits()==0) {
            getChildren().remove(myBlocks.get(block.getMyCoords()));
            myBlocks.remove(block.getMyCoords());
            if(myBlocks.isEmpty()){
                youWin();
            }
        }

    }

    /**
     * @param powerup This is the powerup that is collided with the bouncer
     * This method alters the direction of the bouncer after it collides with a powerup
     * and then calls another method to execute the behavior of the powerup
     */
    private void adjustPowerUpAndBouncer(PowerUp powerup){
        if(myBouncer.getCenterX()>powerup.getX()+FileReader.blockWidth || myBouncer.getCenterX() < powerup.getX()){
            myBouncer.setMyXDirection(-myBouncer.getMyXDirection());
        }
        if(myBouncer.getCenterY()>powerup.getY()+FileReader.blockHeight || myBouncer.getCenterY()<powerup.getY()){
            myBouncer.setMyYDirection(-myBouncer.getMyYDirection());
        }
        executePowerUp(powerup);
    }

    /**
     * @param powerup This is the powerup being executed
     * This method first determines what kind of powerup has been hit and then executes its behavior. It also checks
     * win and loss conditions in the case of the life gain and lose powerups.
     */
    private void executePowerUp(PowerUp powerup){
        int pType = powerup.getMyType();
        if(pType == PowerUp.unbreakable){
            return;
        }
        if (pType == PowerUp.longerPaddle){
            myPaddle.setX(myPaddle.getX()-Paddle.SIZE_INCREASE/2);
            myPaddle.setWidth(Paddle.DEFAULT_PADDLE_WIDTH+Paddle.SIZE_INCREASE);
        }
        if(pType == PowerUp.extraLife){
            myPlayer.setMyLives(myPlayer.getMyLives()+1);
            myLives.setText("Lives: "+ myPlayer.getMyLives());
        }
        if(pType == PowerUp.loseLife){
            myPlayer.setMyLives(myPlayer.getMyLives()-1);
            myLives.setText("Lives: "+ myPlayer.getMyLives());
            if(myPlayer.getMyLives()==0){
                youLose();
            }
        }
        if(pType == PowerUp.extraDamage){
            myBouncer.setMyPower(myBouncer.getMyPower()+1);
        }
        if(pType == PowerUp.explosion){
            destroySurroundingBlocks(powerup);
        }
        getChildren().remove(myPowerUps.get(powerup.getMyCoords()));
        myPowerUps.remove(powerup.getMyCoords());
    }

    /**
     * @param powerUp This is the explosive block that has been triggered
     * This method removes any blocks in a 3x3 grid around where the explosive block was triggered.
     * This effect does not apply to powerups however.
     */
    private void destroySurroundingBlocks(PowerUp powerUp){
        int xCord = (int) powerUp.getX();
        int yCord = (int) powerUp.getY();
        int topLeftXCord = xCord - FileReader.blockWidth-FileReader.gapBetweenBlocks;
        int topLeftYCord = yCord -FileReader.blockHeight-FileReader.gapBetweenBlocks;

        //These for loops check blocks in a 3x3 grid around the explosive block and destroy any that are found
        for(int k=0; k<3; k++){
            for(int j = 0; j<3; j++){
                Pair<Integer,Integer> blockID = new Pair<>(topLeftXCord+k*(FileReader.blockWidth+FileReader.gapBetweenBlocks), topLeftYCord+j*(FileReader.blockHeight+FileReader.gapBetweenBlocks));
                if(myBlocks.get(blockID)!=null){
                    getChildren().remove(myBlocks.get(blockID));
                    myPlayer.setMyScore(myPlayer.getMyScore()+myBlocks.get(blockID).getMyHits());
                    myBlocks.remove(blockID);
                    if(myBlocks.isEmpty()){
                        youWin();
                    }
                    myScore.setText("Score: " + myPlayer.getMyScore());
                }
            }
        }

    }

    /**
     * This method uses a filereader object to read the blocks from a file and then add
     * them to the scene.
     */
    private void addBlocksAndPowerUps(){
        myFileReader.readFile();
        myBlocks = myFileReader.getMyBlocks();
        myPowerUps = myFileReader.getMyPowerUps();

        for(Block k: myBlocks.values()){
            getChildren().add(k);
        }
        for(PowerUp k: myPowerUps.values()){
            getChildren().add(k);
        }
    }

    /**
     * This method adds a background to the level
     */
    private void addBackground(){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(myBackground),Main.GAME_WIDTH,Main.GAME_HEIGHT,false,true);
        BackgroundImage myBI= new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(myBI));
    }

    private void setUpTargetLine(){
        myTargetLine.getStrokeDashArray().addAll(2d,21d);
        myTargetLine.setStroke(Color.WHITE);
        myTargetLine.setStrokeWidth(2);
        getChildren().add(myTargetLine);
    }

    /**
     * This method sets the default positions and behaviors for the paddle and bouncer
     */
    private void setUpPaddleAndBouncer(){
        myPaddle.setWidth(Paddle.DEFAULT_PADDLE_WIDTH);
        myPaddle.setX(Main.GAME_WIDTH/2-Paddle.DEFAULT_PADDLE_WIDTH/2);
        myBouncer.setMyPower(1);
        myBouncer.setMyStatus(Bouncer.STATUS_WAIT);
        getChildren().add(myPaddle);
        getChildren().add(myBouncer);
    }

    /**
     * This method adds the textfield where cheats can be typed
     */
    private void addTextField(){
        myCheats = new TextField();
        myCheats.setLayoutX(467);
        myCheats.setLayoutY(15);
        myCheats.setMaxWidth(50);
        getChildren().add(myCheats);
        myCheats.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
        myCheats.setOnAction(this::executeCheatCodes);
    }

    /**
     * This method adds the text that indicates the level number, player score and player lives
     */
    private void addText(){
        myLevel = new Text();
        myLevel.setText("Level: " + myLevelID);
        myLevel.setX(5);
        myLevel.setY(35);

        myLives = new Text();
        myLives.setText("Lives: "+ myPlayer.getMyLives());
        myLives.setX(105);
        myLives.setY(35);

        myScore = new Text();
        myScore.setText("Score: "+ myPlayer.getMyScore());
        myScore.setX(205);
        myScore.setY(35);

        myCheatLabel = new Text();
        myCheatLabel.setText("Cheats:");
        myCheatLabel.setX(380);
        myCheatLabel.setY(35);

        setFont(myCheatLabel);
        setFont(myScore);
        setFont(myLevel);
        setFont(myLives);

    }

    /**
     * @param text This is the text being processed
     * This method configures the style for text being displayed in the level
     */
    private void setFont(Text text){
        text.setFont(Font.font ("Tahoma", 22));
        text.setFill(Color.WHITE);
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(.5);
        getChildren().add(text);
    }

    /**
     * Resets the level and jumps to the end screen when a loss condition is detected
     */
    private void youLose(){
        resetLevel();
        myEndScreen.showResults(myEndScreen.LOSS);
        myStage.setScene(mySceneList.get(Main.NUM_LEVELS+1));
    }

    /**
     * Resets the level and switches to the next level or the end screen when a win condition
     * is detected
     */
    private void youWin(){
        resetLevel();
        myEndScreen.showResults(myEndScreen.WIN);
        myStage.setScene(mySceneList.get(myLevelID+1));
    }

    /**
     * @param event This is the event created when a string is entered in the cheat code textfield
     * This method executes a particular cheat code based on the character entered in the textfield
     * at the time the method is called.
     */
    private void executeCheatCodes(ActionEvent event){
        String text = myCheats.getText();
        if(text == null || text.length()>1){
            return;
        }
        else{
            int level = Character.getNumericValue(text.charAt(0));
            if(level>=1 && level<=9){
                resetLevel();
                myStage.setScene(mySceneList.get(Math.min(level,Main.NUM_LEVELS)));
            }
            else if(text.charAt(0) == 'G'){
                myBouncer.setMyPower(Bouncer.MAX_POWER);
            }
            else if(text.charAt(0) == 'P'){
                paddleScaling = 2;
            }
            else if(text.charAt(0) == 'B'){
                bouncerScaling = .5;
            }
            else if(text.charAt(0) == 'R'){
                myBouncer.setMyStatus(Bouncer.STATUS_WAIT);
            }
            else if(text.charAt(0) == 'L'){
                myPlayer.setMyLives(myPlayer.getMyLives()+5);
                myLives.setText("Lives: "+myPlayer.getMyLives());

            }
        }
    }

    /**
     * This resets all of the objects and overall state of the level
     */
    private void resetLevel(){
        paddleScaling = 1;
        bouncerScaling =1;
        getChildren().clear();
        myBlocks.clear();
        myPowerUps.clear();
        levelSetUp(false);
    }


}
