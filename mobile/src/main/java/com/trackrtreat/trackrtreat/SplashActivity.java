package com.trackrtreat.trackrtreat;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    Intent i;
    ProgressBar pBar;
    int progress = 0;
    ProgressBarTask pbTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        pbTask = new ProgressBarTask();
        pbTask.execute();
    }

    private class ProgressBarTask extends AsyncTask<Void, Void, Void> {

        Thread thread = new Thread() {
            public void run() {
                while (pBar.getProgress() < 100) {
                    try {
                        Thread.sleep(3000);
                        progress += 10;
                    } catch(Throwable t) {

                    }
                    publishProgress();
                }
            }
        };

        @Override
        protected Void doInBackground(Void... voids) {
            thread.start();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            pBar.setProgress(progress);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
        }
    }


}
