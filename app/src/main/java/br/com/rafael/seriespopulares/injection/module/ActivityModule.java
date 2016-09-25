package br.com.rafael.seriespopulares.injection.module;

import android.app.Activity;
import android.content.Context;

import br.com.rafael.seriespopulares.injection.ActivityContext;
import dagger.Module;
import dagger.Provides;

/**
 * Created by rafael on 9/24/16.
 **/
@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }
}
