import java.awt.event.ActionListener;

import javax.swing.JTextArea;

/**
 * View interface for multiple views to be implemented from. Implements
 * ActionListener.
 * 
 * Classes that implement this interface: TSTView1, TSTView2
 * 
 * @author Thomas Clark
 */
public interface TSTView extends ActionListener {

    /**
     * Register argument as observer/listener of this
     */
    void registerObserver(TSTController controller);

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

}
