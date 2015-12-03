package me.yilv.yilvtravel;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.List;

public class LogUtil {
    private static final boolean SHOW_LOG = Config.IS_DEBUG;

    public static void hex(String TAG, byte[] data, int offset, int count) {
        if (SHOW_LOG) {
            String line = "** hex data " + count + " bytes **\n";
            for (int i = offset; i < count; i++) {
                int pos = (i - offset + 1);

                line += String.format("%02X ", data[i]);

                if (pos % 16 == 0) {
                    line += "\n";
                } else if (pos % 4 == 0) {
                    line += " ";
                }
            }
            Log.w(TAG, line);
        }
    }

    public static void printStackTrace(Throwable t) {
        if (SHOW_LOG) {
            t.printStackTrace();
        }
    }

    public static String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static void printStackTrace(Exception e) {
        if (SHOW_LOG) {
            e.printStackTrace();
        }
    }

    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static void i(String tag, String msg) {
        if (SHOW_LOG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (SHOW_LOG) {
            Log.w(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (SHOW_LOG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (SHOW_LOG) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (SHOW_LOG) {
            Log.v(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (SHOW_LOG) {
            Log.e(tag, msg, tr);
        }
    }

    public static void dumpObject(String tag, Object obj) {
        if (SHOW_LOG) {
            Field[] fields = obj.getClass().getFields();
            for (Field f : fields) {
                try {
                    Object o = f.get(obj);
                    if (o instanceof List) {
                        i(tag, f.getName() + "(list size = " + ((List<?>) o).size() + "):");
                        for (Object l : (List<?>) o) {
                            dumpObject(tag, l);
                        }
                    } else {
                        i(tag, f.getName() + " : " + f.get(obj));
                    }
                } catch (Exception e) {
                    printStackTrace(e);
                }
            }
        }
    }

    public static void printInfo(String msg) {
        Log.i("INFO", msg);
    }

}
