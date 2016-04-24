
package com.felix.opticalixmvptemplate.utils.local_log;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by opticalix on 15/8/24.
 */
public class LogWriteHelper {
    public static final String TAG = "LogWriteHelper";
    public static final int MAX_DAYS = 2;// Max days
    public static final int MAX_FILE_SIZE = 10 * 1024;//Max Mb
    public static final String LOG_SUFFIX_NAME = "logs.txt";
    private static final SimpleDateFormat LOG_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd kk:mm:ss.SSS", Locale.getDefault());

    private static boolean sOnCutting = false;//use a working flag, so you can collect log during CUT.
    private static final String CUT_TEMP_SUFFIX_NAME = ".temp";
    private static final String COLLECT_TEMP_SUFFIX_NAME = ".collect";

    public synchronized static void writeLogToFile(String fileName, String msg)
            throws IOException {
        //prepare dir and file
        File fileToWrite = null;
        File esdf = Environment.getExternalStorageDirectory();
        String dir = esdf.getAbsolutePath() + LocalLog.LOG_DIR_NAME;
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        final File logFile = new File(dir + File.separator + fileName);
        final String logFilePath = logFile.getAbsolutePath();
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        File tempFile = new File(logFile.getAbsolutePath() + COLLECT_TEMP_SUFFIX_NAME);
        final String tempFilePath = tempFile.getAbsolutePath();
        if (sOnCutting) {
            //if cut is running, use temp file replace
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
            fileToWrite = tempFile;
        } else {
            fileToWrite = logFile;
        }

        //combine DATE and LOG
        Date date = new Date();
        String strLog = LOG_FORMAT.format(date);

        strLog = strLog + ' ' + msg + '\n';

        //write into file
        FileWriter fileWriter = new FileWriter(fileToWrite, true);
        fileWriter.write(strLog);
        fileWriter.flush();
        fileWriter.close();

        if (!sOnCutting && logFile.length() > MAX_FILE_SIZE) {
            //check every time.
            sOnCutting = true;
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }

            new Thread(new CutFileRunnable(logFile, logFilePath, tempFilePath)).start();
        }

    }

    static class CutFileRunnable implements Runnable {
        public CutFileRunnable(File logFile, String logFilePath, String tempFilePath) {
            this.logFile = logFile;
            this.logFilePath = logFilePath;
            this.tempFilePath = tempFilePath;
        }

        private File logFile;
        private String logFilePath;
        private String tempFilePath;

        @Override
        public void run() {
            //step1 cut
            boolean b = cutLogToHalf(logFile);
            Log.d(TAG, "child thread run cutHalf result=" + b);
            if (b) {
                //step2 copy other log into half file.
                boolean b1 = mergeCollectToLog(logFilePath, tempFilePath);
                Log.d(TAG, "mergeCollectToLog result=" + b1 + ", thread=" + Thread.currentThread().getName());
            }
        }

    }

    /**
     * @param logFilePath
     * @param tempFilePath
     * @return true if success
     */
    private synchronized static boolean mergeCollectToLog(String logFilePath, String tempFilePath) {
        long start = System.currentTimeMillis();

        File logFile = new File(logFilePath);
        File tempLogFile = new File(tempFilePath);
        if (!logFile.exists() || !tempLogFile.exists()) {
            Log.e(TAG, "mergeCollectToLog, file not exists! thread=" + Thread.currentThread().getName());
        } else {
            BufferedReader bufferedReader = null;
            BufferedWriter bufferedWriter = null;
            try {
                FileWriter fileWriter = new FileWriter(logFile, true);
                bufferedWriter = new BufferedWriter(fileWriter);
                FileReader fileReader = new FileReader(tempLogFile);
                bufferedReader = new BufferedReader(fileReader);
                String thisLine = null;
                while ((thisLine = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(thisLine);
                    bufferedWriter.newLine();
                }
                tempLogFile.delete();
                Log.d(TAG, "temp collect log file delete. thread=" + Thread.currentThread().getName());

                long end = System.currentTimeMillis();
                Log.d(TAG, "copy tempLog spend: " + (end - start) / 1000 + "seconds.");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                sOnCutting = false;
                closeQuietly(bufferedReader);
                closeQuietly(bufferedWriter);
            }
        }
        sOnCutting = false;
        return false;
    }

    /**
     * use RandomAccessFile. copy old data into temp file, then remove old file and rename the temp.
     *
     * @param old
     * @return true if success
     */
    private static boolean cutLogToHalf(File old) {
        Log.i(TAG, "logFile is too big! need cut half.");
        long start = System.currentTimeMillis();

        BufferedWriter bfdTempWriter = null;
        RandomAccessFile randomAccessFile = null;
        try {
            //prepare temp file
            if (!old.exists()) {
                return false;
            }
            File temp = new File(old.getAbsolutePath() + CUT_TEMP_SUFFIX_NAME);
            if (temp.exists()) {
                temp.delete();
            }
            temp.createNewFile();
            FileWriter tempWriter = new FileWriter(temp);
            bfdTempWriter = new BufferedWriter(tempWriter);//fixme better buf?

            //read from old file, start at half
            randomAccessFile = new RandomAccessFile(old, "r");
            long newStart = randomAccessFile.length() / 2;
            randomAccessFile.seek(newStart);
            randomAccessFile.readLine();//just skip this line.
            bfdTempWriter.write("-----CUT HALF START " + LOG_FORMAT.format(new Date()) + "-----\n");

            String thisLine = null;
            while ((thisLine = randomAccessFile.readLine()) != null) {
                bfdTempWriter.write(thisLine);
                bfdTempWriter.newLine();
            }

            //rename. remove.
            if (!old.delete() || !temp.renameTo(old.getAbsoluteFile())) {
                Log.e(TAG, "cutHalfFile, delete or rename error!");
            }
            long end = System.currentTimeMillis();
            Log.d(TAG, "cutHalf file to: " + old.length() / 1024 + "kb, spend: " + (end - start) / 1000 + "seconds.");
            //2m --> 1m: about 8s.
            return true;
        } catch (IOException e) {
            Log.e(TAG, "cutHalfFile error!", e);
        } finally {
            closeQuietly(randomAccessFile);
            closeQuietly(bfdTempWriter);
        }
        return false;
    }

    /**
     * delete expire log files depends on MAX_DAYS.
     */
    public static void deleteExpireLogFiles() {
        if (!LocalLog.externalStorageExist()) {
            return;
        }
        File esdf = Environment.getExternalStorageDirectory();
        String dir = esdf.getAbsolutePath() + LocalLog.LOG_DIR_NAME;
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        Calendar c = Calendar.getInstance();
        ArrayList<String> remainFileNames = new ArrayList<>();
        remainFileNames.add(new File(dir + File.separator + LocalLog.getLogFileName(c.getTime())).getName());
        for (int i = 1; i < MAX_DAYS; i++) {
            c.add(Calendar.DATE, -1);
            remainFileNames.add(new File(dir + File.separator + LocalLog.getLogFileName(c.getTime())).getName());
        }
        Log.d(TAG, "LogFileNames need to remain: " + remainFileNames.toString());

        for (File f : dirFile.listFiles()) {
            if (!remainFileNames.contains(f.getName()) && f.exists() && f.isFile()) {
                f.delete();
                Log.d(TAG, "delete logFile: " + f.getAbsolutePath());
            }
        }
    }

    public static void createTestLogFiles(int days) {
        if (!LocalLog.externalStorageExist()) {
            return;
        }
        File esdf = Environment.getExternalStorageDirectory();
        String dir = esdf.getAbsolutePath() + LocalLog.LOG_DIR_NAME;
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        Calendar c = Calendar.getInstance();
        int temp = 0;
        for (int i = 1; i < days; i++) {
            c.add(Calendar.DATE, -1);
            File logFile = new File(dir + File.separator + LocalLog.getLogFileName(c.getTime()));
            if (!logFile.exists()) {
                try {
                    if (logFile.createNewFile()) {
                        temp++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(TAG, "createTestLogFile: number=" + temp);
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            //ignore
        }
    }

}
