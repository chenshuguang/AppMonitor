package dev.xesam.android.toolbox.appmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

/**
 * Created by xesamguo@gmail.com on 16-3-8.
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.e("onReceive", action);
    }

    public void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            filter.addAction(Intent.ACTION_USER_BACKGROUND);
            filter.addAction(Intent.ACTION_USER_FOREGROUND);
            filter.addAction(Intent.ACTION_USER_INITIALIZE);
        }
        context.registerReceiver(this, filter);
    }

    public void unRegister(Context context) {
        context.unregisterReceiver(this);
    }
}
