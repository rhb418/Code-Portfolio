package breakout;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import java.util.HashMap;

/**
 * @author Robert Hamilton Barnette
 * This class defines the behavior for bouncer objects which are used in the level
 * to launch from the paddle and destroy blocks, it also handles collision detection
 * between the bouncer and other objects in the level
 *
 * I believe this class is well designed because it has a clear purpose and clear
 * methods which handle the status of the bouncer and its collisions with other
 * objects.
 */
public class Bouncer extends Circle {
    public static final double BOUNCER_RADIUS = 8;
    public static final double BOUNCER_START_X = Main.GAME_WIDTH/2;
    public static final double BOUNCER_START_Y = Paddle.PADDLE_Y-BOUNCER_RADIUS-5;
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_IN_PLAY = 1;
    public static final double BOUNCER_SPEED = 5;
    public static final int MAX_POWER =4;
    public static final int PLAYER_DEATH = 1;

    private int myStatus;
    private double myXDirection;
    private double myYDirection;
    private int myPower;

    /**
     * Used to create a bouncer object and set its default position, appearance,and behaviors
     */
    public Bouncer(){
        myStatus = STATUS_WAIT;
        myPower = 1;
        setRadius(BOUNCER_RADIUS);
        setCenterX(BOUNCER_START_X);
        setCenterY(BOUNCER_START_Y);

        setStrokeWidth(Paddle.PADDLE_STROKE_WIDTH);
        setStroke(Color.BLACK);
        setFill(Color.CYAN);

    }
    /**
     * This method updates the position of the bouncer whether it is bouncing or still coupled
     * to the paddle
     */
    public void updateBouncerPosition(Paddle paddle, Line line, double bouncerScaling){
        if(myStatus == Bouncer.STATUS_WAIT){
            setCenterX(paddle.getX()+paddle.getWidth()/2);
            setCenterY(Bouncer.BOUNCER_START_Y);
            line.setStartX(getCenterX());
            line.setStartY(getCenterY());
            line.setStrokeWidth(2);
        }
        if(getMyStatus() == Bouncer.STATUS_IN_PLAY){
            setCenterX(getCenterX()+Bouncer.BOUNCER_SPEED*bouncerScaling*myXDirection);
            setCenterY(getCenterY()+Bouncer.BOUNCER_SPEED*bouncerScaling*myYDirection);
            line.setStrokeWidth(0);
        }
    }
    /**
     * Determines whether the bouncer is collided with the paddle and adjusts its velocity
     * accordingly
     */
    public void checkCollisionsWithPaddle(Paddle paddle){
        if(getCenterY()+getRadius() <= Paddle.PADDLE_Y+2 && getCenterY()+getRadius() >=Paddle.PADDLE_Y-2){
            if(getCenterX() >= paddle.getX() && getCenterX() <= paddle.getX()+paddle.getWidth()){
                setMyYDirection(-getMyYDirection());
            }
        }
    }

    /**
     * @returns the block the bouncer collided with or null if none
     * Determines if the bouncer is collided with the blocks
     */
    public Block checkCollisionsWithBlocks(HashMap<Pair<Integer,Integer>, Block> blocks){
        for(Block block: blocks.values()){
            if(intersects(block.getX(),block.getY(),FileReader.blockWidth,FileReader.blockHeight)){
                return block;
            }
        }
        return null;
    }
    /**
     * @returns the powerup the bouncer collided with or null if none
     * Determines if the bouncer is collided with the powerups
     */
    public PowerUp checkCollisionsWithPowerUps(HashMap<Pair<Integer,Integer>, PowerUp> powerUps){
        for(PowerUp powerup: powerUps.values()){
            if(intersects(powerup.getX(),powerup.getY(),FileReader.blockWidth,FileReader.blockHeight)){
                return powerup;
            }
        }
        return null;
    }

    /**
     * @returns an indication of whether the ball has fallen out of the screen and the player has died
     * This method checks to see if the bouncer has hit one of the game walls or has fallen out of the screen
     */
    public int checkCollisionsWithWall(){
        if(getCenterX()+getRadius()>=Main.GAME_WIDTH || getCenterX()-getRadius()<= FileReader.xBegin){
            setMyXDirection(-getMyXDirection());
        }
        if(getCenterY()-getRadius() <= FileReader.yBegin){
            setMyYDirection(-getMyYDirection());
        }
        if(getCenterY()>=Main.GAME_HEIGHT){
            return PLAYER_DEATH;

        }
        return 0;
    }

    /**
     * @returns the status of the bouncer which is whether it is waiting to be launched
     * or has been launched
     */
    public int getMyStatus() {
        return myStatus;
    }

    /**
     * @param myStatus is used to set the status of a bouncer
     */
    public void setMyStatus(int myStatus) {
        this.myStatus = myStatus;
    }

    /**
     * @returns the x direction that the bouncer is moving in
     */
    public double getMyXDirection() {
        return myXDirection;
    }

    /**
     * @param myXDirection sets the x direction of the bouncer
     */
    public void setMyXDirection(double myXDirection) {
        this.myXDirection = myXDirection;
    }

    /**
     * @return the y direction that the bouncer is moving in
     */
    public double getMyYDirection() {
        return myYDirection;
    }

    /**
     * @param myYDirection sets the y direction of the bouncer
     */
    public void setMyYDirection(double myYDirection) {
        this.myYDirection = myYDirection;
    }

    /**
     * @return gets the power of the bouncer. The power of the bouncer determines how much damage it
     * does to blocks
     */
    public int getMyPower() {
        return myPower;
    }

    /**
     * @param myPower sets the power of the bouncer
     */
    public void setMyPower(int myPower) {
        this.myPower = myPower;
    }
}
