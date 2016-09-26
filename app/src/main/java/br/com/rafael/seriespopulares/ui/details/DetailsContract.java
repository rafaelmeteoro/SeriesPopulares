package br.com.rafael.seriespopulares.ui.details;

import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.ui.base.MvpView;

/**
 * Created by rafael on 9/25/16.
 **/

public interface DetailsContract {

    interface View extends MvpView {
        void setTitle(String title);

        void setBanner(String banner);

        void setYear(int year);

        void setRating(double rating);

        void setSinopse(String sinopse);

        void setGenres(String genres);

        void setEpisodes(String episodes);

        void showProgress();

        void hideprogress();

        void showEmpty();

        void showError();
    }

    interface Presenter {
        void setInfoShow(Show show);

        void getSeasons(Show show);
    }
}
