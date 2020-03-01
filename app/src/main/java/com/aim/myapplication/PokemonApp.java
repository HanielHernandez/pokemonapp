package com.aim.myapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.aim.myapplication.utils.ReleaseTreeLogger;
import com.google.firebase.analytics.FirebaseAnalytics;

import timber.log.Timber;

public class PokemonApp extends Application {
    public static SharedPreferences preferences;
    @Override
    public void onCreate() {
        super.onCreate();
        // Logging configuration
        // Based on: https://itnext.io/timber-enhancing-your-logging-experience-330e8af97341
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTreeLogger());
        }
        Bundle bundle = new Bundle();

        preferences = getSharedPreferences( getPackageName() + "_preferences", MODE_PRIVATE);
    }


    @Override
    public void onTerminate() {
        Bundle bundle = new Bundle();
       /* bundle.putString(FirebaseAnalytics.Param.ITEM_ID, AnalitycsEvents.APP_CLOSED.toString());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, AnalitycsEvents.APP_CLOSED.toString());
        FirebaseAnalytics.getInstance(this).logEvent(AnalitycsEvents.APP_CLOSED.toString(), bundle);
        */
       Timber.d("App terminated!");
       super.onTerminate();

    }
}
