package com.codecoe.coffeetime;

/**
 * This class receives the alarm intent and performs the 'alarm' action notifications
 * based upon it.
 */
  import android.app.Notification;
  import android.app.NotificationManager;
  import android.app.PendingIntent;
  import android.content.BroadcastReceiver;
  import android.content.Context;
  import android.content.Intent;
  import android.net.Uri;
  import android.support.v4.app.NotificationCompat;
  import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID=1;
    NotificationManager notificationManager;
    Notification alarmNotification;
    Uri sound = Uri.parse("android.resource://com.codecoe.coffeetime/" + R.raw.alarm_sound);

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm activated!", Toast.LENGTH_LONG).show();

        Intent alarmIntent = new Intent(Intent.ACTION_VIEW);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmNotification = new NotificationCompat.Builder(context)
                .setContentTitle("Go get yer coffee!")
                .setTicker("Coffee Time!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setSound(sound)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .build();

        notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, alarmNotification);
    }

}
