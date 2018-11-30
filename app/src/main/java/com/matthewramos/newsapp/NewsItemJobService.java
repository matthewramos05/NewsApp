package com.matthewramos.newsapp;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.os.AsyncTask;

public class NewsItemJobService extends JobService {

    static AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters params) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                NewsItemReminderTask.executeTask(NewsItemJobService.this, NewsItemReminderTask.ACTION_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(params, false);
            }
        };
        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(false);
        return true;
    }
}
