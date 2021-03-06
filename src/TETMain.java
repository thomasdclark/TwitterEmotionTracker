import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

/**
 * Simple Twitter stream application that recieves a stream of tweets containing
 * smiley faces and frowny faces, counts how many of each are occurring during a
 * period of tweets, and determines if Twitter is happy or sad during that
 * period.
 * 
 * @author Thomas Clark
 */
public final class TETMain {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TETMain() {
    }

    /**
     * Take the file containing the OAuth information and turns it into a string
     */
    public static String readOAuth(String fileName) {
        String content = null;
        File file = new File(fileName);
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * Takes the string containing the files content and extracts oAuth
     * information
     */
    public static String[] extractOAuth(String fileContent) {
        String[] oAuth = new String[8];
        int j = 0;
        oAuth[0] = "";
        for (int i = 0; i < fileContent.length(); i++) {
            if (fileContent.charAt(i) != '\n') {
                oAuth[j] = oAuth[j] + fileContent.charAt(i);
            } else {
                j++;
                oAuth[j] = "";
            }
        }
        return oAuth;
    }

    /**
     * Takes the string containing the emotions and puts then into an ArrayList
     */
    public static ArrayList<String> getEmotions(String fileName) {
        ArrayList<String> emotions = new ArrayList<String>();
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
        }
        in.close();
        return emotions;
    }

    /**
     * Main function
     */
    public static void main(String[] args) throws TwitterException {

        //Use the Mac OS X menu bar for JMenuBar Instance
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty(
                    "com.apple.mrj.application.apple.menu.about.name",
                    "TwitterEmotionTracker");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("InstantiationException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException: " + e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("UnsupportedLookAndFeelException: "
                    + e.getMessage());
        }

        /*
         * OAuth configuration by extracting a text file. Go to the /resources
         * folder and rename the file 'example_oauth.txt' to 'oauth.txt'. Then
         * change the content within to the correct information by replacing the
         * '---' for each field.
         */
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        String fileContent = readOAuth("resources/oauth.txt");
        String[] oAuth = extractOAuth(fileContent);
        String consumerKey = oAuth[1];
        String consumerSecret = oAuth[3];
        String accessToken = oAuth[5];
        String accessSecret = oAuth[7];
        cb.setOAuthConsumerKey(consumerKey);
        cb.setOAuthConsumerSecret(consumerSecret);
        cb.setOAuthAccessToken(accessToken);
        cb.setOAuthAccessTokenSecret(accessSecret);

        //Create stream of tweets
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
                .getInstance();

        //Create data model
        TETDataModel model = new TETDataModel();

        /*
         * Create view
         */
        TETView view = new TETView1();

        /*
         * Create controller
         */
        TETController controller = new TETController1(model, view);

        //Register observer in view
        view.registerObserver(controller);

        //Initialize status listener
        StatusListener listener = new TETStatusListener(model, controller, view);

        //Tell stream what tweets to filter for
        FilterQuery fq = new FilterQuery();
        String emotionsFile = "resources/emotions.txt";
        ArrayList<String> emotions = getEmotions(emotionsFile);
        String keywords[] = new String[emotions.size()];
        for (int i = 0; i < emotions.size(); i++) {
            keywords[i] = emotions.get(i);
        }
        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);

        //Set up and start timer to fire every second
        Timer timer = new Timer(model.timePeriod * 1000, controller);
        timer.setInitialDelay(5000);
        timer.start();
    }
}
