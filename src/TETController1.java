import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

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
     * Updates the current data plot.
     */
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
        chart.setSize(650, 450);
        chart.setTitle("Tweet Count For Select Emotions For Past " + arraySize
                + " Seconds", Color.WHITE, 14);
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
        //AxisLabels yAxis1 = AxisLabelsFactory.newAxisLabels("Tweet #", 50.0);
        //yAxis1.setAxisStyle(AxisStyle.newAxisStyle(Color.WHITE, 14,
        //        AxisTextAlignment.CENTER));
        AxisLabels xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0,
                arraySize);
        //AxisLabels xAxis1 = AxisLabelsFactory.newAxisLabels("Seconds", 50.0);
        //xAxis1.setAxisStyle(AxisStyle.newAxisStyle(Color.WHITE, 14,
        //        AxisTextAlignment.CENTER));
        xAxis.setAxisStyle(axisStyle);
        chart.addYAxisLabels(yAxis);
        //chart.addYAxisLabels(yAxis1);
        chart.addXAxisLabels(xAxis);
        //chart.addXAxisLabels(xAxis1);
        String urlString = chart.toURLString();
        try {
            this.view.replacePlot(urlString);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * actionPerformed method overridden to fire when the timer in TETMain fires
     * every second
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.model.secondsPast++;

        this.model.counts = new ArrayList<Integer>();
        for (int i = 0; i < this.model.emotions.size(); i++) {
            this.model.counts.add(0);
        }
        this.model.pastSeconds.add(0, this.model.counts);
        if (this.model.pastSeconds.size() > this.model.secondsToRecord) {
            this.model.pastSeconds.remove(this.model.secondsToRecord);
            this.model.updateTotalCounts();
        }

        if (this.model.pastSeconds.size() > 1) {
            this.updatePlot();
        }

        this.model.tweetCount = 0;
    }
}