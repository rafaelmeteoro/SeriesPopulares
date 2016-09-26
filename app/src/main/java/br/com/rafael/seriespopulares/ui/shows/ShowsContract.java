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

        void setData(List<Show> shows);

        void addData(List<Show> shows);

        void updateData(Show show, int position);

        void showEmpty();

        void showError();

        void showMessage(int resId);
    }

    interface Presenter {
        void getShows(int page);

        void favoriteShow(Show show, int position);
    }
}
