package com.codecoe.coffeetime;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.util.Calendar;


public class AlarmView extends Activity {
    private static final String SERVER_IP = "192.168.1.250";
    private TextView alarmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_view);

        Calendar now = Calendar.getInstance();
        Globals.alarm = now;

        alarmText = (TextView) findViewById(R.id.alarmTime);
        int minute = now.get(Calendar.MINUTE);
        String minuteText;
        if(minute < 10){
            minuteText = "0" + minute;
            alarmText.setText(now.get(Calendar.HOUR_OF_DAY) + ":" + minuteText);
        }else {
            alarmText.setText(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_view, menu);
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

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void setAlarm(View v) {
        alarmText = (TextView) findViewById(R.id.alarmTime);
        int minute = Globals.alarm.get(Calendar.MINUTE);
        String minuteText;
        if(minute < 10){
            minuteText = "0" + minute;
            alarmText.setText(Globals.alarm.get(Calendar.HOUR_OF_DAY) + ":" + minuteText);
        }else {
            alarmText.setText(Globals.alarm.get(Calendar.HOUR_OF_DAY) + ":" + Globals.alarm.get(Calendar.MINUTE));
        }

        Intent coffee_intent = new Intent(getBaseContext(), CoffeeReceiver.class);
        PendingIntent pendingCoffeeIntent = PendingIntent.getBroadcast(getBaseContext(), 2, coffee_intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Globals.alarm.getTimeInMillis(), pendingCoffeeIntent);

        Intent alarm_intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(getBaseContext(), 1, alarm_intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, Globals.alarm.getTimeInMillis(), pendingAlarmIntent);
    }

    public void cancelAlarm(){
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(Globals.socket.getInputStream());
            String message = (String) ois.readObject();
            if(message.equals("CoffeeTime")){
                Globals.mediaPlayer.stop();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
