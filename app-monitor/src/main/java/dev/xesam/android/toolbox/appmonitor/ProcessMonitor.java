package dev.xesam.android.toolbox.appmonitor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;


/**
 * 前后台切换监听
 * <p/>
 * Created by xesamguo@gmail.com on 16-3-7.
 */
public class ProcessMonitor {

    private static final String TAG = "ProcessMonitor";

    private static int compatStartCount = 0;

    private static boolean isCompatForeground = true;
    private static boolean isCompatLockStop = false;

    private static boolean isStandard() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isInteractive(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return pm.isInteractive();
        } else {
            return pm.isScreenOn();
        }
    }

    public static void onStart(Activity activity) {
        compatStartCount++;
        if (!isCompatForeground) {
            isCompatForeground = true;
            onBackgroundToForeground(activity);
        }
    }

    public static void onResume(Activity activity) {
        if (isStandard()) {
            //np
        } else {
            if (isCompatLockStop) {
                isCompatLockStop = false;
                onStart(activity);
            }
        }
    }

    public static void onPause(Activity activity) {
        if (isStandard()) {
            //np
        } else {
            if (!isInteractive(activity)) { //锁屏触发
                isCompatLockStop = true;
                onStop(activity);
            }
        }
    }

    public static void onStop(Activity activity) {
        compatStartCount--;
        if (compatStartCount == 0) {
            isCompatForeground = false;
            onForegroundToBackground(activity);
        }
    }

    public static void onForegroundToBackground(Activity activity) {
        Log.e(TAG, activity.getClass().getSimpleName() + ":前台->后台");

    }

    public static void onBackgroundToForeground(Activity activity) {
        Log.e(TAG, activity.getClass().getSimpleName() + ":后台->前台");
    }

    private static Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;

    public static void attach(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (activityLifecycleCallbacks == null) {
                activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {

                    ScreenBroadcastReceiver screenBroadcastReceiver = new ScreenBroadcastReceiver();

                    private int mStartCount = 0;

                    private boolean mIsForeground = true;

                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        screenBroadcastReceiver.register(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                        mStartCount++;

                        if (!mIsForeground) {
                            mIsForeground = true;
                            onBackgroundToForeground(activity);
                        }
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                        mStartCount--;
                        if (mStartCount == 0) {
                            mIsForeground = false;
                            onForegroundToBackground(activity);
                        }
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                        screenBroadcastReceiver.unRegister(activity);
                    }
                };
            }
            application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        } else {
            //np
        }
    }

    public static void detach(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (activityLifecycleCallbacks != null) {
                application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
            }
        }
    }
}
