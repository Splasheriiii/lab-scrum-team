package find_bugs;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class App {
    
    public static Integer ternarySmell(boolean flag1, boolean flag2) {
        return flag1 ? 1 : flag2 ? 2 : null;
    }

    private static double[] vals = new double[] {1.0, 2.0, 3.0};
    public static double arraySmell(int idx) {
        return (idx < 0 || idx >= vals.length) ? null : vals[idx];
    }

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String dateSmell() {
        return format.format(new Date());
    }

    private static BigDecimal d1 = new BigDecimal("1.1");
    private static BigDecimal d2 = new BigDecimal("1.10");
    public static boolean decimalSmell() {
        return d1.equals(d2);
    }

    public static boolean stringSmell() {

        StringWriter stringWriter1 = new StringWriter();
        PrintWriter printWriter1 = new PrintWriter(stringWriter1);
        printWriter1.printf("%s\n", "str#1");

        StringWriter stringWriter2 = new StringWriter();
        PrintWriter printWriter2 = new PrintWriter(stringWriter2);
        printWriter2.println("str#1");

        return stringWriter1.toString().equals(stringWriter2.toString());
    }

    public static void main(String[] args) {
        ternarySmell(false, false);
        arraySmell(3);
        dateSmell();
        decimalSmell();
        stringSmell();
   }
}
