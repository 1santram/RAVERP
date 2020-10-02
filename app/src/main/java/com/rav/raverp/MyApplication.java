package com.rav.raverp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.gson.Gson;
import com.rav.raverp.data.local.prefs.PrefsHelper;
import com.rav.raverp.data.model.api.Login;
import com.rav.raverp.utils.AppConstants;
import com.rav.raverp.utils.JSONConverter;


public class MyApplication extends Application implements LifecycleObserver {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public static final String TAG = MyApplication.class.getSimpleName();
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

    public static boolean getAppOpenStatus() {


        return PrefsHelper.getBoolean(context, AppConstants.APP_FOREGROUNDED);
    }

    public static String getLoginId() {
        String loggedInUserDetails = PrefsHelper.getString(context, AppConstants.LOGIN);
        if (loggedInUserDetails != null) {
            Gson gson=new Gson();
            Login loggedInUser=gson.fromJson(loggedInUserDetails,Login.class);

                return loggedInUser.getStrLoginID();
        }
        return null;
    }
    public static Login getLoginModel() {
        String loggedInUserDetails = PrefsHelper.getString(context, AppConstants.LOGIN);
        if (loggedInUserDetails != null) {
            Gson gson=new Gson();
            Login loggedInUser=gson.fromJson(loggedInUserDetails,Login.class);

            return loggedInUser;
        }

        return null;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        // App in background
        PrefsHelper.putBoolean(context, AppConstants.APP_FOREGROUNDED, false);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        // App in foreground
        PrefsHelper.putBoolean(context, AppConstants.APP_FOREGROUNDED, true);
    }
}
