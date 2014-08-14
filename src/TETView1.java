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
     * Panel objects to hold content.
     */
    private JPanel displayPanel;

    private JPanel graphPanel;

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

        /*
         * Set up displayPanel to hold both displays
         */
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setPreferredSize(new Dimension(250, 460));
        this.displayPanel = new JPanel();
        this.displayPanel.setLayout(new BoxLayout(this.displayPanel,
                BoxLayout.LINE_AXIS));
        this.displayPanel.setPreferredSize(new Dimension(260, 460));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(displayTextScrollPane);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(tweetTextScrollPane);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        this.displayPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        this.displayPanel.add(panel);
        this.displayPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        /*
         * Add displayPanel to frame
         */
        this.add(this.displayPanel);

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
        /*
         * Remove all content from frame and get new plot as a JLabel
         */
        this.getContentPane().removeAll();
        JLabel plot = new JLabel(
                new ImageIcon(ImageIO.read(new URL(urlString))));

        /*
         * Recreate graphPanel from new plot
         */
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel,
                BoxLayout.PAGE_AXIS));
        verticalPanel.setPreferredSize(new Dimension(650, 460));
        verticalPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        verticalPanel.add(plot);
        verticalPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel,
                BoxLayout.LINE_AXIS));
        horizontalPanel.setPreferredSize(new Dimension(655, 460));
        horizontalPanel.add(verticalPanel);
        horizontalPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        this.graphPanel = horizontalPanel;

        /*
         * Add displayPanel and graphPanel to frame and repaint frame
         */
        this.add(this.displayPanel, BorderLayout.WEST);
        this.add(this.graphPanel, BorderLayout.EAST);
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
