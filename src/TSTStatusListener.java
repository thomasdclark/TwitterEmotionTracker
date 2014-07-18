import java.awt.Color;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 * Overrides StatusListener interface with custom functionality.
 * 
 * @author Thomas Clark
 */
public final class TSTStatusListener implements StatusListener {

    //Model
    private final TSTDataModel model;

    //Controller
    private final TSTController controller;

    //View
    private final TSTView view;

    /**
     * Default constructor.
     */
    public TSTStatusListener(TSTDataModel model, TSTController controller,
            TSTView view) {
        this.model = model;
        this.controller = controller;
        this.view = view;
    }

    /**
     * Function to tell if the input tweet (string) contains a smiley face
     * (happy). If it does not, it is assumed that it contains a frowny face.
     */
    private static boolean isHappy(String input) {
        boolean isHappy = false;
        if (input.contains(new StringBuilder(":)"))
                || input.contains(new StringBuilder("(:"))) {
            isHappy = true;
        }
        return isHappy;
    }

    @Override
    public void onStatus(Status status) {
        if (isHappy(status.getText())) {
            this.model.incrementSmileCount();
            this.model.recordHappyOrSad(true);
        } else {
            this.model.incrementFrownCount();
            this.model.recordHappyOrSad(false);
        }
        if (this.model.tweetCounter() == 0) {
            if (isHappy(status.getText())) {
                this.view.tweetText().setText(
                        "@" + status.getUser().getScreenName() + ":\n"
                                + status.getText());
                this.view.tweetText().setForeground(Color.WHITE);
                this.view.tweetText().setBackground(Color.RED);
            } else {
                this.view.tweetText().setText(
                        "@" + status.getUser().getScreenName() + ":\n"
                                + status.getText());
                this.view.tweetText().setForeground(Color.WHITE);
                this.view.tweetText().setBackground(Color.BLUE);
            }
        }
        this.model.incrementTweetCounter();
        this.controller.updateViewToMatchModel();
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
    }

    @Override
    public void onException(Exception ex) {
    }

    @Override
    public void onStallWarning(StallWarning arg0) {
    }
}
