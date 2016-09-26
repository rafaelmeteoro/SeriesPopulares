package br.com.rafael.seriespopulares.ui.details;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.data.DataManager;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.ui.base.BaseRxPresenter;

/**
 * Created by rafael on 9/25/16.
 **/

public class DetailsPresenter extends BaseRxPresenter<DetailsContract.View> implements DetailsContract.Presenter {

    protected DataManager mDataManager;

    @Inject
    public DetailsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void setInfoShow(Show show) {
        checkViewAttached();
        getMvpView().setTitle(show.getTitle());
        getMvpView().setBanner(show.getImages().getBanner().getFull());
        getMvpView().setYear(show.getYear());
        getMvpView().setRating(show.getRating());
        getMvpView().setSinopse(show.getOverview());
        getMvpView().setGenres(show.getGenres());
    }
}
