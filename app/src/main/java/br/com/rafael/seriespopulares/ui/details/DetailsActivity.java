package br.com.rafael.seriespopulares.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import br.com.rafael.seriespopulares.ui.shows.ShowsActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rafael on 9/25/16.
 **/

public class DetailsActivity extends BaseMvpActivity implements DetailsContract.View {

    public static final String EXTRA_SHOW = "EXTRA_SHOW";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";

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

    @Bind(R.id.fab)
    protected FloatingActionButton mFab;

    private Show mShow;
    private int mPosition;

    @Inject
    protected DetailsPresenter mPresenter;

    public static Intent getStartIntent(Context context, Show show, int position) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_SHOW, show);
        intent.putExtra(EXTRA_POSITION, position);
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
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareShow(mShow);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareShow(Show show) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, show.getTextShare());
        startActivity(shareIntent);
    }

    @OnClick(R.id.error_view)
    public void onReloadClick() {
        setupViews();
        mPresenter.getSeasons(mShow);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        mPresenter.favoriteShow(mShow);
    }

    @Override
    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void setFavorite(boolean isFavorite) {
        mFab.setImageResource(isFavorite ? R.drawable.ic_heart : R.drawable.ic_heart_outline);
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

    @Override
    public void showMessage(int resId) {
        Snackbar snackbar = Snackbar.make(mContentView, resId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void updateShow(Show show) {
        Intent intent = new Intent(ShowsActivity.UPDATE_SHOW_INTENT_FILTER);
        intent.putExtra(EXTRA_SHOW, show);
        intent.putExtra(EXTRA_POSITION, mPosition);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
