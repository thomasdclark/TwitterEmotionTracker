import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
     * Line plot within a JLabel
     */
    public JLabel plot;

    /**
     * Default constructor.
     */
    public TETPlot() {

        /*
         * Call JFrame superclass with title
         */
        super("Twitter Emotion Tracker");

        this.plot = null;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Register argument as observer/listener of this
     */
    public void registerObserver(TETController controller) {
        this.controller = controller;
    }

    /**
     * Register argument as observer/listener of this
     */
    public void replacePlot(String urlString) throws IOException {
        this.getContentPane().removeAll();
        this.plot = new JLabel(new ImageIcon(ImageIO.read(new URL(urlString))));
        this.getContentPane().add(this.plot, BorderLayout.CENTER);
        this.pack();
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

}
