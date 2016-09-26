package br.com.rafael.seriespopulares.data.remote;

import android.app.Application;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;

/**
 * Created by rafael on 9/25/16.
 **/
@Singleton
public class ApiProvider {

    private final Retrofit mRetrofit;
    private final Application mApplication;

    @Inject
    public ApiProvider(Retrofit retrofit, Application application) {
        mRetrofit = retrofit;
        mApplication = application;
    }

    public PopularShowsService getPopularShowsService() {
        return new MockPopularShowsService(mApplication.getApplicationContext());
    }
}
