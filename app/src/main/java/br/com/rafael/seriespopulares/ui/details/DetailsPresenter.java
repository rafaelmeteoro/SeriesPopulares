package br.com.rafael.seriespopulares.ui.details;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.R;
import br.com.rafael.seriespopulares.data.DataManager;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.ui.base.BaseRxPresenter;
import rx.Subscriber;

/**
 * Created by rafael on 9/25/16.
 **/

public class DetailsPresenter extends BaseRxPresenter<DetailsContract.View> implements DetailsContract.Presenter {

    private static final String EXTENDED = "episodes";

    protected DataManager mDataManager;

    @Inject
    public DetailsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void setInfoShow(Show show) {
        checkViewAttached();
        getMvpView().setTitle(show.getTitle());
        getMvpView().setFavorite(show.isFavorite());
        getMvpView().setBanner(show.getImages().getBanner().getFull());
        getMvpView().setYear(show.getYear());
        getMvpView().setRating(show.getRating());
        getMvpView().setSinopse(show.getOverview());
        getMvpView().setGenres(show.getGenres());
    }

    @Override
    public void getSeasons(Show show) {
        checkViewAttached();
        getMvpView().showProgress();
        int id = show.getIds().getTrakt();

        unsubscribe();
        mSubscription = mDataManager.getSeasons(id, EXTENDED)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().hideprogress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideprogress();
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(String value) {
                        if (value == null) {
                            getMvpView().showEmpty();
                        } else {
                            getMvpView().setEpisodes(value);
                        }
                    }
                });
    }

    @Override
    public void favoriteShow(Show show) {
        checkViewAttached();

        unsubscribe();
        mSubscription = mDataManager.favoriteOrUnfavoriteShow(show)
                .subscribe(new Subscriber<Show>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showMessage(R.string.favorite_shows_unfavorite_failed);
                    }

                    @Override
                    public void onNext(Show show) {
                        getMvpView().setFavorite(show.isFavorite());
                        getMvpView().updateShow(show);
                        if (show.isFavorite()) {
                            getMvpView().showMessage(R.string.favorite_shows_success);
                        }
                    }
                });
    }
}
