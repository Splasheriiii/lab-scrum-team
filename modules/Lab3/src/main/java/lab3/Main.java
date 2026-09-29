package lab3;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            // Принудительно устанавливаем UTF-8 для вывода
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(args[0].equals("1")) {
            task1(
                    Integer.parseInt(args[1]),              //TARGETS
                    Integer.parseInt(args[2]),              //TRACKED
                    Integer.parseInt(args[3]),              //MEASUREMENTS
                    Integer.parseInt(args[4]),              //CALC_PER_TARGET
                    Double.parseDouble(args[5])             //LAMBDA
            );
        } else if(args[0].equals("2")) {
            task2(
                    Integer.parseInt(args[1]),              //TARGETS
                    Integer.parseInt(args[2]),              //TRACKED
                    Integer.parseInt(args[3]),              //MEASUREMENTS
                    Integer.parseInt(args[4])               //CALC_PER_TARGET
            );
        } else if(args[0].equals("3")) {
            // 1. Сначала разбираем сложные аргументы (массивы) из строк
            double[] Vj = Arrays.stream(args[9].split(","))
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            int[] Bk = Arrays.stream(args[10].split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            // 2. Теперь вызываем метод task3, передавая уже готовые данные
            task3(
                    Double.parseDouble(args[6]),    // R0
                    Double.parseDouble(args[7]),    // lambda
                    Integer.parseInt(args[8]),      // n
                    Vj,                             // массив Vj
                    Bk,                             // массив Bk
                    Double.parseDouble(args[11])    // Vnext
            );
        }

    }

    static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    // ---------------- Задание 1 ----------------
    static double task1(int TARGETS, int MEASUREMENTS, int TRACKED, int CALC_PER_TARGET, double LAMBDA) {
        System.out.println("========== Задание №1 ==========");
        // Принято: n2* = (цели×измерения×параметры)+(цели×рассчитываемые параметры)
        double n2 = (TARGETS * MEASUREMENTS * TRACKED) + (TARGETS * CALC_PER_TARGET);
        double Vstar = (n2 + 2) * log2(n2 + 2);
        double B = (Vstar * Vstar) / (3000.0 * LAMBDA);

        System.out.printf("n2* = %.0f%n", n2);
        System.out.printf("V*  = %.3f%n", Vstar);
        System.out.printf("B   = %.3f%n", B);

        return B;
    }

    // ---------------- Задание 2 ----------------
    static ResultTask2 task2(int TARGETS, int MEASUREMENTS, int TRACKED, int CALC_PER_TARGET) {
        System.out.println("\n========== Задание №2 ==========");

        double n2 = (TARGETS * MEASUREMENTS * TRACKED) + (TARGETS * CALC_PER_TARGET);
        double k = n2 / 8.0;
        double i = (log2(n2) / 3.0) + 1;
        double K;

        if (k >= 8) {
            K = n2 / 8.0 + n2 / (8.0 * 8.0);
        } else {
            K = k;
        }

        double N = ( 220 * K ) + ( K * log2(K) );
        double V = K * 220 * log2(48);
        double P = 3 * N / 8.0;

        int m = 5;      // число программистов
        int v = 20;     // производительность, команд/день

        double TkDays = 3 * N / (8.0 * m * v);
        double TkHours = TkDays * 8.0;
        double B = V / 3000.0;
        double tn = TkHours / (2 * Math.log(B));

        System.out.printf("n2* = %.0f%n", n2);
        System.out.printf("k   = %.3f%n", k);
        System.out.printf("i   = %.3f%n", i);
        System.out.printf("K   = %.3f%n", K);
        System.out.printf("N   = %.3f%n", N);
        System.out.printf("V   = %.3f%n", V);
        System.out.printf("P   = %.3f%n", P);
        System.out.printf("Tk  = %.3f дней = %.3f ч%n", TkDays, TkHours);
        System.out.printf("B   = %.3f%n", B);
        System.out.printf("tн  = %.3f ч%n", tn);

        return new ResultTask2(n2,k,i,K,N,V,P,TkDays,TkHours,B,tn);
    }
    public record ResultTask2(double n2,double k,double i,double K,double N,double V,double P,double TkDays,double TkHours,double B,double tn) {}

    // ---------------- Задание 3 ----------------
    static void task3(double R0, double lambda, int n, double[] Vj, int[] Bk, double Vnext) {
        System.out.println("\n========== Задание №3 ==========");

        for (int variant = 1; variant <= 3; variant++) {
            var res = task3_1(R0, lambda, n, Vj, Bk, Vnext, variant);
            if (res.Rate<0) {
                System.out.printf("Вариант c%d некорректен. Модель не устойчива (R<0) R = %.3f\n", variant, res.Rate);
            } else {
                System.out.printf("Вариант c%d: R = %.3f, \t ожидаемые ошибки = %.3f \n",
                        variant, res.Rate, res.Errors);
            }
        }
    }

    static ResultTask3 task3_1(double R0, double lambda, int n, double[] Vj, int[] Bk, double Vnext, int variant) {
        double VjSum=0;

        for(int q=0; q< Vj.length; q++) {
            VjSum += Vj[q];
        }

        double R = R0;
        for (int j = 0; j < n; j++) {
            double c = coefficient(variant, lambda, R);
            if (Bk[j] > 0) {
                double factor = 1 + 0.001 * (VjSum - Bk[j] / c);
                R = R * factor;
            } else {
                R = R * (1 + 0.001 * VjSum);
            }
        }
        double expectedErrors = coefficient(variant, lambda, R) * Vnext;

        return new ResultTask3(R, expectedErrors);
    }

    static double coefficient(int variant, double lambda, double R) {
        switch (variant) {
            case 1:
                return 1.0 / (lambda + R);
            case 2:
                return 1.0 / (lambda * R);
            case 3:
                return 1.0 / lambda + 1.0 / R;
            default:
                return 0;
        }
    }
    public record ResultTask3(double Rate, double Errors) {}

}
