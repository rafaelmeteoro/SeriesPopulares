package br.com.rafael.seriespopulares.runner;

import android.os.Bundle;
import android.support.test.espresso.Espresso;

import br.com.rafael.seriespopulares.util.RxIdlingExecutionHook;
import br.com.rafael.seriespopulares.util.RxIdlingResource;
import rx.plugins.RxJavaPlugins;

/**
 * Created by rafael on 9/27/16.
 *
 * Runner that registers a Espresso Idling resource that handles waiting for
 * RxJava Observables to finish.
 * WARNING - Using this runner will block the tests if the application uses long-lived hot
 * Observables such us event buses, etc.
 **/

public class RxAndroidJUnitRunner extends UnlockDeviceAndroidJUnitRunner {

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        RxIdlingResource rxIdlingResource = new RxIdlingResource();
        RxJavaPlugins.getInstance()
                .registerObservableExecutionHook(new RxIdlingExecutionHook(rxIdlingResource));
        Espresso.registerIdlingResources(rxIdlingResource);
    }
}
