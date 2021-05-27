package breakout;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Robert Hamilton Barnette
 * This class reads a text file and from it generates two HashMaps one of pairs to blocks and one of
 * pairs to powerups that define the structure of a given level.
 *
 * I believe this class is well designed because it has clear and efficient methods and fulfills its
 * singular purpose of reading a text file to generate a level
 */
public class FileReader {
    private String myFileName;
    private HashMap<Pair<Integer,Integer>, Block> myBlocks;
    private HashMap<Pair<Integer,Integer>, PowerUp> myPowerUps;

    public static final int xBegin = 1;
    public static final int blocksPerLine = 10;
    public static final int yBegin = 50;
    public static final int numBasicBlocktypes = 4;
    public static final int numPowerUps = 5;
    public static final int noBlockID = 0;
    public static final int blockWidth = Main.GAME_WIDTH/10-2;
    public static final int blockHeight = blockWidth/2;
    public static final int gapBetweenBlocks = 2;


    /**
     * @param filename text file that the filereader is reading
     * Creates a filereader object from a filename
     */
    public FileReader(String filename){
        myFileName = filename;
        myBlocks = new HashMap<>();
        myPowerUps = new HashMap<>();
    }

    /**
     * Reads the text file which must be a series of space delimited integers and processes
     * the values into objects based on an encoding
     */
    public void readFile(){
        Scanner scan = new Scanner(this.getClass().getClassLoader().getResourceAsStream(myFileName));
        int valuesRead = 0;
        while (scan.hasNextInt()){
            int value = scan.nextInt();

            int xpos = valuesRead%blocksPerLine*(blockWidth+gapBetweenBlocks)+xBegin;
            int ypos = (valuesRead/blocksPerLine)*(blockHeight+gapBetweenBlocks)+yBegin;

            if(value != noBlockID && value <= numBasicBlocktypes){
                setUpBlocks(value,xpos,ypos);
            }
            else if(value != noBlockID){
                setUpPowerUps(value, xpos,ypos);
            }
            valuesRead++;
        }
    }

    /**
     * @param value Encoding determining the number of hits the block has
     * @param xpos x position of the block
     * @param ypos y position of the block
     * Creates block objects from the data in the text file and adds these objects to a map
     * where they can be accessed by their coordinates
     */
    private void setUpBlocks(int value, int xpos, int ypos){
        Block newBlock = new Block(value,xpos,ypos);

        newBlock.setUp();
        Pair<Integer,Integer> newPair = new Pair<>(xpos,ypos);
        myBlocks.put(newPair,newBlock);

    }

    /**
     * @param value Encoding that determines the type of powerup this is
     * @param xpos x position of the powerup
     * @param ypos y position of the powerup
     * Creates powerup objects from the data in the text file and adds these objects to a map
     * where they can be accessed by their coordinates
     */
    private void setUpPowerUps (int value, int xpos, int ypos){
        PowerUp newPower = new PowerUp(value,xpos,ypos);
        newPower.setUp();
        Pair<Integer,Integer> newPair = new Pair<>(xpos,ypos);
        myPowerUps.put(newPair,newPower);
    }


    /**
     * @returns the map containing the blocks in the level which are accessed by their position coordinates
     */
    public HashMap<Pair<Integer, Integer>, Block> getMyBlocks() {
        return myBlocks;
    }

    /**
     * @returns the map containing the powerups in the level which are accessed by their position coordinates
     */
    public HashMap<Pair<Integer, Integer>, PowerUp> getMyPowerUps() {
        return myPowerUps;
    }
}
