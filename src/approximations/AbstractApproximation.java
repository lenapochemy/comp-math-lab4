package approximations;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.DoubleFunction;

public abstract class AbstractApproximation {
    public final double[] x, y;
    public final int n;
    public double[] phi, eps; //значения аппроксимируюшей функции и отклонения
    public double S, standardDeviation, R2;
    public final DoubleFunction<Double> phiFunc; //аппроксимирующая функция
    public final boolean outputMode, negativeData;
    public final FileWriter file;
    public String name, coef, type;

    public AbstractApproximation(int n, double[] x, double[] y, String name,String type, boolean outputMode, FileWriter file, boolean negativeData){
        this.outputMode = outputMode;
        this.negativeData = negativeData;
        this.file = file;
        this.n = n;
        this.x = x;
        this.y = y;
        this.name = name;
        this.type = type;
        writeResult("\n-----------------------------------------------");
        writeResult(name + ": " + type + "\n");
        this.phiFunc = findFunc();
        if(negativeData){
            writeResult("Данная аппроксимация не возможна с отрицательными числами");
        } else {
            phi = new double[n];
            eps = new double[n];
            S = 0;
            double phi_sr = 0;

            for (int i = 0; i < n; i++) {
                phi[i] = phiFunc.apply(x[i]);
                eps[i] = phi[i] - y[i];
                S += eps[i] * eps[i];
                phi_sr += phi[i];
            }

            standardDeviation = Math.sqrt(S / n); //СКО

            phi_sr = phi_sr / n; //phi среднее
            double sum1 = 0, sum2 = 0;
            for(int i = 0; i < n; i++){
                sum1 += Math.pow((y[i] - phi[i]), 2);
                sum2 += Math.pow((y[i] - phi_sr), 2);
            }
            R2 = 1 - sum1 / sum2; //достоверность

            printAllResults();
        }
//        System.out.println("s= " + S);
    }

    public abstract DoubleFunction<Double> findFunc();

    public void printAllResults(){
        writeResult("Коэффициенты: " + coef);
        writeResult("Мера отклонения: " + S);
        writeResult("Среднеквадратичное отклонение: " + standardDeviation);
        String accuracy;
        if(R2 >= 0.95) {
            accuracy = "Высокая точность аппроксимации";
        } else if(R2 >= 0.75){
            accuracy = "Удовлетворительная точность аппроксимации";
        } else if(R2 >= 0.5){
            accuracy = "Слабая точность аппроксимации";
        } else {
            accuracy = "Недостаточная точность аппроксимации";
        }
        writeResult("Достоверность аппроксимации: " + R2 + " -> " + accuracy + "\n");
        String res = String.format("%-6s|", "x_i");
        for(int i = 0; i < n; i++){
            res += String.format(" %-20s|", rounding(x[i]));
        }
        writeResult(res);
        res = String.format("%-6s|", "y_i");
        for(int i = 0; i < n; i++){
            res += String.format(" %-20s|", rounding(y[i]));
        }
        writeResult(res);
        res = String.format("%-6s|", "phi_i");
        for(int i = 0; i < n; i++){
            res += String.format(" %-20s|", rounding(phi[i]));
        }
        writeResult(res);
        res = String.format("%-6s|", "eps_i");
        for(int i = 0; i < n; i++){
            res += String.format(" %-20s|", rounding(eps[i]));
        }
        writeResult(res);
    }
    private double rounding(double number){
        BigDecimal help = new BigDecimal(number);
        help = help.setScale(15, RoundingMode.HALF_UP);
        return help.doubleValue();
    }

//    public void setOutputMode(boolean outputMode){
//        this.outputMode = outputMode;
//    }
//    public void setFile(FileWriter file){
//        this.file = file;
//    }
    public void writeResult(String string){
        if(outputMode){
            System.out.println(string);
        } else {
            try {
                file.write(string + "\n");
//                file.close();
            } catch (IOException e){
                System.out.println("Проблемы с файлом");
            }
        }
    }
}
