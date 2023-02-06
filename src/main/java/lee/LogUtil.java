package lee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {
    private static final DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
    private static final String level = "info";


    public static void log(String level, String text) {
        System.out.println(format.format(new Date()) + " - " + level + " - " + text);
    }

    public static void debug(String text) {
        if ("info".equals(level)) {
            return;
        }
        if ("error".equals(level)) {
            return;
        }
        log("debug", text);
    }

    public static void info(String text) {
        if ("error".equals(level)) {
            return;
        }
        log("info", text);
    }

    public static void error(String text) {
        log("error", text);
    }
}
