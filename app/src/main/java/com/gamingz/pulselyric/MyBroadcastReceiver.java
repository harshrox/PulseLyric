package com.gamingz.pulselyric;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        mp=MediaPlayer.create(context, R.raw.reminder_tone);
        mp.start();
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();

    }
}
