package com.codecoe.coffeetime;

/**
 * This class receives the alarm intent and performs the 'alarm' action notifications
 * based upon it.
 */
  import android.app.Notification;
  import android.app.NotificationManager;
  import android.content.BroadcastReceiver;
  import android.content.Context;
  import android.content.Intent;
  import android.media.MediaPlayer;
  import android.os.PowerManager;
  import android.support.v4.app.NotificationCompat;
  import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID=1;
    private NotificationManager notificationManager;
    private Notification alarmNotification;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm activated!", Toast.LENGTH_LONG).show();

        alarmNotification = new NotificationCompat.Builder(context)
                .setContentTitle("Go get yer coffee!")
                .setTicker("Coffee Time!")
                .setWhen(System.currentTimeMillis())
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        Globals.mediaPlayer = MediaPlayer.create(context, R.raw.alarm_sound);
        Globals.mediaPlayer.setLooping(true);
        Globals.mediaPlayer.start();

        notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, alarmNotification);

        while(Globals.mediaPlayer.isPlaying()){
            AlarmView.cancelAlarm();
        }
    }

}
