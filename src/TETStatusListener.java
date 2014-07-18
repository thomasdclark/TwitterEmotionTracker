import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 * Overrides StatusListener interface with custom functionality.
 * 
 * @author Thomas Clark
 */
public final class TETStatusListener implements StatusListener {

    //Model
    TETDataModel model;

    //Controller
    TETController controller;

    //View
    TETView view;

    /**
     * Default constructor.
     */
    public TETStatusListener(TETDataModel model, TETController controller,
            TETView view) {
        this.model = model;
        this.controller = controller;
        this.view = view;
    }

    /**
     * Function to tell if the input tweet (string) contains a smiley face
     * (happy). If it does not, it is assumed that it contains a frowny face.
     */
    public void incrementEmotion(String input) {
        for (int i = 0; i < this.model.emotions.size(); i++) {
            if (input.contains(this.model.emotions.get(i))) {
                this.model.incrementCount(i);
                return;
            }
        }
    }

    @Override
    public void onStatus(Status status) {
        this.incrementEmotion(status.getText());
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
