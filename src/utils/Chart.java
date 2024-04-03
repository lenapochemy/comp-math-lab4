package utils;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
public class Chart {

    public Chart(){
    }
    public void drawTwoGraphics(double[] x, double[] y, double[] line, double[] quadro, double[] cubic,
                                double[] power, double[] exp, double[] log){
        int n = x.length;
        XYSeries series1 = new XYSeries("Исходные данные");
        XYSeries series2 = new XYSeries("Линейная");
        XYSeries series3 = new XYSeries("Квадратная");
        XYSeries series4 = new XYSeries("Кубическая");
        XYSeries series5 = new XYSeries("Степенная");
        XYSeries series6 = new XYSeries("Экспоненциальная");
        XYSeries series7 = new XYSeries("Логарифмическая");

        for(int i = 0; i < n; i++){
            series1.add(x[i], y[i]);
            series2.add(x[i], line[i]);
            series3.add(x[i], quadro[i]);
            series4.add(x[i], cubic[i]);
            if(power != null) {
                series5.add(x[i], power[i]);
            }
            if(exp != null) {
                series6.add(x[i], exp[i]);
            }
            if(log != null) {
                series7.add(x[i], log[i]);
            }
        }

        XYSeriesCollection xyDataset1 = new XYSeriesCollection();
        xyDataset1.addSeries(series1);
        XYSeriesCollection xyDataset2 = new XYSeriesCollection();
        xyDataset2.addSeries(series2);
        xyDataset2.addSeries(series3);
        xyDataset2.addSeries(series4);
        xyDataset2.addSeries(series5);
        xyDataset2.addSeries(series6);
        xyDataset2.addSeries(series7);

        show(xyDataset1, xyDataset2);
    }

    public void show(XYDataset dots, XYDataset line){

        XYItemRenderer rendererLine = new XYLineAndShapeRenderer();
        NumberAxis XAxis = new NumberAxis("x");
        NumberAxis YAxis = new NumberAxis("y");

        XYItemRenderer rendererScatter = new XYShapeRenderer();
        XYPlot plot = new XYPlot(dots, XAxis, YAxis, rendererScatter);
        plot.setRenderer(1, rendererLine);
        plot.setDataset(1, line);

        JFreeChart chart = new JFreeChart(plot);

        JFrame frame = new JFrame("График");
        frame.getContentPane().add(new ChartPanel(chart));

        frame.setSize(800, 800);
        frame.show();
    }

}
