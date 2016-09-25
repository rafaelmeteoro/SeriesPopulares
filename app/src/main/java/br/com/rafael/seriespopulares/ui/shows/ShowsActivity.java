package br.com.rafael.seriespopulares.ui.shows;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.R;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.injection.component.ActivityComponent;
import br.com.rafael.seriespopulares.ui.base.BaseMvpActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by rafael on 9/25/16.
 **/

public class ShowsActivity extends BaseMvpActivity implements ShowsContract.View {

    // Quantidade de colunas do grid
    private static final int COLUMN_TWO = 2;
    private static final int COLUMN_THREE = 3;
    private static final int COLUMN_FOUR = 4;
    private static final int COLUMN_FIVE = 5;

    @Inject
    protected ShowsPresenter mPresenter;

    @Inject
    protected ShowsAdapter mAdapter;

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.content_view)
    protected SwipeRefreshLayout mContentView;

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.loading_view)
    protected ProgressBar mLoadingView;

    @Bind(R.id.error_view)
    protected TextView mErrorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);
        ButterKnife.bind(this);
        mPresenter.attachView(this);

        setSupportActionBar(mToolbar);
        setupViews();

        mPresenter.getShows();
    }

    /**
     * Determina a quantidade colunas da grid de um dispositivo
     * Se for tablet e estiver em landscape - 5
     * Se for tablet e estiver em portrait - 4
     * Se for smartphone e estiver em landscape - 3
     * Se for smartphone e estiver em portrait - 2
     * */
    private int numColumn() {

        int numColumn;

        boolean isTablet = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        int orientation = getResources().getConfiguration().orientation;

        if (isTablet) {
            numColumn = orientation == Configuration.ORIENTATION_LANDSCAPE ? COLUMN_FIVE : COLUMN_FOUR;
        } else {
            numColumn = orientation == Configuration.ORIENTATION_LANDSCAPE ? COLUMN_THREE : COLUMN_TWO;
        }

        return numColumn;
    }

    private void setupViews() {
        mContentView.setEnabled(false);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numColumn()));
        mRecyclerView.setAdapter(mAdapter);
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
    public void showProgress() {
        if (mRecyclerView.getVisibility() == View.VISIBLE && mAdapter.getItemCount() > 0) {
            mContentView.setRefreshing(true);
        } else {
            mLoadingView.setVisibility(View.VISIBLE);
        }
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mContentView.setRefreshing(false);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showShows(List<Show> shows) {
        mAdapter.setList(shows);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mErrorView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_empty_glass_gray, 0, 0);
        mErrorView.setText(R.string.view_empty_text);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mErrorView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_sentiment_very_dissatisfied_gray, 0, 0);
        mErrorView.setText(R.string.view_erro_text);
        mErrorView.setVisibility(View.VISIBLE);
    }
}