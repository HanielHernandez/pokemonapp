package com.aim.myapplication.utils;

import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

public class ReleaseTreeLogger extends Timber.Tree {
    @Override
    protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
        if ((priority == Log.ERROR || priority == Log.WARN )&& t != null) {
            // TODO Implementar crashlitycs
            FirebaseCrashlytics.getInstance().recordException(t);
            FirebaseCrashlytics.getInstance().sendUnsentReports();
        }
    }
}
