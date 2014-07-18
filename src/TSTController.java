/**
 * Controller interface for multiple controllers to be implemented from.
 * 
 * Classes that implement this interface: TSTController1, TSTController2
 * 
 * @author Thomas Clark
 */
public interface TSTController {

    /**
     * Updates this.view to display this.model.
     */
    void updateViewToMatchModel();

    /**
     * Processes event to reset model.
     */
    void processResetEvent();
}