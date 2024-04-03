package approximations;

import utils.Det;

import java.io.FileWriter;
import java.util.function.DoubleFunction;

public class LogarithmicApproximation extends AbstractApproximation{
    public LogarithmicApproximation(int n, double[] x, double[] y, boolean outputMode, FileWriter file, boolean negativeData){
        super(n, x, y, "Логарифмическая аппроксимация", "a*ln(x)+b", outputMode, file, negativeData);
    }

    @Override
    public DoubleFunction<Double> findFunc(){
        if(!negativeData) {
            double sx = 0, sx2 = 0, sy = 0, sxy = 0;
            for (int i = 0; i < n; i++) {
                double xi = Math.log(x[i]);
                double yi = y[i];
                sx += xi;
                sx2 += xi * xi;
                sy += yi;
                sxy += xi * yi;
            }

            double det = Det.determinant(new double[][]{{n, sx}, {sx, sx2}});
            double det1 = Det.determinant(new double[][]{{sy, sx}, {sxy, sx2}});
            double det2 = Det.determinant(new double[][]{{n, sy}, {sx, sxy}});
            double b = det1 / det;
            double a = det2 / det;

//        System.out.println(a + "*ln(x)+" + b);
            coef = "a = " + a + " b = " + b;
            return x -> a * Math.log(x) + b;
        } else return null;
    }
}
