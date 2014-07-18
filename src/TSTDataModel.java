import java.util.Date;

/**
 * Model class to hold data collected from tweet stream.
 * 
 * @author Thomas Clark
 */
public final class TSTDataModel {

    /**
     * Model variables.
     */
    int smileCount;
    int frownCount;
    Date startDate;
    /*
     * The happyOrSadArray records how many smile/frowns occurred in the last
     * few tweets. The amount recorded is changed from within the tweetNumber
     * variable. The overall result of whether there are more smiles or frowns
     * in the array is recorded in the happyOrSad variable.
     */
    boolean happyOrSad; //Happy is true, sad is false
    boolean[] happyOrSadArray; //Happy is true, sad is false
    int arrayIndex;
    /*
     * tweetNumber variable determines how large the happyOrSad array is. Since
     * Twitter *usually* tends on the side of more smiley face tweets to frowny
     * face tweets, having a large number of values in the happyToSadArray will
     * cause the happyToSad value to never change from happy. Decreasing the
     * tweetNumber will cause the happyToSad value to become more volatile,
     * making the value more indicative of the actual instantaneous smile/frown
     * measurement.
     */
    final int tweetNumber = 25;
    /*
     * Used to count how many tweets have been tweeted, restarts after 100
     */
    int tweetCounter;

    /**
     * Default constructor.
     */
    public TSTDataModel() {
        /*
         * Initialize model
         */
        this.smileCount = 0;
        this.frownCount = 0;
        this.happyOrSadArray = new boolean[this.tweetNumber];
        this.happyOrSad = true;
        this.arrayIndex = 0;
        this.startDate = new Date();
    }

    /**
     * Reports this.smileCount.
     */
    public int smileCount() {
        return this.smileCount;
    }

    /**
     * Reports this.frownCount.
     */
    public int frownCount() {
        return this.frownCount;
    }

    /**
     * Sets this.smileCount to argument.
     */
    public void setSmileCount(int input) {
        this.smileCount = input;
    }

    /**
     * Sets this.frownCount to argument.
     */
    public void setFrownCount(int input) {
        this.frownCount = input;
    }

    /**
     * Increases this.smileCount by one.
     */
    public void incrementSmileCount() {
        this.smileCount++;
    }

    /**
     * Increases this.frownCount by one.
     */
    public void incrementFrownCount() {
        this.frownCount++;
    }

    /**
     * Reports start date.
     */
    public Date date() {
        return this.startDate;
    }

    /**
     * Resets the start date.
     */
    public void resetDate() {
        this.startDate = new Date();
    }

    /**
     * Sets this.arrayIndex to zero.
     */
    public void resetArrayIndex() {
        this.arrayIndex = 0;
    }

    /**
     * Increases this.arrayIndex by one.
     */
    public void incrementArrayIndex() {
        this.arrayIndex++;
    }

    /**
     * Records happyOrSad instance in array.
     */
    public void recordHappyOrSad(boolean happyOrSad) {
        if (this.arrayIndex == this.tweetNumber) {
            this.resetArrayIndex();
        }
        this.happyOrSadArray[this.arrayIndex] = happyOrSad;
        this.incrementArrayIndex();
        this.calculateHappyOrSad();
    }

    /**
     * Calculates whether Twitter is happyOrSad.
     */
    public void calculateHappyOrSad() {
        int happyCount = 0;
        int sadCount = 0;
        for (int i = 0; i < this.tweetNumber; i++) {
            if (this.happyOrSadArray[i]) {
                happyCount++;
            } else {
                sadCount++;
            }
        }

        if (happyCount >= sadCount) {
            this.happyOrSad = true;
        } else {
            this.happyOrSad = false;
        }
    }

    /**
     * Increases this.tweetCounter by one.
     */
    public void incrementTweetCounter() {
        this.tweetCounter++;
        /*
         * Change this if-statement to determine how fast tweets appear on
         * screen
         */
        if (this.tweetCounter == 51) {
            this.tweetCounter = 0;
        }
    }

    /**
     * Returns tweetCounter
     */
    public int tweetCounter() {
        return this.tweetCounter;
    }
}
