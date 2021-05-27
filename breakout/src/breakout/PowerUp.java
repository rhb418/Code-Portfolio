package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.HashMap;


/**
 * @author Robert Hamilton Barnette
 * This class defines the integer encodings which are present in the text file and
 * maps them to particular blocks and powerups which are represented as images in the
 * game.
 */
public class PowerUp extends ImageView {
    public static final int extraDamage = 5;
    public static final int longerPaddle = 6;
    public static final int extraLife = 7;
    public static final int loseLife = 8;
    public static final int unbreakable = 9;
    public static final int explosion = 10;

    private int myType;
    private String myExtraDamage = "extra_damage.jpg";
    private String myExtraLife = "heart.jpg";
    private String myLoseLife = "lose_life.jpg";
    private String myUnbreakable = "stone_brick.jpg";
    private String myExplosion = "explosion.jpg";
    private String myPaddleImage = "paddle.png";
    private Image myImage;

    private HashMap<Integer, String> myMap;
    private Pair<Integer,Integer> myCoords;


    /**
     * @param type This is the integer encoding for the type of powerup this object is
     *             it gets converted to a filename which is the image the powerup is given as in game
     * @param xpos x position of the powerup
     * @param ypos y position of the powerup
     * This constructor sets the position of the powerup and generates a pair object for its coordinates
     */
    public PowerUp(int type, int xpos, int ypos){
        myType = type;
        myMap = new HashMap<>();
        myCoords = new Pair<>(xpos,ypos);
        setX(xpos);
        setY(ypos);
    }

    /**
     * This method sets up the encodings for the powerups and maps an image onto the powerup object
     */
    public void setUp(){
        myMap.put(5, myExtraDamage);
        myMap.put(6, myPaddleImage);
        myMap.put(7, myExtraLife);
        myMap.put(8, myLoseLife);
        myMap.put(9,myUnbreakable);
        myMap.put(10,myExplosion);

        myImage = new Image(this.getClass().getClassLoader().getResourceAsStream(myMap.get(myType)));
        setImage(myImage);
        setFitHeight(FileReader.blockHeight);
        setFitWidth(FileReader.blockWidth);
    }

    /**
     * @returns the type of powerup that this object is
     */
    public int getMyType() {
        return myType;
    }

    /**
     * @returns the coordinates of this powerup
     */
    public Pair<Integer, Integer> getMyCoords() {
        return myCoords;
    }
}
