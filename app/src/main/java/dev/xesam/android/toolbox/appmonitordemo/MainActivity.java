package dev.xesam.android.toolbox.appmonitordemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.xesam.android.toolbox.appmonitor.ProcessMonitor;
import dev.xesam.android.toolbox.appmonitor.ScreenBroadcastReceiver;

public class MainActivity extends AppCompatActivity {

    ScreenBroadcastReceiver screenBroadcastReceiver = new ScreenBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenBroadcastReceiver.register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ProcessMonitor.onStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProcessMonitor.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ProcessMonitor.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ProcessMonitor.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        screenBroadcastReceiver.unRegister(this);
    }
}
