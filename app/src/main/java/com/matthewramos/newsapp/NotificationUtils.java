package com.matthewramos.newsapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class NotificationUtils {
    private static final int NEWS_REFRESH_REMINDER_NOTIFICATION_ID = 1138;
    private static final int NEWS_REFRESH_REMINDER_PENDING_INTENT_ID = 3417;
    private static final String NEWS_REFRESH_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    private static final int ACTION_REFRESH_PENDING_INTENT_ID = 1;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUser(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NEWS_REFRESH_REMINDER_NOTIFICATION_CHANNEL_ID, "Priority",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_baseline_refresh_24px);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NEWS_REFRESH_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_baseline_refresh_24px)
                .setLargeIcon(bitmap)
                .setContentTitle("Check for updates?")
                .setContentText("Updating News")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Update News."))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(refreshNewsAction(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(NEWS_REFRESH_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static NotificationCompat.Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, NewsItemIntentService.class);
        ignoreReminderIntent.setAction(NewsItemReminderTask.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.ic_baseline_cancel_24px,
                "No update.",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    private static NotificationCompat.Action refreshNewsAction(Context context) {
        Intent refreshNewsIntent = new Intent(context, NewsItemIntentService.class);
        refreshNewsIntent.setAction(NewsItemReminderTask.ACTION_REFRESH_NEWS);
        PendingIntent refreshNewsPendingIntent = PendingIntent.getService(
                context,
                ACTION_REFRESH_PENDING_INTENT_ID,
                refreshNewsIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action refreshNewsAction = new NotificationCompat.Action(R.drawable.ic_baseline_refresh_24px,
                "Yes update.",
                refreshNewsPendingIntent);
        return refreshNewsAction;
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                NEWS_REFRESH_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
