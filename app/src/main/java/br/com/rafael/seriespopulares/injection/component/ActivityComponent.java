package br.com.rafael.seriespopulares.injection.component;

import android.content.Context;

import br.com.rafael.seriespopulares.injection.ActivityContext;
import br.com.rafael.seriespopulares.injection.PerActivity;
import br.com.rafael.seriespopulares.injection.module.ActivityModule;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by rafael on 9/25/16.
 **/
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ActivityContext
    Context context();

    Retrofit retrofit();
}
