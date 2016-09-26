package br.com.rafael.seriespopulares.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.R;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.injection.component.ActivityComponent;
import br.com.rafael.seriespopulares.ui.base.BaseMvpActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rafael on 9/25/16.
 **/

public class DetailsActivity extends BaseMvpActivity implements DetailsContract.View {

    private static final String EXTRA_SHOW = "EXTRA_SHOW";

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.iv_banner)
    protected ImageView ivBanner;

    @Bind(R.id.tv_year)
    protected TextView tvYear;

    @Bind(R.id.tv_rating)
    protected TextView tvRating;

    @Bind(R.id.tv_sinopse)
    protected TextView tvSinopse;

    @Bind(R.id.tv_generos)
    protected TextView tvGeneros;

    @Bind(R.id.tv_episodes)
    protected TextView tvEpisodes;

    private Show mShow;

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

        setSupportActionBar(mToolbar);

        mShow = getIntent().getParcelableExtra(EXTRA_SHOW);
        mPresenter.setInfoShow(mShow);
        mPresenter.getSeasons(mShow);
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

    @Override
    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void setBanner(String banner) {
        Picasso.with(this)
                .load(banner)
                .into(ivBanner);
    }

    @Override
    public void setYear(int year) {
        tvYear.setText(getString(R.string.layout_item_show_year_format, year));
    }

    @Override
    public void setRating(double rating) {
        tvRating.setText(getString(R.string.layout_item_show_rating_format, rating));
    }

    @Override
    public void setSinopse(String sinopse) {
        tvSinopse.setText(sinopse);
    }

    @Override
    public void setGenres(String genres) {
        tvGeneros.setText(genres);
    }

    @Override
    public void setEpisodes(String episodes) {
        tvEpisodes.setText(episodes);
    }
}
