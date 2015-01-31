package com.codecoe.coffeetime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Presents a Date Picker fragment for setting the alarm date
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar current = Calendar.getInstance();

        Calendar cal = AlarmView.alarm;
        System.out.println(AlarmView.alarm.getTime());
        cal.set(year, month, day);

        if(cal.compareTo(current) <= 0){
            //The set Date/Time already passed
            Toast.makeText(view.getContext(),
                    "Invalid Date/Time",
                    Toast.LENGTH_LONG).show();
        }else {
            AlarmView.alarm = cal;
        }
    }
}
