import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

/**
 * View class that implements TSTView. To be used with TSTController2.
 * 
 * @author Thomas Clark
 */
public final class TETPlot extends JFrame {

    /**
     * Controller object.
     */
    private TETController controller;

    /**
     * Line plot
     */
    public Plot2DPanel plot;

    /**
     * Default constructor.
     */
    public TETPlot() {

        /*
         * Call JFrame superclass with title
         */
        super("Twitter Emotion Tracker");

        this.plot = new Plot2DPanel();

        /*
         * Start the main application window
         */
        this.plot.addLegend("SOUTH");
        this.setSize(1000, 600);
        this.setContentPane(this.plot);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Register argument as observer/listener of this
     */
    public void registerObserver(TETController controller) {
        this.controller = controller;
    }
}
