import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller interface for multiple controllers to be implemented from.
 * 
 * Classes that implement this interface: TSTController1, TSTController2
 * 
 * @author Thomas Clark
 */
public interface TETController extends ActionListener {

    /**
     * Updates this.view to display this.model.
     */
    void updateViewToMatchModel();

    /**
     * Updates the current data plot.
     */
    void updatePlot();

    /**
     * Override of actionPerformed() method from ActionListener interface
     */
    @Override
    public void actionPerformed(ActionEvent e);
}