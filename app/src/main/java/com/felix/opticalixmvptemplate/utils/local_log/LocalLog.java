package com.felix.opticalixmvptemplate.utils.local_log;

import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Use this log to write log locally.
 * Created by opticalix on 15/8/24.
 */
public class LocalLog {

    public static void init(String tag, String logDirName, boolean debug) {
        if (!TextUtils.isEmpty(tag)) {
            TAG = tag;
        }
        if (!TextUtils.isEmpty(logDirName)) {
            LOG_DIR_NAME = logDirName;
        }
        isDebug = debug;
        isLogUpload = debug;
    }

    private LocalLog() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = false;//need to enable
    public static boolean isLogUpload = false;//need to enable
    public static String LOG_DIR_NAME = "/Edu24ol/local-log";
    private static String TAG = "LOCAL-LOG";

    private static final String MsgType_Error = "err ";
    private static final String MsgType_Warn = "warn ";
    private static final String MsgType_Debug = "debug ";
    private static final String MsgType_Info = "info ";

    /**
     * Reference : boolean : %b. byte, short, int, long, Integer,
     * Long : %d. NOTE %x for hex. String : %s. Object : %s, for this
     * occasion, toString of the object will be called, and the
     * object can be null - no exception for this occasion.
     *
     * @param obj
     * @param format
     * @param args
     */
    public static void d(Object obj, String format, Object... args) {
        try {
            String msg = null;
            if (format != null) {
                msg = String.format(format, args);
            } else {
                msg = Arrays.toString(args);
            }
            if (TextUtils.isEmpty(msg)) {
                return;
            }
            int line = getCallerLineNumber();
            String filename = getCallerFilename();
            String logText = msgForTextLog(MsgType_Debug, obj, filename, line, msg);
            if (isDebug) {
                Log.d(TAG, logText);
            }
            if (isLogUpload && externalStorageExist()) {
                writeToLog(logText);
            }
        } catch (java.util.IllegalFormatException e) {
            e.printStackTrace();
        }
    }

    public static void d(Object obj, String msg) {
        try {
            if (TextUtils.isEmpty(msg)) {
                return;
            }
            int line = getCallerLineNumber();
            String filename = getCallerFilename();
            String logText = msgForTextLog(MsgType_Debug, obj, filename, line, msg);
            if (isDebug) {
                Log.d(TAG, logText);
            }
            if (isLogUpload && externalStorageExist()) {
                writeToLog(logText);
            }
        } catch (java.util.IllegalFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reference : boolean : %b. byte, short, int, long, Integer,
     * Long : %d. NOTE %x for hex. String : %s. Object : %s, for this
     * occasion, toString of the object will be called, and the
     * object can be null - no exception for this occasion.
     *
     * @param obj
     * @param format
     * @param args
     */
    public static void i(Object obj, String format, Object... args) {
        try {
            String msg = null;
            if (format != null) {
                msg = String.format(format, args);
            } else {
                msg = Arrays.toString(args);
            }
            if (TextUtils.isEmpty(msg)) {
                return;
            }
            int line = getCallerLineNumber();
            String filename = getCallerFilename();
            String logText = msgForTextLog(MsgType_Info, obj, filename, line, msg);
            if (isDebug) {
                Log.i(TAG, logText);
            }
            if (isLogUpload && externalStorageExist()) {
                writeToLog(logText);
            }
        } catch (java.util.IllegalFormatException e) {
            e.printStackTrace();
        }
    }

    public static void i(Object obj, String msg) {
        try {
            if (TextUtils.isEmpty(msg)) {
                return;
            }
            int line = getCallerLineNumber();
            String filename = getCallerFilename();
            String logText = msgForTextLog(MsgType_Info, obj, filename, line, msg);
            if (isDebug) {
                Log.i(TAG, logText);
            }
            if (isLogUpload && externalStorageExist()) {
                writeToLog(logText);
            }
        } catch (java.util.IllegalFormatException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reference : boolean : %b. byte, short, int, long, Integer,
     * Long : %d. NOTE %x for hex. String : %s. Object : %s, for this
     * occasion, toString of the object will be called, and the
     * object can be null - no exception for this occasion.
     *
     * @param obj
     * @param format
     * @param args
     */
    public static void w(Object obj, String format, Object... args) {
        try {
            String msg = null;
            if (format != null) {
                msg = String.format(format, args);
            } else {
                msg = Arrays.toString(args);
            }
            if (TextUtils.isEmpty(msg)) {
                return;
            }
            int line = getCallerLineNumber();
            String filename = getCallerFilename();
            String logText = msgForTextLog(MsgType_Warn, obj, filename, line, msg);
            if (isDebug) {
                Log.w(TAG, logText);
            }
            if (isLogUpload && externalStorageExist()) {
                writeToLog(logText);
            }
        } catch (java.util.IllegalFormatException e) {
            e.printStackTrace();
        }
    }

    public static void w(Object obj, String msg) {
        try {
            if (TextUtils.isEmpty(msg)) {
                return;
            }
            int line = getCallerLineNumber();
            String filename = getCallerFilename();
            String logText = msgForTextLog(MsgType_Warn, obj, filename, line, msg);
            if (isDebug) {
                Log.w(TAG, logText);
            }
            if (isLogUpload && externalStorageExist()) {
                writeToLog(logText);
            }
        } catch (java.util.IllegalFormatException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reference : boolean : %b. byte, short, int, long, Integer,
     * Long : %d. NOTE %x for hex. String : %s. Object : %s, for this
     * occasion, toString of the object will be called, and the
     * object can be null - no exception for this occasion.
     *
     * @param obj
     * @param format
     * @param args
     */
    public static void e(Object obj, String format, Object... args) {
        try {
            String msg = null;
            if (format != null) {
                msg = String.format(format, args);
            } else {
                msg = Arrays.toString(args);
            }
            if (TextUtils.isEmpty(msg)) {
                return;
            }
            int line = getCallerLineNumber();
            String filename = getCallerFilename();
            String logText = msgForTextLog(MsgType_Error, obj, filename, line, msg);
            if (isDebug) {
                Log.e(TAG, logText);
            }
            if (isLogUpload && externalStorageExist()) {
                writeToLog(logText);
            }
        } catch (java.util.IllegalFormatException e) {
            e.printStackTrace();
        }
    }

    public static void e(Object obj, String msg) {
        try {
            if (TextUtils.isEmpty(msg)) {
                return;
            }
            int line = getCallerLineNumber();
            String filename = getCallerFilename();
            String logText = msgForTextLog(MsgType_Error, obj, filename, line, msg);
            if (isDebug) {
                Log.e(TAG, logText);
            }
            if (isLogUpload && externalStorageExist()) {
                writeToLog(logText);
            }
        } catch (java.util.IllegalFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * use when catch exception.
     *
     * @param obj
     * @param msg
     * @param t
     */
    public static void e(Object obj, String msg, Throwable t) {
        int line = getCallerLineNumber();
        String filename = getCallerFilename();
        String methodName = getCallerMethodName();
        String logText = msgForException(obj, methodName, filename, line);
        if (!TextUtils.isEmpty(msg)) {
            logText += " msg:" + msg;
        }
        if (isDebug)
            Log.e(TAG, logText, t);
        if (isLogUpload && externalStorageExist())
            writeToLog(logText, t);
    }

    private static int getCallerLineNumber() {
        return Thread.currentThread().getStackTrace()[4].getLineNumber();
    }

    private static String getCallerFilename() {
        return Thread.currentThread().getStackTrace()[4].getFileName();
    }

    private static String getCallerMethodName() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

    private static String msgForTextLog(String MsgType, Object obj, String filename, int line,
                                        String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(MsgType);
        sb.append(msg);
        sb.append("(P:");
        sb.append(Process.myPid());
        sb.append(")");
        sb.append("(T:");
        sb.append(Thread.currentThread().getId());
        sb.append(")");
        sb.append("(C:");
        if (objClassName(obj) == "String") {
            sb.append(obj);
        } else {
            sb.append(objClassName(obj));
        }
        sb.append(")");
        sb.append("at (");
        sb.append(filename);
        sb.append(":");
        sb.append(line);
        sb.append(")");
        String ret = sb.toString();
        return ret;
    }

    private static String msgForException(Object obj, String methodname, String filename, int line) {
        StringBuilder sb = new StringBuilder();
        if (obj instanceof String)
            sb.append((String) obj);
        else
            sb.append(obj.getClass().getSimpleName());
        sb.append(" Exception occurs at ");
        sb.append("(P:");
        sb.append(Process.myPid());
        sb.append(")");
        sb.append("(T:");
        sb.append(Thread.currentThread().getId());
        sb.append(") at ");
        sb.append(methodname);
        sb.append(" (");
        sb.append(filename);
        sb.append(":" + line);
        sb.append(")");
        String ret = sb.toString();
        return ret;
    }

    private static String objClassName(Object obj) {
        if (obj instanceof String)
            return (String) obj;
        else
            return obj.getClass().getSimpleName();
    }

    public static boolean externalStorageExist() {
        boolean ret = false;
        ret = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
        return ret;
    }

    /**
     * write log text into local file.
     *
     * @param logText
     */
    private static void writeToLog(final String logText) {
        if (externalStorageExist()) {
            try {
                LogWriteHelper.writeLogToFile(getLogFileName(new Date()), logText);
            } catch (Throwable e) {
                Log.e(TAG, "writeLogToFile fail:" + logText, e);
            }
        }
    }

    /**
     * write log text and exception msg into local file.
     *
     * @param logText
     */
    private static void writeToLog(String logText, Throwable t) {
        StringWriter sw = new StringWriter();
        sw.write(logText);
        sw.write("\n");
        t.printStackTrace(new PrintWriter(sw));
        writeToLog(sw.toString());
    }

    /**
     * use DATE to determine file name.
     *
     * @param date
     * @return
     */
    public static String getLogFileName(Date date) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sb.append(simpleDateFormat.format(date)).append("-").append(LogWriteHelper.LOG_SUFFIX_NAME);
        return sb.toString();
    }


}
