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

    protected DataManager mDataManager;

    @Inject
    public ShowsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getShows() {
        checkViewAttached();

        unsubscribe();
        mSubscription = mDataManager.getShows()
                .subscribe(new Subscriber<List<Show>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Erro ao carregar lista");
                    }

                    @Override
                    public void onNext(List<Show> shows) {
                        Timber.d("Shows", shows);
                    }
                });
    }
}
