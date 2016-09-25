package br.com.rafael.seriespopulares.ui.series;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.data.DataManager;
import br.com.rafael.seriespopulares.ui.base.BaseRxPresenter;

/**
 * Created by rafael on 9/25/16.
 **/

public class SeriesPresenter extends BaseRxPresenter<SeriesContract.View> implements SeriesContract.Presenter {

    protected DataManager mDataManager;

    @Inject
    public SeriesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
}
