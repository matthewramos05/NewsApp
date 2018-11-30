package com.matthewramos.newsapp;

import android.content.Context;

public class NewsItemReminderTask {

    public static final String ACTION_REFRESH_NEWS ="action_refresh_news";
    public static final String ACTION_DISMISS_NOTIFICATION = "action_dismiss_notification";
    public static final String ACTION_REMINDER = "action_reminder";


    public static void executeTask(Context context, String action) {
        if (ACTION_REFRESH_NEWS.equals(action)) {
            refreshNews(context);
        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        } else if (ACTION_REMINDER.equals(action)) {
            issueReminder(context);
        }
    }

    private static void refreshNews(Context context) {
        NewsItemRepository.syncNews();
        NotificationUtils.clearAllNotifications(context);
    }

    private static void issueReminder(Context context) {
        NotificationUtils.remindUser(context);
    }

}
