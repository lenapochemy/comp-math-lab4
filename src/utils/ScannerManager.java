package utils;

import exceptions.FileException;
import exceptions.IncorrectValueException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ScannerManager {
    private Scanner scanner;
    private boolean fileMode;
    public ScannerManager(Scanner scanner){
        this.scanner = scanner;
    }

    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }

    public void setFileMode(boolean fileMode){
        this.fileMode = fileMode;
    }
    //вернет true если ввод с клавиатуры
    public boolean sayInputMode(){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.print("Вы хотите вводить данные с клавиатуры или из файла? (k/f) ");
                String ans = scanner.nextLine().trim();
                switch (ans) {
                    case "" ->
                        throw new NullPointerException();
                    case "k" -> {
                        flag = true;
                        return true;
                    }
                    case "f" -> {
                        flag = true;
                        return false;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть \"k\" или \"f\"");
            }
        }
        return flag;
    }

    //вернет true если вывод на экран
    public boolean sayOutputMode(){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.print("Результаты вывести на экран или в записать в файл? (s/f) ");
                String ans = scanner.nextLine().trim();
                switch (ans) {
                    case "" ->
                            throw new NullPointerException();
                    case "s" -> {
                        flag = true;
                        return true;
                    }
                    case "f" -> {
                        flag = true;
                        return false;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть \"s\" или \"f\"");
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return flag;
    }

    public Scanner sayNewScanner(){
        String sFile;
        Scanner scanner1 = null;
        while(scanner1 == null){
            try{
                System.out.println("Введите путь к файлу:");
                sFile = scanner.nextLine().trim();
                if(sFile.isEmpty()) throw new NullPointerException();
                File file = new File(sFile);
                if(file.exists() && !file.canRead()) throw new FileException();
                scanner1 = new Scanner(file);
            } catch (NullPointerException e){
                System.out.println("Путь не может быть пустым");
            } catch (FileException e){
                System.out.println("Данные из файла невозможно прочитать");
            } catch (FileNotFoundException e){
                System.out.println("Файл не найден");
            }
        }
        return scanner1;
    }


    public FileWriter sayFileToWrite(){
        String sFile;
        FileWriter writer = null;
        while(writer == null){
            try{
                System.out.println("Введите путь к файлу:");
                sFile = scanner.nextLine().trim();
                if(fileMode) System.out.println(sFile);
                if(sFile.isEmpty()) throw new NullPointerException();
                File file = new File(sFile);
                if(file.exists() && !file.canWrite()) throw new FileException();
                writer = new FileWriter(file);
            } catch (NullPointerException e){
                System.out.println("Путь не может быть пустым");
            } catch (FileException e){
                System.out.println("В файл невозможно записать");
            } catch (IOException e){
                System.out.println("Файл не найден");
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return writer;
    }

    public SortedMap<Double, Double> sayInitialData(int n){
//        int n = sayN();
        SortedMap<Double, Double> map = new TreeMap<>();
        System.out.println("Введите исходные точки парами (x, y) через пробел");
        for(int i = 0; i < n; i++){
            String[] num = new String[2];
            double x = 0,y = 0;
            String sNum;
            boolean flag = true;
            while (flag){
                try {
//                    System.out.print("Введите " + name +  " : ");
                    sNum = scanner.nextLine().trim();
                    if(fileMode) System.out.println(sNum);
                    if(sNum.isEmpty()) throw new NullPointerException();
                    num = sNum.split(" ",2);
                    x = Double.parseDouble(num[0]);
                    y = Double.parseDouble(num[1]);
                    flag = false;
                } catch (NullPointerException e){
                    System.out.println("Значение точки не может быть пустым");
                    if(fileMode) errorEnd();
                }  catch (NumberFormatException e) {
                    System.out.println("Значение точки должно быть парой чисел");
                    if (fileMode) errorEnd();
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Нужно ввести два числа");
                    if(fileMode) errorEnd();
                } catch (NoSuchElementException e){
                    System.out.println("Данные не найдены в файле");
                    System.exit(0);
                }
            }

//            map.put(Double.parseDouble(num[0]), Double.parseDouble(num[1]));
            map.put(x, y);
        }
        return map;
    }

    public int sayN(){
        int num = 0;
        String sNum;
        while (num < 7  || num > 12){
            try {
                System.out.print("Введите количество точек от 7 до 12: ");
                sNum = scanner.nextLine().trim();
                if(fileMode) System.out.println(sNum);
                if(sNum.isEmpty()) throw new NullPointerException();
                num = Integer.parseInt(sNum);
                if(num < 7  || num > 12) throw new IncorrectValueException();
            } catch (IncorrectValueException e){
                System.out.println("Значение количества точек должно быть положительным числои из промежутка [7;12]");
                if(fileMode) errorEnd();
            } catch (NullPointerException e){
                System.out.println("Количество точек не может быть пустым");
                if(fileMode) errorEnd();
            }  catch (NumberFormatException e){
                System.out.println("Количество точек должно быть целым числом");
                if(fileMode) errorEnd();
            }
        }
        return num;
    }

    private void errorEnd(){
        System.out.println("В файле неверные данные, программа завершена");
        System.exit(0);
    }

}
