import java.util.ArrayList;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

/**
 * Model class to hold data collected from tweet stream.
 * 
 * @author Thomas Clark
 */
public final class TETDataModel {

    /**
     * The array of emotions
     */
    ArrayList<String> emotions;

    /**
     * An array to count how many times each emotion was tweeted
     */
    ArrayList<Integer> counts;

    /**
     * How many tweets have occurred so far
     */
    int tweetCount;

    /**
     * The number of tweets that the tweetCount resets at
     */
    final int tweetNumber = 25;

    /**
     * Default constructor.
     */
    public TETDataModel() {
        /*
         * Initialize model
         */
        this.emotions = getEmotions("resources/emotions.txt");
        this.counts = new ArrayList<Integer>();
        for (int i = 0; i < this.emotions.size(); i++) {
            this.counts.add(0);
        }
        this.tweetCount = 0;
    }

    /**
     * Takes the string containing the files content and extracts oAuth
     * information
     */
    public static ArrayList<String> getEmotions(String fileName) {
        ArrayList<String> emotions = new ArrayList<String>();
        SimpleReader in = new SimpleReader1L(fileName);
        while (!in.atEOS()) {
            emotions.add(in.nextLine());
        }
        in.close();
        return emotions;
    }

    /**
     * Increases this.counts[i] by one.
     */
    public void incrementCount(int i) {
        int current = this.counts.get(i);
        current++;
        this.counts.set(i, current);
        this.tweetCount++;
        if (this.tweetCount > this.tweetNumber) {
            this.tweetCount = 0;
            for (int j = 0; j < this.counts.size(); j++) {
                this.counts.set(j, 0);
            }
        }
    }

    /**
     * Returns the index of the leading emotion
     */
    public int leadingEmotion() {
        int leadingEmotion = 0;
        int leadingAmount = 0;
        for (int j = 0; j < this.counts.size(); j++) {
            if (this.counts.get(j) > leadingAmount) {
                leadingAmount = this.counts.get(j);
                leadingEmotion = j;
            }
        }
        return leadingEmotion;
    }

}
