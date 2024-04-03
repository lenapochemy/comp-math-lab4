package approximations;

import utils.Det;

import java.io.FileWriter;
import java.util.function.DoubleFunction;

public class ExponentialApproximation extends AbstractApproximation{
    public ExponentialApproximation(int n, double[] x, double[] y, boolean outputMode, FileWriter file, boolean negativeData){
        super(n, x, y,"Экспоненциальная аппроксимация", "a*e^(bx)", outputMode, file, negativeData);
    }

    @Override
    public DoubleFunction<Double> findFunc(){
        if(!negativeData) {
            double sx = 0, sx2 = 0, sy = 0, sxy = 0;
            for (int i = 0; i < n; i++) {
                double xi = x[i];
                double yi = Math.log(y[i]);
                sx += xi;
                sx2 += xi * xi;
                sy += yi;
                sxy += xi * yi;
            }

            double det = Det.determinant(new double[][]{{n, sx}, {sx, sx2}});
            double det1 = Det.determinant(new double[][]{{sy, sx}, {sxy, sx2}});
            double det2 = Det.determinant(new double[][]{{n, sy}, {sx, sxy}});
            double a = Math.exp(det1 / det);
            double b = det2 / det;

//        System.out.println(a + "*e^(" + b +"x)");
            coef = "a = " + a + " b = " + b;
            return x -> a * Math.exp(b * x);
        } return null;
    }
}
