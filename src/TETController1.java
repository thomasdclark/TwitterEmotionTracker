import java.text.DecimalFormat;
import java.util.Date;

/**
 * Controller class that implements TSTController. To be used with TSTView1.
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
        int smileCount = this.model.smileCount();
        int frownCount = this.model.frownCount();
        boolean happyOrSad = this.model.happyOrSad;
        String happyOrSadString;
        if (happyOrSad) {
            happyOrSadString = "HAPPY :)";
        } else {
            happyOrSadString = "SAD :(";
        }
        Date startDate = this.model.date();

        DecimalFormat df = new DecimalFormat("#.###");
        double smileToFrownRatio = (double) smileCount / frownCount;

        String output = "Twitter is:  " + happyOrSadString
                + "\nRecording Since:  " + startDate.toString()
                + "\nSmile Count:  " + smileCount + "\nFrown Count:  "
                + frownCount + "\nSmile to Frown Ratio:  "
                + df.format(smileToFrownRatio);

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
        /*
         * Update model in response to this event
         */
        this.model.setSmileCount(0);
        this.model.setFrownCount(0);
        this.model.resetDate();
        this.view.updateDisplay(this.model.date().toString()
                + "\nSmile Count:  0\nFrown Count:  0");
    }
}