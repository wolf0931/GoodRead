package com.wph.goodread.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by wph on 2017/2/10.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // the 'theme' has two value, 0 and 1
        // 0 --> day theme, 1 --> night theme

        if (getSharedPreferences("user_settings", MODE_PRIVATE).getInt("theme", 0) == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
