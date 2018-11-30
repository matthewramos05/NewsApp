package com.matthewramos.newsapp;

import android.app.IntentService;
import android.content.Intent;

public class NewsItemIntentService extends IntentService {

    public NewsItemIntentService() {
        super("NewsReminderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        NewsItemReminderTask.executeTask(this,action);
    }
}
