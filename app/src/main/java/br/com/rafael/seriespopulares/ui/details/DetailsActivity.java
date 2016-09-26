package br.com.rafael.seriespopulares.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.R;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.injection.component.ActivityComponent;
import br.com.rafael.seriespopulares.ui.base.BaseMvpActivity;
import butterknife.ButterKnife;

/**
 * Created by rafael on 9/25/16.
 **/

public class DetailsActivity extends BaseMvpActivity implements DetailsContract.View {

    private static final String EXTRA_SHOW = "EXTRA_SHOW";

    @Inject
    protected DetailsPresenter mPresenter;

    public static Intent getStartIntent(Context context, Show show) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_SHOW, show);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
