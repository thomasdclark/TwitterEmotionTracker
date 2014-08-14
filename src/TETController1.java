import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Shape;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Controller class that implements TSTController. To be used with TSTView2.
 * 
 * @author Thomas Clark
 */
public final class TETController1 implements TETController {

    /**
     * Model object.
     */
    private final TETDataModel model;

    /**
     * View object.
     */
    private final TETView view;

    /**
     * Constructor; connects this to the model and view.
     */
    public TETController1(TETDataModel model, TETView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Updates this.view to display this.model.
     */
    @Override
    public void updateViewToMatchModel() {
        int leadingEmotion = this.model.leadingEmotion();
        String emotionString = this.model.emotions.get(leadingEmotion);

        String output = "\n\n    Twitter is:\n      " + emotionString;

        /*
         * Update the display
         */
        this.view.displayText().setBackground(
                this.model.colors.get(leadingEmotion));
        this.view.updateDisplay(output);
    }

    /**
     * Processes event to reset model.
     */
    @Override
    public void processResetEvent() {

    }

    @Override
    public void updatePlot() {
        ArrayList<ArrayList<Integer>> pastSeconds = this.model.pastSeconds;
        ArrayList<Line> lines = new ArrayList<Line>();
        int arraySize = pastSeconds.size();
        int max = 0;
        for (int i = 0; i < pastSeconds.get(0).size(); i++) {
            for (int j = 0; j < pastSeconds.size(); j++) {
                if (pastSeconds.get(j).get(i) > max) {
                    max = pastSeconds.get(j).get(i);
                }
            }
        }
        for (int i = 0; i < pastSeconds.get(0).size(); i++) {
            double[] y = new double[arraySize];
            for (int j = 0; j < pastSeconds.size(); j++) {
                y[j] = pastSeconds.get(j).get(i);
            }
            String colorString = this.model.colorStrings.get(i);
            Color color;
            switch (colorString) {
                case "black":
                    color = Color.BLACK;
                    break;
                case "blue":
                    color = Color.BLUE;
                    break;
                case "cyan":
                    color = Color.CYAN;
                    break;
                case "darkGray":
                    color = Color.DARKGRAY;
                    break;
                case "gray":
                    color = Color.GRAY;
                    break;
                case "green":
                    color = Color.GREEN;
                    break;
                case "lightGray":
                    color = Color.GRAY;
                    break;
                case "magenta":
                    color = Color.MAGENTA;
                    break;
                case "orange":
                    color = Color.ORANGE;
                    break;
                case "pink":
                    color = Color.PINK;
                    break;
                case "red":
                    color = Color.RED;
                    break;
                case "white":
                    color = Color.WHITE;
                    break;
                case "yellow":
                    color = Color.YELLOW;
                    break;
                default:
                    color = Color.WHITE;
                    break;
            }
            Line line = Plots.newLine(DataUtil.scaleWithinRange(0, max, y),
                    color, this.model.emotions.get(i));
            line.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
            line.addShapeMarkers(Shape.DIAMOND, color, 12);
            line.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);
            lines.add(line);
        }
        LineChart chart = GCharts.newLineChart(lines);
        chart.setSize(600, 450);
        chart.setTitle("Twitter Emotion Tracking for past " + arraySize
                + " seconds", Color.WHITE, 14);
        chart.setGrid(100, 500 / (double) max, 1, 0);
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("1F1D1D")));
        LinearGradientFill fill = Fills.newLinearGradientFill(0,
                Color.newColor("363433"), 100);
        fill.addColorAndOffset(Color.newColor("2E2B2A"), 0);
        chart.setAreaFill(fill);
        AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.WHITE, 12,
                AxisTextAlignment.CENTER);
        AxisLabels yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, max);
        yAxis.setAxisStyle(axisStyle);
        AxisLabels xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0,
                arraySize);
        xAxis.setAxisStyle(axisStyle);
        chart.addYAxisLabels(yAxis);
        chart.addXAxisLabels(xAxis);
        String urlString = chart.toURLString();
        try {
            this.view.replacePlot(urlString);
            System.out.println(urlString);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public JLabel initialPlot() {
        double[] y = { 0 };
        Line line = Plots.newLine(DataUtil.scaleWithinRange(0, 0, y),
                Color.WHITE, "emotion");
        LineChart chart = GCharts.newLineChart(line);
        chart.setSize(600, 450);
        chart.setTitle("Twitter Emotion Tracking", Color.WHITE, 14);
        chart.setGrid(100, 100, 1, 0);
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("1F1D1D")));
        LinearGradientFill fill = Fills.newLinearGradientFill(0,
                Color.newColor("363433"), 100);
        fill.addColorAndOffset(Color.newColor("2E2B2A"), 0);
        chart.setAreaFill(fill);
        String urlString = chart.toURLString();
        JLabel plot = new JLabel();
        try {
            plot = new JLabel(new ImageIcon(ImageIO.read(new URL(urlString))));
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return plot;
    }

    /**
     * actionPerformed method overridden to fire when the timer in TETMain fires
     * every second
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.model.secondsPast++;
        if (this.model.pastSeconds.size() > 0) {
            this.updatePlot();
        }

        SimpleWriter out = new SimpleWriter1L();
        out.println(this.model.totalCountsPastSeconds + "   "
                + this.model.counts);
        out.close();

        this.model.counts = new ArrayList<Integer>();
        for (int i = 0; i < this.model.emotions.size(); i++) {
            this.model.counts.add(0);
        }
        this.model.pastSeconds.add(0, this.model.counts);
        if (this.model.pastSeconds.size() > this.model.secondsToRecord) {
            this.model.pastSeconds.remove(this.model.secondsToRecord);
            this.model.updateTotalCounts();
        }
        this.model.tweetCount = 0;
    }
}