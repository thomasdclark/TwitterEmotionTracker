import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * View class that implements TSTView. To be used with TSTController2.
 * 
 * @author Thomas Clark
 */
public final class TETView1 extends JFrame implements TETView {

    /**
     * Controller object.
     */
    private TETController controller;

    /**
     * Plot object.
     */
    private JLabel plot;

    private JPanel panel;

    /**
     * Constants
     */
    private static final int LINES_IN_DISPLAY_TEXT = 2,
            LINE_LENGTHS_IN_DISPLAY_TEXT = 8, LINES_IN_TWEET_TEXT = 5,
            LINE_LENGTHS_IN_TWEET_TEXT = 8;

    /**
     * Text areas.
     */
    private final JTextArea displayText;

    private final JTextArea tweetText;

    /**
     * Default constructor.
     */
    public TETView1() {

        /*
         * Call JFrame superclass with title
         */
        super("Twitter Emotion Tracker");

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
        this.displayText.setBackground(Color.WHITE);

        /*
         * Customize font of tweet
         */
        Font tweetFont = new Font("TimeRoman", Font.BOLD, 12);
        this.tweetText.setFont(tweetFont);
        this.tweetText.setForeground(Color.WHITE);
        this.tweetText.setBackground(Color.WHITE);

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

        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
        this.panel.setPreferredSize(new Dimension(250, 450));

        /*
         * Add scroll panes and button panel to main window
         */
        this.panel.add(displayTextScrollPane);
        this.panel.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panel.add(tweetTextScrollPane);

        this.add(this.panel);

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
    public void registerObserver(TETController controller) {
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
     * Replaces/refreshes plot in view
     */
    @Override
    public void replacePlot(String urlString) throws IOException {
        this.getContentPane().removeAll();
        this.plot = new JLabel(new ImageIcon(ImageIO.read(new URL(urlString))));
        this.add(this.panel, BorderLayout.WEST);
        this.add(this.plot, BorderLayout.EAST);
        this.pack();
        this.revalidate();
        this.repaint();
        this.setVisible(true);
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
