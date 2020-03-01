package com.aim.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;

import android.os.Bundle;

import com.aim.myapplication.BuildConfig;
import com.aim.myapplication.R;
import com.aim.myapplication.ui.fragments.MainFragment;
import com.aim.myapplication.utils.ReleaseTreeLogger;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTreeLogger());
        }
        Timber.e("Error Message");
        Timber.d("Debug Message");

        Timber.tag("Some Different tag").e("And error message");
    }
}
