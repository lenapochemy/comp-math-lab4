package approximations;

import utils.Det;

import java.io.FileWriter;
import java.util.function.DoubleFunction;

public class CubicApproximation extends AbstractApproximation{

    public CubicApproximation(int n, double[] x, double[] y, boolean outputMode, FileWriter file){
        super(n, x, y, "Кубическая аппроксимация", "a3*x³+a2*x²+a1*x+a0", outputMode, file, false);
    }

    @Override
    public DoubleFunction<Double> findFunc(){
        double sx = 0, sx2 = 0, sx3 = 0, sx4 = 0, sx5 = 0, sx6 = 0,  sy = 0, sxy = 0, sx2y = 0, sx3y = 0;

        for (int i = 0; i < n; i++) {
            sx += x[i];
            sx2 += x[i] * x[i];
            sx3 += Math.pow(x[i], 3);
            sx4 += Math.pow(x[i], 4);
            sx5 += Math.pow(x[i], 5);
            sx6 += Math.pow(x[i], 6);
            sy += y[i];
            sxy += x[i] * y[i];
            sx2y += x[i] * x[i] * y[i];
            sx3y += Math.pow(x[i], 3) * y[i];
        }

//        System.out.println(sx + " " + sx2 + " " + sx3 + " " + sx4 + " " + sy + " " + sxy + " " + sxxy);
        double det = Det.determinant(new double[][]{
                {n, sx, sx2, sx3},
                {sx, sx2, sx3, sx4},
                {sx2, sx3, sx4, sx5},
                {sx3, sx4, sx5, sx6}});
        double det1 = Det.determinant(new double[][]{
                {sy, sx, sx2, sx3},
                {sxy, sx2, sx3, sx4},
                {sx2y, sx3, sx4, sx5},
                {sx3y, sx4, sx5, sx6}});
        double det2 = Det.determinant(new double[][]{
                {n, sy, sx2, sx3},
                {sx, sxy, sx3, sx4},
                {sx2, sx2y, sx4, sx5},
                {sx3, sx3y, sx5, sx6}});
        double det3 = Det.determinant(new double[][]{
                {n, sx, sy, sx3},
                {sx, sx2, sxy, sx4},
                {sx2, sx3, sx2y, sx5},
                {sx3, sx4, sx3y, sx6}});
        double det4 = Det.determinant(new double[][]{
                {n, sx, sx2, sy},
                {sx, sx2, sx3, sxy},
                {sx2, sx3, sx4, sx2y},
                {sx3, sx4, sx5, sx3y}});
        double a0 = det1 / det;
        double a1 = det2 / det;
        double a2 = det3 / det;
        double a3 = det4 / det;
//        writeResult(a0 + "+" + a1 + "x+" + a2 + "x²+" + a3 + "x³");
        coef = "a3 = " + a3 + " a2 = " + a2 + " a1 = " + a1 + " a0 = " + a0;
//        coef = "a0 = " + a0 + " a1 = " + a1 + " a2 = " + a2 + " a3 = " + a3;
        return x -> a0 + a1 * x + a2 * x * x + a3 * x * x * x;
    }
}
