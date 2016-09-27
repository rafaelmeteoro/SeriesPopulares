package br.com.rafael.seriespopulares.ui.shows;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.rafael.seriespopulares.R;
import br.com.rafael.seriespopulares.data.DataManager;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.test.common.TestDataFactory;
import br.com.rafael.seriespopulares.util.RxSchedulersOverrideRule;
import rx.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rafael on 9/27/16.
 **/
@RunWith(MockitoJUnitRunner.class)
public class ShowsPresenterTest {

    @Mock
    ShowsContract.View mMockView;

    @Mock
    DataManager mMockDataManager;

    private ShowsPresenter mPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setup() {
        mPresenter = new ShowsPresenter(mMockDataManager);
        mPresenter.attachView(mMockView);
    }

    @After
    public void detachView() {
        mPresenter.detachView();
    }

    @Test
    public void getShowsFirsPageSuccessful() {
        List<Show> shows = TestDataFactory.newListShow(10);
        stubDataManagerGetShows(Observable.just(shows));

        mPresenter.getShows(1);
        verify(mMockView).showProgress();
        verify(mMockView).setData(shows);
        verify(mMockView).hideProgress();
    }

    @Test
    public void getShowsAnyPageSuccessful() {
        List<Show> shows = TestDataFactory.newListShow(10);
        stubDataManagerGetShows(Observable.just(shows));

        mPresenter.getShows(2);
        verify(mMockView).showProgress();
        verify(mMockView).addData(shows);
        verify(mMockView).hideProgress();
    }

    @Test
    public void getShowsEmptySuccessful() {
        List<Show> shows = new ArrayList<>();
        stubDataManagerGetShows(Observable.just(shows));

        mPresenter.getShows(1);
        verify(mMockView).showProgress();
        verify(mMockView).showEmpty();
        verify(mMockView).hideProgress();
    }

    @Test
    public void getShowsFailure() {
        stubDataManagerGetShows(Observable.<List<Show>>error(new RuntimeException()));

        mPresenter.getShows(1);
        verify(mMockView).showProgress();
        verify(mMockView).hideProgress();
        verify(mMockView).showError();
    }

    @Test
    public void favoriteShow() {
        Show show = TestDataFactory.newShow();
        show.setFavorite(true);
        int position = 0;
        stubDataManagerFavoriteShow(Observable.just(show));

        mPresenter.favoriteShow(show, position);
        verify(mMockView).updateData(show, position);
        verify(mMockView).showMessage(R.string.favorite_shows_success);
    }

    @Test
    public void unfavoriteShow() {
        Show show = TestDataFactory.newShow();
        show.setFavorite(false);
        int position = 0;
        stubDataManagerFavoriteShow(Observable.just(show));

        mPresenter.favoriteShow(show, position);
        verify(mMockView).updateData(show, position);
    }

    @Test
    public void favoriteShowFailure() {
        Show show = TestDataFactory.newShow();
        int position = 0;
        stubDataManagerFavoriteShow(Observable.<Show>error(new RuntimeException()));

        mPresenter.favoriteShow(show, position);
        verify(mMockView).showMessage(R.string.favorite_shows_unfavorite_failed);
    }

    private void stubDataManagerGetShows(Observable<List<Show>> observable) {
        when(mMockDataManager.getShows(anyInt(), anyInt(), anyString())).thenReturn(observable);
    }

    private void stubDataManagerFavoriteShow(Observable<Show> observable) {
        when(mMockDataManager.favoriteOrUnfavoriteShow((Show) anyObject())).thenReturn(observable);
    }
}
