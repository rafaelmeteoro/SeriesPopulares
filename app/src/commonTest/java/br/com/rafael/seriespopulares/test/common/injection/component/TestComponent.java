package br.com.rafael.seriespopulares.test.common.injection.component;

import javax.inject.Singleton;

import br.com.rafael.seriespopulares.injection.component.ApplicationComponent;
import br.com.rafael.seriespopulares.injection.module.NetworkModule;
import br.com.rafael.seriespopulares.test.common.injection.module.ApplicationTestModule;
import dagger.Component;

/**
 * Created by rafael on 9/26/16.
 **/
@Singleton
@Component(modules = {
        ApplicationTestModule.class,
        NetworkModule.class
})
public interface TestComponent extends ApplicationComponent {
}
