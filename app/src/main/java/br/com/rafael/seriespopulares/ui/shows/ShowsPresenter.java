package br.com.rafael.seriespopulares.ui.shows;

import java.util.List;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.data.DataManager;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.ui.base.BaseRxPresenter;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by rafael on 9/25/16.
 **/

public class ShowsPresenter extends BaseRxPresenter<ShowsContract.View> implements ShowsContract.Presenter {

    private static final int LIMIT = 30;
    private static final String EXTENDED = "images,full";

    protected DataManager mDataManager;

    @Inject
    public ShowsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getShows(final int page) {
        checkViewAttached();
        getMvpView().showProgress();

        unsubscribe();
        mSubscription = mDataManager.getShows(page, LIMIT, EXTENDED)
                .subscribe(new Subscriber<List<Show>>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Erro ao carregar lista");
                        getMvpView().hideProgress();
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Show> shows) {
                        if (shows != null && shows.isEmpty() && page == ShowsActivity.FIRST_PAGE) {
                            getMvpView().showEmpty();
                        } else if (shows != null && !shows.isEmpty() && page == ShowsActivity.FIRST_PAGE) {
                            getMvpView().setData(shows);
                        } else {
                            getMvpView().addData(shows);
                        }
                    }
                });
    }

    @Override
    public void favoriteShow(Show show) {
        checkViewAttached();

        unsubscribe();
        mSubscription = mDataManager.saveShowFavorite(show)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String b = "";
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        String b = "";
                    }
                });
    }
}
