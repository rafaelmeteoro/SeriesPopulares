package br.com.rafael.seriespopulares.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.R;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.injection.component.ActivityComponent;
import br.com.rafael.seriespopulares.ui.base.BaseMvpActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rafael on 9/25/16.
 **/

public class DetailsActivity extends BaseMvpActivity implements DetailsContract.View {

    private static final String EXTRA_SHOW = "EXTRA_SHOW";

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.iv_banner)
    protected ImageView mIvBanner;

    @Bind(R.id.tv_year)
    protected TextView mTvYear;

    @Bind(R.id.tv_rating)
    protected TextView mTvRating;

    @Bind(R.id.tv_sinopse)
    protected TextView mTvSinopse;

    @Bind(R.id.tv_generos)
    protected TextView mTvGeneros;

    @Bind(R.id.tv_episodes)
    protected TextView mTvEpisodes;

    @Bind(R.id.content_view)
    protected LinearLayout mContentView;

    @Bind(R.id.loading_view)
    protected ProgressBar mLoadingView;

    @Bind(R.id.error_view)
    protected TextView mErrorView;

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
        setupViews();

        mShow = getIntent().getParcelableExtra(EXTRA_SHOW);
        mPresenter.setInfoShow(mShow);
        mPresenter.getSeasons(mShow);
    }

    private void setupViews() {
        mContentView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
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

    @OnClick(R.id.error_view)
    public void onReloadClick() {
        setupViews();
        mPresenter.getSeasons(mShow);
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
                .into(mIvBanner);
    }

    @Override
    public void setYear(int year) {
        mTvYear.setText(getString(R.string.layout_item_show_year_format, year));
    }

    @Override
    public void setRating(double rating) {
        mTvRating.setText(getString(R.string.layout_item_show_rating_format, rating));
    }

    @Override
    public void setSinopse(String sinopse) {
        mTvSinopse.setText(sinopse);
    }

    @Override
    public void setGenres(String genres) {
        mTvGeneros.setText(genres);
    }

    @Override
    public void setEpisodes(String episodes) {
        mTvEpisodes.setText(episodes);
        mContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideprogress() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        mContentView.setVisibility(View.VISIBLE);
        mErrorView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_empty_glass_gray, 0, 0);
        mErrorView.setText(R.string.view_empty_text);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mContentView.setVisibility(View.VISIBLE);
        mErrorView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_sentiment_very_dissatisfied_gray, 0, 0);
        mErrorView.setText(R.string.view_erro_text);
        mErrorView.setVisibility(View.VISIBLE);
    }
}
