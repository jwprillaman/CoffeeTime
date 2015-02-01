package com.codecoe.coffeetime;

/**
 * This class receives the coffee intent and performs the 'alarm' action to signal the
 * coffee server.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CoffeeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Coffee's 'a brewin'!", Toast.LENGTH_LONG).show();

        new Thread(new ClientThread()).start();
    }

}
