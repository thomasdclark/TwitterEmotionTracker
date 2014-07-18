import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * View class that implements TSTView. To be used with TSTController2.
 * 
 * @author Thomas Clark
 */
public final class TSTView2 extends JFrame implements TSTView {

    /**
     * Controller object.
     */
    private TSTController controller;

    /**
     * Constants
     */
    private static final int LINES_IN_DISPLAY_TEXT = 4,
            LINE_LENGTHS_IN_DISPLAY_TEXT = 8, LINES_IN_TWEET_TEXT = 5,
            LINE_LENGTHS_IN_TWEET_TEXT = 8, ROWS_IN_THIS_GRID = 2,
            COLUMNS_IN_THIS_GRID = 1;

    /**
     * Text areas.
     */
    private final JTextArea displayText;

    private final JTextArea tweetText;

    /**
     * Default constructor.
     */
    public TSTView2() {

        /*
         * Call JFrame superclass with title
         */
        super("Twitter Smile Tracker");

        /*
         * Create widgets
         */
        this.displayText = new JTextArea("", LINES_IN_DISPLAY_TEXT,
                LINE_LENGTHS_IN_DISPLAY_TEXT);

        this.tweetText = new JTextArea("", LINES_IN_TWEET_TEXT,
                LINE_LENGTHS_IN_TWEET_TEXT);

        /*
         * Customize font of text
         */
        Font textFont = new Font("TimeRoman", Font.BOLD, 30);
        this.displayText.setFont(textFont);
        this.displayText.setForeground(Color.WHITE);
        this.displayText.setBackground(Color.RED);

        /*
         * Customize font of tweet
         */
        Font tweetFont = new Font("TimeRoman", Font.BOLD, 12);
        this.tweetText.setFont(tweetFont);
        this.tweetText.setForeground(Color.WHITE);
        this.tweetText.setBackground(Color.RED);

        /*
         * Text areas should wrap lines, and outputText should be read-only
         */
        this.displayText.setEditable(false);
        this.displayText.setLineWrap(true);
        this.displayText.setWrapStyleWord(true);
        this.tweetText.setEditable(false);
        this.tweetText.setLineWrap(true);
        this.tweetText.setWrapStyleWord(true);

        /*
         * Create scroll panes for the text areas
         */
        JScrollPane displayTextScrollPane = new JScrollPane(this.displayText);
        JScrollPane tweetTextScrollPane = new JScrollPane(this.tweetText);

        /*
         * Organize main window by setting layout
         */
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        /*
         * Add scroll panes and button panel to main window
         */
        this.add(displayTextScrollPane);
        this.add(tweetTextScrollPane);

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

    /**
     * This view has no buttons so no need to implement actionPerformed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

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
        return this.tweetText;
    }
}
