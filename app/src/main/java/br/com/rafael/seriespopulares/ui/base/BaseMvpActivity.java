package br.com.rafael.seriespopulares.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.com.rafael.seriespopulares.SeriesPopularesApplication;
import br.com.rafael.seriespopulares.injection.component.ActivityComponent;
import br.com.rafael.seriespopulares.injection.component.DaggerActivityComponent;
import br.com.rafael.seriespopulares.injection.module.ActivityModule;

/**
 * Created by rafael on 9/25/16.
 **/

public abstract class BaseMvpActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
    }

    private void injectDependencies() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(SeriesPopularesApplication.get(this).getComponent())
                    .build();
        }
        inject(mActivityComponent);
    }

    protected abstract void inject(ActivityComponent activityComponent);
}
