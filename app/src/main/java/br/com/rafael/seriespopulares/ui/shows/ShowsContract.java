package br.com.rafael.seriespopulares.ui.shows;

import java.util.List;

import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.ui.base.MvpView;

/**
 * Created by rafael on 9/25/16.
 **/

public interface ShowsContract {

    interface View extends MvpView {
        void showProgress();

        void hideProgress();

        void showShows(List<Show> shows);

        void showEmpty();

        void showError();
    }

    interface Presenter {
        void getShows();
    }
}
