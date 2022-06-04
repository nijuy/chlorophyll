package com.example.myapplication.calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class AlarmRecevier extends BroadcastReceiver {

    public AlarmRecevier() { }

    NotificationManager manager;
    NotificationCompat.Builder builder;

    // Notification Channel
    private static String CHANNEL_ID;
    private static String CHANNEL_NAME;

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        String plantName = intent.getExtras().getString("plantName");
        String todoWhat = intent.getExtras().getString("todoWhat");
        CHANNEL_ID = plantName + todoWhat;
        CHANNEL_NAME = plantName + todoWhat;

        builder = null;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel( new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT));
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        // 알림창 클릭 시 MainActivity 화면
        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 101, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        // 알림창 아이콘
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        // 알림창 제목
        builder.setContentTitle(plantName);
        // 알림창 내용
        builder.setContentText(todoWhat);
        // 알림창 터치 시 자동 삭제
        builder.setAutoCancel(true);

        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        manager.notify(1, notification);
    }
}