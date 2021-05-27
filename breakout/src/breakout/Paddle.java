package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

/**
 * @author Robert Hamilton Barnette
 * This class defines the behaviors and attributes of paddle objects
 * which are used during the level.
 */
public class Paddle extends Rectangle {
    public static final double PADDLE_HEIGHT = 10;
    public  static final double PADDLE_Y = 520;
    public static final int GAME_WIDTH = 500;
    public  static final double ARC_WIDTH = 10;
    public  static final double  SIZE_INCREASE = 60;
    public static final double DEFAULT_PADDLE_WIDTH = 80;
    public static final double PADDLE_STROKE_WIDTH = 2;

    private int myDirection;
    private double myPaddleWidth;


    /**
     * Constructor used to create a paddle object that sets the default position and
     * appearance for the paddle
     */
    public Paddle(){
        myPaddleWidth = DEFAULT_PADDLE_WIDTH;
        myDirection = 0;

        setHeight(PADDLE_HEIGHT);
        setWidth(myPaddleWidth);
        setArcHeight(ARC_WIDTH);
        setArcWidth(ARC_WIDTH);


        setX(GAME_WIDTH/2 -myPaddleWidth/2);
        setY(PADDLE_Y);

        setFill(Color.WHITE);
        setStrokeWidth(PADDLE_STROKE_WIDTH);
        setStroke(Color.BLACK);
    }

    /**
     * @returns the direction of the paddle's movement
     */
    public int getDirection(){
        return myDirection;
    }

    /**
     * @param direction sets the direction of the paddle's movement
     */
    public void setDirection(int direction){
        myDirection = direction;
    }



}
