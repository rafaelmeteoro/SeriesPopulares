package br.com.rafael.seriespopulares.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import br.com.rafael.seriespopulares.SeriesPopularesApplication;
import br.com.rafael.seriespopulares.injection.ApplicationContext;
import br.com.rafael.seriespopulares.injection.module.ApplicationModule;
import br.com.rafael.seriespopulares.injection.module.NetworkModule;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by rafael on 9/25/16.
 **/
@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class
})
public interface ApplicationComponent {

    void inject(SeriesPopularesApplication application);

    @ApplicationContext
    Context context();

    Application application();

    Retrofit retrofit();
}
