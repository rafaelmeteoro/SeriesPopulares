package br.com.rafael.seriespopulares.ui.series;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.R;
import br.com.rafael.seriespopulares.injection.component.ActivityComponent;
import br.com.rafael.seriespopulares.ui.base.BaseMvpActivity;

/**
 * Created by rafael on 9/25/16.
 **/

public class SeriesActivity extends BaseMvpActivity implements SeriesContract.View {

    @Inject
    protected SeriesPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter.attachView(this);

        mPresenter.getSeries();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
