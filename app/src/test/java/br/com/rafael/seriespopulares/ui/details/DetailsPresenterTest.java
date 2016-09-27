package br.com.rafael.seriespopulares.ui.details;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.StringJoiner;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.rafael.seriespopulares.R;
import br.com.rafael.seriespopulares.data.DataManager;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.test.common.TestDataFactory;
import br.com.rafael.seriespopulares.util.RxSchedulersOverrideRule;
import rx.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rafael on 9/27/16.
 **/
@RunWith(MockitoJUnitRunner.class)
public class DetailsPresenterTest {

    @Mock
    DetailsContract.View mMockView;

    @Mock
    DataManager mMockDataManager;

    private DetailsPresenter mPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setup() {
        mPresenter = new DetailsPresenter(mMockDataManager);
        mPresenter.attachView(mMockView);
    }

    @After
    public void detachView() {
        mPresenter.detachView();
    }

    @Test
    public void setInfoSuccesful() {
        Show show = TestDataFactory.newShow();
        mPresenter.setInfoShow(show);
        verify(mMockView).setTitle(show.getTitle());
        verify(mMockView).setFavorite(show.isFavorite());
        verify(mMockView).setBanner(show.getImages().getBanner().getFull());
        verify(mMockView).setYear(show.getYear());
        verify(mMockView).setRating(show.getRating());
        verify(mMockView).setSinopse(show.getOverview());
        verify(mMockView).setGenres(show.getGenres());
    }

    @Test
    public void getSeasonsSuccessful() {
        String seasons = TestDataFactory.randomString();
        stubDataManagerGetSeasons(Observable.just(seasons));

        mPresenter.getSeasons(TestDataFactory.newShow());
        verify(mMockView).showProgress();
        verify(mMockView).setEpisodes(seasons);
        verify(mMockView).hideprogress();
    }

    @Test
    public void getSeasonsEmptySuccessful() {
        String season = null;
        stubDataManagerGetSeasons(Observable.just(season));

        mPresenter.getSeasons(TestDataFactory.newShow());
        verify(mMockView).showProgress();
        verify(mMockView).showEmpty();
        verify(mMockView).hideprogress();
    }

    @Test
    public void getSeasonsFailure() {
        stubDataManagerGetSeasons(Observable.<String>error(new RuntimeException()));

        mPresenter.getSeasons(TestDataFactory.newShow());
        verify(mMockView).showProgress();
        verify(mMockView).hideprogress();
        verify(mMockView).showError();
    }

    @Test
    public void favoriteShowSuccessful() {
        Show show = TestDataFactory.newShow();
        show.setFavorite(true);
        stubDataManagerFavoriteShow(Observable.just(show));

        mPresenter.favoriteShow(show);
        verify(mMockView).setFavorite(show.isFavorite());
        verify(mMockView).updateShow(show);
        verify(mMockView).showMessage(R.string.favorite_shows_success);
    }

    @Test
    public void unfavoriteShowSuccessful() {
        Show show = TestDataFactory.newShow();
        show.setFavorite(false);
        stubDataManagerFavoriteShow(Observable.just(show));

        mPresenter.favoriteShow(show);
        verify(mMockView).setFavorite(show.isFavorite());
        verify(mMockView).updateShow(show);
    }

    @Test
    public void favoriteShowFailure() {
        stubDataManagerFavoriteShow(Observable.<Show>error(new RuntimeException()));

        mPresenter.favoriteShow(TestDataFactory.newShow());
        verify(mMockView).showMessage(R.string.favorite_shows_unfavorite_failed);
    }

    private void stubDataManagerGetSeasons(Observable<String> observable) {
        when(mMockDataManager.getSeasons(anyInt(), anyString())).thenReturn(observable);
    }

    private void stubDataManagerFavoriteShow(Observable<Show> observable) {
        when(mMockDataManager.favoriteOrUnfavoriteShow((Show) anyObject())).thenReturn(observable);
    }
}
