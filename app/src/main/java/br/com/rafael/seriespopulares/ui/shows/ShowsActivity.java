package br.com.rafael.seriespopulares.ui.shows;

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

/**
 * Created by rafael on 9/25/16.
 **/

public class ShowsActivity extends BaseMvpActivity implements ShowsContract.View {

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

    private int NUM_COLUMN = 2;

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

    private void setupViews() {
        mContentView.setEnabled(false);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUM_COLUMN));
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

    }

    @Override
    public void showError() {

    }
}
