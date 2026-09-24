package lab2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class Main {

    public static void main(String[] args) throws IOException {
        // Имя файла: первый аргумент командной строки или input.txt
        String filename = args.length > 0 ? args[0] : "input.txt";

        double[] x = readXi(filename);
        int n = x.length;
        double sumX = 0.0;
        double sumIX = 0.0;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumIX += (i + 1) * x[i]; // i*Xi, i начинается с 1
        }

        // Решение нелин. уравнения для B
        double B = solveB(n, sumX, sumIX);
//        int B = (int) Math.round(solveB(n, sumX, sumIX));  // ошибки это int?

        // K по формуле максим. правдоподобия
        double K = n / ((B + 1) * sumX - sumIX);

        // Среднее время до (n+1)-й ошибки
        double Xnext = 1.0 / (K * (B - n));

        // Время до конца тестирования t_k = (1/K) * sum(1/i), i = 1..(B-n)
        int remaining = (int) Math.round(B) - n;  // остаток
        double tK = harmonic(remaining) / K;

        System.out.print("");
        System.out.printf("n = %d%n", n);
        System.out.printf("sumX = %.3f%n", sumX);
        System.out.printf("sum i*Xi = %.3f%n", sumIX);
        System.out.println();

        System.out.println("--- RESULT ---");
        System.out.printf("B = %s%n", B);
        System.out.printf("K = %.10f%n", K);
        System.out.printf("X_{n+1} = %.6f hours%n", Xnext);
        System.out.printf("t_k = %.3f hours%n", tK);
        System.out.println("=== RESULT ===");
    }

    /**
     * Чтение Xi из файла: одна строка — одно число.
     * Пустые строки пропускаются.
     */
    static double[] readXi(String filename) throws IOException {
        List<Double> list = new ArrayList<>();

        // Если файл есть на диске рядом с проектом — читаем его.
        java.nio.file.Path path = Paths.get(filename);
        List<String> lines;

        if (Files.exists(path)) {
            lines = Files.readAllLines(path);
        } else {
            // Иначе берём из ресурсов (внутри JAR)
            try (java.io.InputStream in = Main.class.getClassLoader().getResourceAsStream(filename)) {
                if (in == null) {
                    throw new IOException("File not found: " + filename);
                }
                lines = new java.io.BufferedReader(
                        new java.io.InputStreamReader(in))
                        .lines().collect(java.util.stream.Collectors.toList());
            }
        }

        for (String line : lines) {
            String s = line.trim();
            if (s.isEmpty()) continue;
            s = s.replace(',', '.');
            list.add(Double.parseDouble(s));
        }

        double[] arr = new double[list.size()];
        for (int i = 0; i < list.size(); i++) arr[i] = list.get(i);
        return arr;
    }

    /**
     * Решение уравнения для B методом бисекции.
     */
    static double solveB(int n, double sumX, double sumIX) {
        double low = n + 1e-9;
        double high = n + 1.0;

        // Ищем верхнюю границу, где f(high) < 0
        while (f(high, n, sumX, sumIX) > 0) {
            high *= 2.0;
        }

        for (int i = 0; i < 200; i++) {
            double mid = (low + high) / 2.0;
            if (f(mid, n, sumX, sumIX) > 0) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return (low + high) / 2.0;
    }

    /**
     * Левая часть минус правая часть уравнения для B.
     */
    static double f(double B, int n, double sumX, double sumIX) {
        double left = 0.0;
        for (int i = 1; i <= n; i++) {
            left += 1.0 / (B - i + 1);
        }
        double right = n * sumX / ((B + 1) * sumX - sumIX);
        return left - right;
    }

    /**
     * Гармоническое число H_m = 1 + 1/2 + ... + 1/m.
     */
    static double harmonic(int m) {
        double s = 0.0;
        for (int i = 1; i <= m; i++) {
            s += 1.0 / i;
        }
        return s;
    }
}