import java.awt.Color;
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
     * An array to hold the background color for each emotion
     */
    ArrayList<Color> colors;

    /**
     * An array to count how many times each emotion was tweeted
     */
    ArrayList<Integer> counts;

    /**
     * How many seconds have pasted since the tweet display was changed. Is
     * reset by TETStatusListener when it updates the tweet display
     */
    int secondsPast;

    /**
     * How many tweets have occurred so far
     */
    int tweetCount;

    /**
     * ArrayList to hold ArrayList of the tweet counts for the past 60 seconds
     */
    ArrayList<ArrayList<Integer>> past60Seconds;

    /**
     * The total count for each emotion for the entirety of the past 60 seconds
     */
    ArrayList<Integer> totalCountsPast60Seconds;

    /**
     * Default constructor.
     */
    public TETDataModel() {
        /*
         * Initialize model
         */
        ArrayList<ArrayList> array = getEmotionsAndColors("resources/emotions.txt");
        this.emotions = array.get(0);
        this.colors = array.get(1);
        this.counts = new ArrayList<Integer>();
        this.totalCountsPast60Seconds = new ArrayList<Integer>();
        this.past60Seconds = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 60; i++) {
            ArrayList<Integer> initList = new ArrayList<Integer>();
            for (int j = 0; j < this.emotions.size(); j++) {
                initList.add(0);
            }
            this.past60Seconds.add(initList);
        }
        for (int i = 0; i < this.emotions.size(); i++) {
            this.counts.add(0);
            this.totalCountsPast60Seconds.add(0);
        }
        this.tweetCount = 0;
        this.secondsPast = 0;
    }

    /**
     * Takes the string containing the emotions and puts then into an ArrayList
     */
    public static ArrayList<ArrayList> getEmotionsAndColors(String fileName) {
        ArrayList<ArrayList> array = new ArrayList<ArrayList>();
        ArrayList<String> emotions = new ArrayList<String>();
        ArrayList<String> colorStrings = new ArrayList<String>();
        ArrayList<Color> colors = new ArrayList<Color>();
        SimpleReader in = new SimpleReader1L(fileName);
        while (!in.atEOS()) {
            String line = in.nextLine();
            String emotion = "";
            String color = "";
            boolean emotionOrColor = false;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == ' ') {
                    emotionOrColor = true;
                } else if (!emotionOrColor) {
                    emotion = emotion + Character.toString(c);
                } else {
                    color = color + Character.toString(c);
                }
            }
            emotions.add(emotion);
            colorStrings.add(color);
        }
        in.close();
        colors = setColors(colorStrings);
        array.add(emotions);
        array.add(colors);
        return array;
    }

    /**
     * Sets the background color for each emotion
     */
    public static ArrayList<Color> setColors(ArrayList<String> strings) {
        ArrayList<Color> colors = new ArrayList<Color>();
        for (int i = 0; i < strings.size(); i++) {
            String color = strings.get(i);
            switch (color) {
                case "black":
                    colors.add(Color.black);
                    break;
                case "blue":
                    colors.add(Color.blue);
                    break;
                case "cyan":
                    colors.add(Color.cyan);
                    break;
                case "darkGray":
                    colors.add(Color.darkGray);
                    break;
                case "gray":
                    colors.add(Color.gray);
                    break;
                case "green":
                    colors.add(Color.green);
                    break;
                case "lightGray":
                    colors.add(Color.lightGray);
                    break;
                case "magenta":
                    colors.add(Color.magenta);
                    break;
                case "orange":
                    colors.add(Color.orange);
                    break;
                case "pink":
                    colors.add(Color.pink);
                    break;
                case "red":
                    colors.add(Color.red);
                    break;
                case "white":
                    colors.add(Color.white);
                    break;
                case "yellow":
                    colors.add(Color.yellow);
                    break;
                default:
                    colors.add(Color.white);
                    break;
            }
        }
        return colors;
    }

    /**
     * Increases this.counts[i] by one.
     */
    public void incrementCount(int i) {
        int future = this.counts.get(i) + 1;
        this.counts.set(i, future);
        this.tweetCount++;
        this.updateTotalCounts();
    }

    /**
     * Recounts and updates this.totalCountsPast60Seconds
     */
    public void updateTotalCounts() {
        for (int j = 0; j < this.totalCountsPast60Seconds.size(); j++) {
            this.totalCountsPast60Seconds.set(j, 0);
        }
        for (int i = 0; i < this.past60Seconds.size(); i++) {
            ArrayList<Integer> arrayList = this.past60Seconds.get(i);
            for (int j = 0; j < arrayList.size(); j++) {
                int previous = this.totalCountsPast60Seconds.get(j);
                int future = previous + arrayList.get(j);
                this.totalCountsPast60Seconds.set(j, future);
            }
        }
    }

    /**
     * Returns the index of the leading emotion from within the past second
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

    /**
     * Reports this.tweetCount.
     */
    public int tweetCount() {
        return this.tweetCount;
    }
}
