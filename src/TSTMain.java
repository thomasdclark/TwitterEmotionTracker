import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Simple Twitter stream application that recieves a stream of tweets containing
 * smiley faces and frowny faces, counts how many of each are occurring during a
 * period of tweets, and determines if Twitter is happy or sad during that
 * period.
 * 
 * @author Thomas Clark
 */
public final class TSTMain {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TSTMain() {
    }

    /**
     * Take the file containing the OAuth information and turns it into a string
     */
    public static String readOAuth(String filename) {
        String content = null;
        File file = new File(filename); //for ex foo.txt
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
     * Main function
     */
    public static void main(String[] args) throws TwitterException {

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
        TSTDataModel model = new TSTDataModel();

        /*
         * Decide which view to use. Both have different displays but show the
         * same information
         */
        //TSTView view = new TETView1();
        TSTView view = new TSTView2();

        /*
         * Decide which controller to use. TSTController1 should be used with
         * TSTView1 and TSTController2 should be used with TSTView2
         */
        //TSTController controller = new TSTController1(model, view);
        TSTController controller = new TSTController2(model, view);

        //Register observer in view
        view.registerObserver(controller);

        //Initialize status listener
        StatusListener listener = new TSTStatusListener(model, controller, view);

        //Tell stream what tweets to filter for
        FilterQuery fq = new FilterQuery();
        String keywords[] = { ":)", ":(", "(:", "):" };
        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }
}
