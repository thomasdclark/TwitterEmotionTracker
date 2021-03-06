import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextArea;

/**
 * View interface for multiple views to be implemented from. Implements
 * ActionListener.
 * 
 * Classes that implement this interface: TSTView1, TSTView2
 * 
 * @author Thomas Clark
 */
public interface TETView extends ActionListener {

    /**
     * Register argument as observer/listener of this
     */
    void registerObserver(TETController controller);

    /**
     * Updates output display based on String provided as argument.
     */
    void updateDisplay(String output);

    /**
     * Returns the displayText JTextArea
     */
    JTextArea displayText();

    /**
     * Returns the tweetText JTextArea
     */
    JTextArea tweetText();

    /**
     * Replaces/refreshes plot in view
     */
    public void replacePlot(String urlString) throws IOException;

}
