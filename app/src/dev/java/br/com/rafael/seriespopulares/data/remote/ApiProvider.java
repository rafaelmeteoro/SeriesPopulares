package br.com.rafael.seriespopulares.data.remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;

/**
 * Created by rafael on 9/25/16.
 **/
@Singleton
public class ApiProvider {

    private final Retrofit mRetrofit;

    @Inject
    public ApiProvider(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public PopularShowsService getPopularShowsService() {
        return mRetrofit.create(PopularShowsService.class);
    }
}
