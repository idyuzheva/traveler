package com.idyuzheva.traveler;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.idyuzheva.traveler.utils.AsyncUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private WeakReference<MyAsyncTask> ref;

    private List<MyAsyncTask> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                for (int i = 0; i < 3; i++) {
                    MyAsyncTask task = new MyAsyncTask();
                    tasks.add(task);
                    AsyncUtils.executeAsyncTask(task, "param" + i);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        for (MyAsyncTask task : tasks) {
            if (task != null) {
                Log.d("[MyAsyncTask]", "onDestroy " + task.name);
                task.cancel(true);
            }
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final class MyAsyncTask extends AsyncTask<String, Integer, Void> {

        private String name;

        @Override
        protected void onPreExecute() {
            Log.d("[MyAsyncTask]", "start: onPreExecute");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            name = params[0];
            Log.d("[MyAsyncTask]", name + " doInBackground");
            for (int i = 1; i < 2; i++) {
                if (isCancelled()) {
                    Log.d("[MyAsyncTask]", "doInBackground - isCancelled");
                    break;
                }
                publishProgress(i);
                SystemClock.sleep(1000);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (!isCancelled()) {
                Log.d("[MyAsyncTask]", name + " onProgressUpdate");
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("[MyAsyncTask]", name + " finish: onPostExecute");
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            Log.d("[MyAsyncTask]", name + "finish: onCancelled");
            super.onCancelled();
        }
    }
}
