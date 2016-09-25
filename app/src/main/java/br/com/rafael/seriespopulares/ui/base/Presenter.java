package br.com.rafael.seriespopulares.ui.base;

/**
 * Created by rafael on 9/25/16.
 **/

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
