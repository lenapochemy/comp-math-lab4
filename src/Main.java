import utils.Solver;

import java.io.FileWriter;
import java.util.Scanner;
import java.util.SortedMap;

import utils.ScannerManager;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ScannerManager scannerManager = new ScannerManager(scanner);


        if (scannerManager.sayInputMode()) {
            scannerManager.setFileMode(false);
        } else {
            scannerManager.setScanner(scannerManager.sayNewScanner());
            scannerManager.setFileMode(true);
        }

        int n = scannerManager.sayN();
        SortedMap<Double, Double> map = scannerManager.sayInitialData(n);
        boolean negativeData = false;
        double[] x = new double[n], y = new double[n];
        int j = 0;
        for(double i : map.keySet()){
            x[j] = i;
            y[j] = map.get(i);
            if(i < 0 || map.get(i) < 0) negativeData = true;
            j++;
        }

        scannerManager.setScanner(scanner);
        scannerManager.setFileMode(false);
        boolean outputMode = scannerManager.sayOutputMode();
        FileWriter file;
        if(!outputMode) file = scannerManager.sayFileToWrite();
        else file = null;

        Solver solver = new Solver(x, y, outputMode, file, negativeData);
        solver.solve();
    }
}
//C:\Users\Elena\IdeaProjects\compMath\lab4\src\files\data1