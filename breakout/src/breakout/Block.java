package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.HashMap;

/**
 * @author Robert Barnette
 * This class defines the behaviors and tracks the number of hits of a block object
 */
public class Block extends Rectangle {
    private int myHits;
    private Pair<Integer,Integer> myCoords;
    private HashMap<Integer, Color> myBlockColors;
    public static final double blockStrokeWidth = 1;

    /**
     * @param num_hits Number of hits the block can take before destroyed
     * @param x x coordinate
     * @param y y coordinate
     * Sets the width, height, x and y coordinates of a block
     */
    public Block(int num_hits, int x, int y){
        myHits = num_hits;
        myBlockColors = new HashMap<>();
        myCoords = new Pair<>(x,y);

        setX(x);
        setY(y);

        setWidth(FileReader.blockWidth);
        setHeight(FileReader.blockHeight);

    }

    /**
     * @param hits amount of damage the block has taken when it collides with a bouncer
     * Alters the color of the block based on the number of hits it has left before being destroyed
     */
    public void updateHits(int hits){
        myHits = Math.max(myHits-hits,0);
        setFill(myBlockColors.get(myHits));

    }

    /**
     * Encodes the number of hits the block has left before its destroyed to a
     * particular color that the block is displayed as
     */
    public void setUp(){
        myBlockColors.put(1,Color.RED);
        myBlockColors.put(2,Color.BLUE);
        myBlockColors.put(3,Color.GREEN);
        myBlockColors.put(4,Color.YELLOW);

        setStroke(Color.BLACK);
        setStrokeWidth(blockStrokeWidth);
        setFill(myBlockColors.get(myHits));

    }

    /**
     * @returns the number of hits the block has left before it is destroyed
     */
    public int getMyHits() {
        return myHits;
    }

    /**
     * @returns the coordinates of the block
     */
    public Pair<Integer, Integer> getMyCoords() {
        return myCoords;
    }
}
