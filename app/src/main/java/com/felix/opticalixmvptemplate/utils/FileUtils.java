package com.felix.opticalixmvptemplate.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * Created by opticalix@gmail.com on 2016/2/27.
 */
public class FileUtils {
    @Nullable
    public static File getDiskCacheDir(Context context, String uniqueName) throws IOException {
        File cacheDir;
        String cacheUniqueDirPath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {

            cacheDir = context.getExternalCacheDir();
        } else {
            cacheDir = context.getCacheDir();
        }
        if (cacheDir != null) {
            cacheUniqueDirPath = cacheDir.getPath() + File.separator + uniqueName;
            File cacheUniqueDir = new File(cacheUniqueDirPath);
            if (!cacheUniqueDir.exists() && cacheUniqueDir.mkdirs()) {
                return cacheUniqueDir;
            }
        }
        return null;
    }
}
