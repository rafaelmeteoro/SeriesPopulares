package br.com.rafael.seriespopulares.ui.series;

import java.util.List;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.data.DataManager;
import br.com.rafael.seriespopulares.data.model.Serie;
import br.com.rafael.seriespopulares.ui.base.BaseRxPresenter;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by rafael on 9/25/16.
 **/

public class SeriesPresenter extends BaseRxPresenter<SeriesContract.View> implements SeriesContract.Presenter {

    protected DataManager mDataManager;

    @Inject
    public SeriesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getSeries() {
        checkViewAttached();

        unsubscribe();
        mSubscription = mDataManager.getSeries()
                .subscribe(new Subscriber<List<Serie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Erro ao carregar lista");
                    }

                    @Override
                    public void onNext(List<Serie> series) {
                        Timber.d("Series", series);
                    }
                });
    }
}
