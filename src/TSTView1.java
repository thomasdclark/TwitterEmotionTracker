import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * View class that implements TSTView. To be used with TSTController1.
 * 
 * @author Thomas Clark
 */
public final class TSTView1 extends JFrame implements TSTView {

    /**
     * Controller object.
     */
    private TSTController controller;

    /**
     * Constants
     */
    private static final int LINES_IN_TEXT_AREAS = 5,
            LINE_LENGTHS_IN_TEXT_AREAS = 30, ROWS_IN_BUTTON_PANEL_GRID = 1,
            COLUMNS_IN_BUTTON_PANEL_GRID = 1, ROWS_IN_THIS_GRID = 2,
            COLUMNS_IN_THIS_GRID = 1;

    /**
     * Text areas.
     */
    private final JTextArea displayText;

    /**
     * Buttons.
     */
    private final JButton resetButton;

    /**
     * Default constructor.
     */
    public TSTView1() {

        /*
         * Call JFrame superclass with title
         */
        super("Twitter Smile Tracker");

        /*
         * Create widgets
         */
        this.displayText = new JTextArea("", LINES_IN_TEXT_AREAS,
                LINE_LENGTHS_IN_TEXT_AREAS);
        this.resetButton = new JButton("Reset");

        /*
         * Text areas should wrap lines, and outputText should be read-only
         */
        this.displayText.setEditable(false);
        this.displayText.setLineWrap(true);
        this.displayText.setWrapStyleWord(true);

        /*
         * Create scroll panes for the text areas
         */
        JScrollPane displayTextScrollPane = new JScrollPane(this.displayText);

        /*
         * Create button panel organized using grid layout
         */
        JPanel buttonPanel = new JPanel(new GridLayout(
                ROWS_IN_BUTTON_PANEL_GRID, COLUMNS_IN_BUTTON_PANEL_GRID));

        /*
         * Add buttons to the button panel
         */
        buttonPanel.add(this.resetButton);

        /*
         * Organize main window using grid layout
         */
        this.setLayout(new GridLayout(ROWS_IN_THIS_GRID, COLUMNS_IN_THIS_GRID));

        /*
         * Add scroll panes and button panel to main window
         */
        this.add(displayTextScrollPane);
        this.add(buttonPanel);

        /*
         * Register this object as the observer for all GUI events
         */
        this.resetButton.addActionListener(this);

        /*
         * Start the main application window
         */
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Register argument as observer/listener of this
     */
    @Override
    public void registerObserver(TSTController controller) {
        this.controller = controller;
    }

    /**
     * Updates output display based on String provided as argument.
     */
    @Override
    public void updateDisplay(String output) {
        this.displayText.setText(output);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor to indicate computation on-going
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        /*
         * Determine which event has occurred
         */
        Object source = event.getSource();
        if (source == this.resetButton) {
            this.controller.processResetEvent();
        }

        /*
         * Set the cursor back to normal
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Returns the displayText JTextArea
     */
    @Override
    public JTextArea displayText() {
        return this.displayText;
    }

    /**
     * Returns the tweetText JTextArea
     */
    @Override
    public JTextArea tweetText() {
        return null;
    }
}
