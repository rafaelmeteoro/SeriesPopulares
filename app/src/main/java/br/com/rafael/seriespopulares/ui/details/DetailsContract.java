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
    }

    interface Presenter {
        void setInfoShow(Show show);
    }
}
