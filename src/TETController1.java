import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.math.plot.Plot2DPanel;

import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

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
     * Plot object.
     */
    private final TETPlot plot;

    /**
     * Constructor; connects this to the model and view.
     */
    public TETController1(TETDataModel model, TETView view, TETPlot plot) {
        this.model = model;
        this.view = view;
        this.plot = plot;
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
        this.view.displayText().setBackground(
                this.model.colors.get(leadingEmotion));
        this.view.updateDisplay(output);
    }

    /**
     * Processes event to reset model.
     */
    @Override
    public void processResetEvent() {

    }

    @Override
    public void updatePlot() {
        Plot2DPanel plot = new Plot2DPanel();
        ArrayList<ArrayList<Integer>> past60Seconds = this.model.past60Seconds;
        double[] x = new double[60];
        for (int i = 0; i < 60; i++) {
            x[i] = i;
        }
        for (int i = 0; i < past60Seconds.get(0).size(); i++) {
            double[] y = new double[60];
            for (int j = 0; j < past60Seconds.size(); j++) {
                y[j] = past60Seconds.get(j).get(i);
            }
            plot.addLinePlot(this.model.emotions.get(i), x, y);
        }
        this.plot.setContentPane(plot);
        this.plot.revalidate();
    }

    /**
     * actionPerformed method overridden to fire when the timer in TETMain fires
     * every second
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.model.secondsPast++;
        this.updatePlot();

        SimpleWriter out = new SimpleWriter1L();
        out.println(this.model.totalCountsPast60Seconds + "   "
                + this.model.counts);
        out.close();

        this.model.counts = new ArrayList<Integer>();
        for (int i = 0; i < this.model.emotions.size(); i++) {
            this.model.counts.add(0);
        }
        this.model.past60Seconds.add(0, this.model.counts);
        if (this.model.past60Seconds.size() > 60) {
            this.model.past60Seconds.remove(60);
            this.model.updateTotalCounts();
        }
        this.model.tweetCount = 0;
    }
}