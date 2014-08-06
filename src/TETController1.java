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
        ArrayList<ArrayList<Integer>> pastSeconds = this.model.pastSeconds;
        int arraySize = pastSeconds.size();
        plot.addLegend("WEST");
        double[] x = new double[arraySize];
        for (int i = 0; i < arraySize; i++) {
            x[i] = i;
        }
        for (int i = 0; i < pastSeconds.get(0).size(); i++) {
            double[] y = new double[arraySize];
            for (int j = 0; j < pastSeconds.size(); j++) {
                y[j] = pastSeconds.get(j).get(i);
            }
            plot.addLinePlot(this.model.emotions.get(i), x, y);
        }
        this.plot.setSize(1000, 600);
        this.plot.setContentPane(plot);
        this.plot.revalidate();
        this.plot.repaint();
    }

    /**
     * actionPerformed method overridden to fire when the timer in TETMain fires
     * every second
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.model.secondsPast++;
        if (this.model.pastSeconds.size() > 0) {
            this.updatePlot();
        }

        SimpleWriter out = new SimpleWriter1L();
        out.println(this.model.totalCountsPastSeconds + "   "
                + this.model.counts);
        out.close();

        this.model.counts = new ArrayList<Integer>();
        for (int i = 0; i < this.model.emotions.size(); i++) {
            this.model.counts.add(0);
        }
        this.model.pastSeconds.add(0, this.model.counts);
        if (this.model.pastSeconds.size() > this.model.secondsToRecord) {
            this.model.pastSeconds.remove(this.model.secondsToRecord);
            this.model.updateTotalCounts();
        }
        this.model.tweetCount = 0;
    }
}