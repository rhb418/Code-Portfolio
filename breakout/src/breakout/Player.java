package breakout;

/**
 * @author Robert Barnette
 * This class contains information about the player such as lives and score
 */
public class Player {
    private int myLives;
    private int myScore;

    /**
     * @param lives Number of lives the player is initally given
     * Sets the starting score of a player to 0
     */
    public Player(int lives){
        myLives = lives;
        myScore = 0;

    }

    /**
     * @returns number of lives the player has
     */
    public int getMyLives() {
        return myLives;
    }

    /**
     * @param myLives sets the number of lives the player has
     */
    public void setMyLives(int myLives) {
        this.myLives = myLives;
    }

    /**
     * @returns the player's score
     */
    public int getMyScore() {
        return myScore;
    }

    /**
     * @param myScore sets the player's score
     */
    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }
}
