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
     * Function to tell which emotion was tweeted and increment that emotion in
     * the data model. Also returns the index of the emotion in the array that
     * the emotions are held in.
     */
    public int incrementEmotion(String input) {
        int emotionIndex = 0;
        for (int i = 0; i < this.model.emotions.size(); i++) {
            if (input.contains(this.model.emotions.get(i))) {
                this.model.incrementCount(i);
                return i;
            }
        }
        return emotionIndex;
    }

    @Override
    public void onStatus(Status status) {
        int emotionIndex = this.incrementEmotion(status.getText());
        if (this.model.secondsPast == 1) //Tweet display will only change no more than once per second
        {
            this.model.secondsPast = 0;
            this.view.tweetText().setText(
                    "@" + status.getUser().getScreenName() + ":\n"
                            + status.getText());
            this.view.tweetText().setBackground(
                    this.model.colors.get(emotionIndex));
        }
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
