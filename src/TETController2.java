import java.awt.Color;

/**
 * Controller class that implements TSTController. To be used with TSTView2.
 * 
 * @author Thomas Clark
 */
public final class TETController2 implements TETController {

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
    public TETController2(TETDataModel model, TETView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Updates this.view to display this.model.
     */
    @Override
    public void updateViewToMatchModel() {
        boolean happyOrSad = this.model.happyOrSad;
        String happyOrSadString;
        if (happyOrSad) {
            happyOrSadString = "HAPPY :)";
            this.view.displayText().setForeground(Color.WHITE);
            this.view.displayText().setBackground(Color.RED);
        } else {
            happyOrSadString = "SAD :(";
            this.view.displayText().setForeground(Color.WHITE);
            this.view.displayText().setBackground(Color.BLUE);
        }

        String output = "\n   Twitter is:\n     " + happyOrSadString;

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