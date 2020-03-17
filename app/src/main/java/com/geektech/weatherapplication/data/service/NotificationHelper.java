package com.geektech.weatherapplication.data.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.geektech.weatherapplication.R;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationHelper {

    private static String CHANNEL_ID = "Service";
    private static String ACTION_CLOSE = "ACTION_CLOSE";


    public static void showNotification(Context context, RemoteMessage remoteMessage){
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        NotificationCompat.Builder builder = createNotification(context);

        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setContentTitle(remoteMessage.getNotification().getTitle());

        managerCompat.notify(1, builder.build());
    }


    public static NotificationCompat.Builder createNotification(Context context){
        createNotificationChannel(context);

        Intent intent = new Intent(context, ServiceBuilder.class);
        intent.setAction(ACTION_CLOSE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .addAction(R.mipmap.ic_launcher, "STOP", pendingIntent)
                .setContentTitle("Посмотрите погоду")
                .setContentText("Погода может быть изменена. Не забудьте посмотреть")
                .setSmallIcon(R.mipmap.ic_launcher);
        return builder;
    }


    private static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Изменения погоды";
            String description = "Привет";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
