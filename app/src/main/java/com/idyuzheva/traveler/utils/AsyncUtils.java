package com.idyuzheva.traveler.utils;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by inessa on 05/01/16.
 */
public class AsyncUtils {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

    private AsyncUtils() {
    }
}
