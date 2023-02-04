package lee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {
    private static final DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

    public static void log(String level, String text) {
        System.out.println(format.format(new Date()) + " - " + level + " - " + text);
    }

    public static void debug(String text) {
        log("debug", text);
    }

    public static void info(String text) {
        log("info", text);
    }

    public static void error(String text) {
        log("error", text);
    }
}
