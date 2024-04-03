package utils;

import approximations.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Solver {

    private final int n;
    private final double[] x, y;
//    private String minValue;
    private final boolean outputMode, negativeData;
    private final FileWriter file;

    public Solver(double[] x, double[] y, boolean outputMode, FileWriter file, boolean negativeData){
        this.x = x;
        this.y = y;
        this.n = x.length;
        this.outputMode = outputMode;
        this.file = file;
        this.negativeData = negativeData;
    }

    public void solve(){

        LinearApproximation line = new LinearApproximation(n, x, y, outputMode, file);
        QuadraticApproximation quadr = new QuadraticApproximation(n, x ,y, outputMode, file);
        CubicApproximation cubic = new CubicApproximation(n, x, y, outputMode, file);
        PowerApproximation power = new PowerApproximation(n, x, y, outputMode, file, negativeData);
        ExponentialApproximation exp = new ExponentialApproximation(n, x, y, outputMode, file, negativeData);
        LogarithmicApproximation log = new LogarithmicApproximation(n, x, y, outputMode, file, negativeData);

        Chart chart = new Chart();
        chart.drawTwoGraphics(x, y, line.phi, quadr.phi, cubic.phi, power.phi, exp.phi, log.phi);

        double[] stDevs = new double[6];
        String[] names = new String[6];
        stDevs[0] =  line.standardDeviation;
        stDevs[1] =  quadr.standardDeviation;
        stDevs[2] = cubic.standardDeviation;

        names[0] = line.name;
        names[1] = quadr.name;
        names[2] = cubic.name;

        if(!negativeData) {
            stDevs[3] = power.standardDeviation;
            stDevs[4] = exp.standardDeviation;
            stDevs[5] = log.standardDeviation;

            names[3] = power.name;
            names[4] = exp.name;
            names[5] = log.name;
        }

        double minStDev =Double.MAX_VALUE;
        for(int i = 0; i < 6; i++){
            if(stDevs[i] < minStDev) minStDev = stDevs[i];
        }

        String minName = "";
        for(int i = 0; i < 6; i++){
            if(stDevs[i] == minStDev) {
                if(minName.isEmpty()) {
                    minName = names[i];
                } else {
                    minName = minName + ", " + names[i];
                }
            }
        }


        line.writeResult("\n-----------------------------------------------");
        line.writeResult("\nНаилучшее приближение - " + minName);

        if(!outputMode) {
            try {
                file.close();
            } catch (IOException e) {
                System.out.println("Проблемы с файлом");
            }
        }
    }

}
