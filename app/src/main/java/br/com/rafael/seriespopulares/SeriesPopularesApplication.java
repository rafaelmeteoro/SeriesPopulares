package br.com.rafael.seriespopulares;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import br.com.rafael.seriespopulares.injection.component.ApplicationComponent;
import br.com.rafael.seriespopulares.injection.component.DaggerApplicationComponent;
import br.com.rafael.seriespopulares.injection.module.ApplicationModule;
import br.com.rafael.seriespopulares.injection.module.NetworkModule;
import timber.log.Timber;

/**
 * Created by rafael on 9/24/16.
 **/

public class SeriesPopularesApplication extends Application {

    protected ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        initDagger();
    }

    private void initDagger() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(BuildConfig.API_URL))
                .build();

        mApplicationComponent.inject(this);
    }

    public static SeriesPopularesApplication get(Context context) {
        return (SeriesPopularesApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    @VisibleForTesting
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
