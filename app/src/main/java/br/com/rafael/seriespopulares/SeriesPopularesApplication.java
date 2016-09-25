package br.com.rafael.seriespopulares;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by rafael on 9/24/16.
 **/

public class SeriesPopularesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }
}
