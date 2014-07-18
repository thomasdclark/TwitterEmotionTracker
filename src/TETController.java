/**
 * Controller interface for multiple controllers to be implemented from.
 * 
 * Classes that implement this interface: TSTController1, TSTController2
 * 
 * @author Thomas Clark
 */
public interface TETController {

    /**
     * Updates this.view to display this.model.
     */
    void updateViewToMatchModel();

    /**
     * Processes event to reset model.
     */
    void processResetEvent();
}