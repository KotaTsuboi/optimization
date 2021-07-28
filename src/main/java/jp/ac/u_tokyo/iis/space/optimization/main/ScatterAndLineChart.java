package jp.ac.u_tokyo.iis.space.optimization.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

/**
 * 折線グラフのクラスです。
 *
 * @author Kota
 */
public class ScatterAndLineChart implements ChartMouseListener {

    private JFreeChart chart;
    private final String title;
    private final String xAxisLabel;
    private final String yAxisLabel;
    private double[] rangeX;
    private double[] rangeY;
    private double xTickUnit;
    private double yTickUnit;
    private final XYSeriesCollection dataset;

    private ChartPanel chartPanel;
    private CrosshairOverlay crosshairOverlay;

    {
        dataset = new XYSeriesCollection();
        xTickUnit = Double.NaN;
        yTickUnit = Double.NaN;
    }

    public ScatterAndLineChart(String title, String xAxisLabel, String yAxisLabel) {
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
    }

    public static void main(String[] args) {
        String title = "sin & cos chart";
        String xAxisLabel = "x label";
        String yAxisLabel = "y label";
        ScatterAndLineChart chart = new ScatterAndLineChart(title, xAxisLabel, yAxisLabel);
        double[] x = new double[100];
        double[] sinX = new double[100];
        double[] cosX = new double[100];
        double xi = 0;
        double dx = 0.1;
        for (int i = 0; i < 100; i++) {
            x[i] = xi;
            sinX[i] = Math.sin(xi);
            cosX[i] = Math.cos(xi);
            xi += dx;
        }
        chart.addData("y = sin(x)", x, sinX);
        chart.addData("y = cos(x)", x, cosX);
        chart.createChart();
        chart.show();
    }

    public void addData(String key, double[] x, double[] y) {
        XYSeries series = new XYSeries(key);
        for (int i = 0; i < x.length; i++) {
            series.add(x[i], y[i]);
        }
        dataset.addSeries(series);
    }

    public void setXAxisRange(double min, double max) {
        rangeX = new double[]{min, max};
    }

    public void setYAxisRange(double min, double max) {
        rangeY = new double[]{min, max};
    }

    public void setXTickUnit(double tx) {
        xTickUnit = tx;
    }

    public void setYTickUnit(double ty) {
        yTickUnit = ty;
    }

    public void createChart() {
        chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, false, false);
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);

        plot.setBackgroundPaint(Color.WHITE);

        String fontName = "dialog";
        int style = Font.PLAIN;
        ValueAxis xAxis = plot.getDomainAxis();
        ValueAxis yAxis = plot.getRangeAxis();
        xAxis.setLabelFont(new Font(fontName, style, 24));
        xAxis.setTickLabelFont(new Font(fontName, style, 18));
        yAxis.setLabelFont(new Font(fontName, style, 24));
        yAxis.setTickLabelFont(new Font(fontName, style, 18));
        TextTitle title = chart.getTitle();
        title.setFont(new Font(fontName, style, 36));
        LegendTitle legend = chart.getLegend();
        legend.setItemFont(new Font(fontName, style, 24));

        if (rangeX != null) {
            xAxis.setRange(rangeX[0], rangeX[1]);
        }

        if (rangeY != null) {
            yAxis.setRange(rangeY[0], rangeY[1]);
        }

        if (!Double.isNaN(xTickUnit)) {
            TickUnits tx = new TickUnits();
            NumberTickUnit ux = new NumberTickUnit(xTickUnit);
            tx.add(ux);
            xAxis.setStandardTickUnits(tx);
        }

        if (!Double.isNaN(yTickUnit)) {
            TickUnits ty = new TickUnits();
            NumberTickUnit uy = new NumberTickUnit(yTickUnit);
            ty.add(uy);
            xAxis.setStandardTickUnits(ty);
        }
    }

    public void saveChartAsSVG(String filePath, int width, int height) throws IOException {
        System.err.println("Save chart as SVG.");
        SVGGraphics2D svg = new SVGGraphics2D(width, height);
        Rectangle2D rectangle = new Rectangle(width, height);
        chart.draw(svg, rectangle);
        SVGUtils.writeToSVG(new File(filePath), svg.getSVGElement());
    }

    public void saveChartAsPNG(String filePath, int width, int height) throws IOException {
        System.out.println("Save chart as PNG.");
        File file = new File(filePath);
        ChartUtils.saveChartAsPNG(file, chart, width, height);
        System.out.println("PNG saved: " + filePath);
    }

    public void show() {
        ChartFrame frame = new ChartFrame("", chart);
        frame.setDefaultCloseOperation(ChartFrame.EXIT_ON_CLOSE);
        frame.setBounds(10, 10, 1000, 500);
        frame.setVisible(true);

        chartPanel = frame.getChartPanel();
        chartPanel.addChartMouseListener(this);
        crosshairOverlay = new CrosshairOverlay();
        chartPanel.addOverlay(crosshairOverlay);
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent ev) {
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent ev) {
        // 前回の表示をクリアする
        crosshairOverlay.clearDomainCrosshairs();
        crosshairOverlay.clearRangeCrosshairs();

        JFreeChart jfreeChart = ev.getChart();
        XYPlot plot = (XYPlot) jfreeChart.getPlot();
        ValueAxis xaxis = plot.getDomainAxis();
        Rectangle2D rectangle = chartPanel.getScreenDataArea();
        double dx = xaxis.java2DToValue(ev.getTrigger().getX(), rectangle, RectangleEdge.BOTTOM);
        if (!(xaxis.getRange().contains(dx))) {
            dx = 0.0D;
        }

        // Xの値を設定する
        Crosshair xCrosshair = new Crosshair(0.0D, Color.GRAY, new BasicStroke(0.0F));
        xCrosshair.setLabelVisible(true);
        xCrosshair.setValue(dx);
        crosshairOverlay.addDomainCrosshair(xCrosshair);

        // Yの値を設定する
        Crosshair yCrosshair;
        // シリーズ数を取得する
        int cnt = plot.getDataset().getSeriesCount();
        // それぞれに対して横線を表示する
        for (int i = 0; i < cnt; i++) {
            double dy = DatasetUtils.findYValue(plot.getDataset(), i, dx);
            yCrosshair = new Crosshair(0.0D, Color.GRAY, new BasicStroke(0.0F));
            yCrosshair.setLabelVisible(true);
            yCrosshair.setValue(dy);
            crosshairOverlay.addRangeCrosshair(yCrosshair);
        }
    }
}
