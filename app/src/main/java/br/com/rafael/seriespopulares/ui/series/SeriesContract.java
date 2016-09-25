package br.com.rafael.seriespopulares.ui.series;

import br.com.rafael.seriespopulares.ui.base.MvpView;

/**
 * Created by rafael on 9/25/16.
 **/

public interface SeriesContract {

    interface View extends MvpView {

    }

    interface Presenter {
        void getSeries();
    }
}
