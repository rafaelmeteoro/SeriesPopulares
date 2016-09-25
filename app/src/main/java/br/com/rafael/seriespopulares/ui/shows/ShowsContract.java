package br.com.rafael.seriespopulares.ui.shows;

import br.com.rafael.seriespopulares.ui.base.MvpView;

/**
 * Created by rafael on 9/25/16.
 **/

public interface ShowsContract {

    interface View extends MvpView {

    }

    interface Presenter {
        void getShows();
    }
}
