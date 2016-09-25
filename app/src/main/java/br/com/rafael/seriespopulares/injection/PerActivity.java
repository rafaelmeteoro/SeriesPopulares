package br.com.rafael.seriespopulares.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by rafael on 9/24/16.
 **/
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
