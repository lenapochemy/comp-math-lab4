package approximations;

import utils.Det;

import java.io.FileWriter;
import java.util.function.DoubleFunction;

public class QuadraticApproximation extends AbstractApproximation{

    public QuadraticApproximation(int n, double[] x, double[] y, boolean outputMode, FileWriter file){
        super(n, x, y, "Квадратичная аппроксимация", "a2*x²+a1*x+a0", outputMode, file, false);
    }

    @Override
    public DoubleFunction<Double> findFunc(){
        double sx = 0, sx2 = 0, sx3 = 0, sx4 = 0, sy = 0, sxy = 0, sx2y = 0;

        for (int i = 0; i < n; i++) {
            sx += x[i];
//            System.out.println(sx3);
            sx2 += x[i] * x[i];
//            System.out.println(x[i] + " " + sx2);
            sx3 += Math.pow(x[i], 3);
            sx4 += Math.pow(x[i], 4);
            sy += y[i];
            sxy += x[i] * y[i];
            sx2y += x[i] * x[i] * y[i];
        }

//        System.out.println(sx + " " + sx2 + " " + sx3 + " " + sx4 + " " + sy + " " + sxy + " " + sxxy);
        double det = Det.determinant(new double[][]{{n, sx, sx2},
                                                    {sx, sx2, sx3},
                                                    {sx2, sx3, sx4}});
        double det1 = Det.determinant(new double[][]{{sy, sx, sx2},
                                                    {sxy, sx2, sx3},
                                                    {sx2y, sx3, sx4}});
        double det2 = Det.determinant(new double[][]{{n, sy, sx2},
                                                    {sx, sxy, sx3},
                                                    {sx2, sx2y, sx4}});
        double det3 = Det.determinant(new double[][]{{n, sx, sy},
                                                    {sx, sx2, sxy},
                                                    {sx2, sx3, sx2y}});
        double a0 = det1 / det;
        double a1 = det2 / det;
        double a2 = det3 / det;
//        System.out.println(a0 + "+" + a1 + "x+" + a2 + "x²");
        coef = "a2 = " + a2 + " a1 = " + a1 + " a0 = " + a0;
        return x -> a0 + a1 * x + a2 * x * x;
    }
}
