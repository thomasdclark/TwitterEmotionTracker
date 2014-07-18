
/**
 * Controller class that implements TSTController. To be used with TSTView2.
 * 
 * @author Thomas Clark
 */
public final class TETController1 implements TETController {

    /**
     * Model object.
     */
    private final TETDataModel model;

    /**
     * View object.
     */
    private final TETView view;

    /**
     * Constructor; connects this to the model and view.
     */
    public TETController1(TETDataModel model, TETView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Updates this.view to display this.model.
     */
    @Override
    public void updateViewToMatchModel() {
        int leadingEmotion = this.model.leadingEmotion();
        String emotionString = this.model.emotions.get(leadingEmotion);

        String output = "\n   Twitter is:\n     " + emotionString;

        /*
         * Update the display
         */
        this.view.updateDisplay(output);
    }

    /**
     * Processes event to reset model.
     */
    @Override
    public void processResetEvent() {

    }
}