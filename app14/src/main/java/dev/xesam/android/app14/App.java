package dev.xesam.android.app14;

import android.app.Application;

import dev.xesam.android.toolbox.appmonitor.ProcessMonitor;

/**
 * Created by xesamguo@gmail.com on 16-3-7.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ProcessMonitor.attach(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ProcessMonitor.detach(this);
    }
}
