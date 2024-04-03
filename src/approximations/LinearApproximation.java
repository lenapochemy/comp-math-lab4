package approximations;

import java.io.FileWriter;
import java.util.function.DoubleFunction;

public class LinearApproximation extends AbstractApproximation{

    public double r;

    public LinearApproximation(int n, double[] x, double[] y, boolean outputMode, FileWriter file){
        super(n, x, y, "Линейная аппроксимация", "ax+b", outputMode, file, false);
        double x_sr = 0;
        double y_sr = 0;
        for(int i = 0; i < n; i++){
            x_sr += x[i];
            y_sr += y[i];
        }
        x_sr = x_sr / n;
        y_sr = y_sr / n;

        double s1 = 0, s2 = 0, s3 = 0;
        for(int i = 0; i < n; i++){
            s1 += (x[i] - x_sr) * (y[i] - y_sr);
            s2 += Math.pow((x[i] - x_sr), 2);
            s3 += Math.pow((y[i] - y_sr), 2);
        }
        r = s1 / Math.sqrt(s2 * s3);
        writeResult("\nКоэффициент корреляции: " + r);
    }

    @Override
    public DoubleFunction<Double> findFunc() {
        double sx = 0, sx2 = 0, sy = 0, sxy = 0;
        for (int i = 0; i < n; i++) {
            sx += x[i];
            sx2 += x[i] * x[i];
            sy += y[i];
            sxy += x[i] * y[i];
        }

        double det = sx2 * n - sx * sx;
        double det1 = sxy * n - sx * sy;
        double det2 = sx2 * sy - sx * sxy;
//        double det = Det.determinant(new double[][]{{n, sx}, {sx, sx2}});
//        double det1 = Det.determinant(new double[][]{{sy, sx}, {sxy, sx2}});
//        double det2 = Det.determinant(new double[][]{{n, sy}, {sx, sxy}});
        double a = det1 / det;
        double b = det2 / det;
//        System.out.println(a + "x+" + b);
        coef = "a = " + a + " b = " + b;
        return x -> a * x + b;
    }
}
