package br.com.rafael.seriespopulares.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import br.com.rafael.seriespopulares.BuildConfig;
import br.com.rafael.seriespopulares.data.local.ShowFavoriteDao;
import br.com.rafael.seriespopulares.data.model.Season;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.data.remote.ApiProvider;
import br.com.rafael.seriespopulares.data.remote.PopularShowsService;
import br.com.rafael.seriespopulares.test.common.TestDataFactory;
import br.com.rafael.seriespopulares.util.RxSchedulersOverrideRule;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


/**
 * Created by rafael on 9/27/16.
 **/
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    ShowFavoriteDao mMockShowFavoriteDao;

    @Mock
    PopularShowsService mMockPopularShowsService;

    @InjectMocks
    ApiProvider mMockApiProvider = spy(new ApiProvider(mockRetrofit()));

    DataManager mDataManager;

    @Rule
    // Must be added to every test class that targets app code that use RxJava
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    private Retrofit mockRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Before
    public void setup() {
        mDataManager = new DataManager(mMockApiProvider, mMockShowFavoriteDao);
    }

    @Test
    public void getShowsComplete() {
        List<Show> shows = TestDataFactory.newListShow(10);
        stubPopularShowsServiceGetShows(Observable.just(shows));

        TestSubscriber<List<Show>> testSubscriber = new TestSubscriber<>();
        mDataManager.getShows(0, 0, "").subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(shows);
    }

    @Test
    public void getShowsError() {
        Throwable throwable = new RuntimeException();
        stubPopularShowsServiceGetShows(Observable.<List<Show>>error(throwable));

        TestSubscriber<List<Show>> testSubscriber = new TestSubscriber<>();
        mDataManager.getShows(0, 0, "").subscribe(testSubscriber);
        testSubscriber.assertNotCompleted();
        testSubscriber.assertError(throwable);
    }

    @Test
    public void getSeasonsComplete() {
        List<Season> seasons = TestDataFactory.newListSeason(10);
        stubPopularShowsServiceGetSeasons(Observable.just(seasons));

        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        mDataManager.getSeasons(0, "").subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(mDataManager.formatSeasons(seasons));
    }

    @Test
    public void getSeasonsError() {
        Throwable throwable = new RuntimeException();
        stubPopularShowsServiceGetSeasons(Observable.<List<Season>>error(throwable));

        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        mDataManager.getSeasons(0, "").subscribe(testSubscriber);
        testSubscriber.assertNotCompleted();
        testSubscriber.assertError(throwable);
    }

    @Test
    public void favoriteOrUnfavoriteComplete() {
        Show show = TestDataFactory.newShow();
        show.setFavorite(true);
        stubFavoriteDao();

        TestSubscriber<Show> testSubscriber = new TestSubscriber<>();
        mDataManager.favoriteOrUnfavoriteShow(show).subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(show);
    }

    private void stubPopularShowsServiceGetShows(Observable<List<Show>> observable) {
        when(mMockApiProvider.getPopularShowsService()).thenReturn(mMockPopularShowsService);
        when(mMockPopularShowsService.getShows(anyInt(), anyInt(), anyString())).thenReturn(observable);
    }

    private void stubPopularShowsServiceGetSeasons(Observable<List<Season>> observable) {
        when(mMockApiProvider.getPopularShowsService()).thenReturn(mMockPopularShowsService);
        when(mMockPopularShowsService.getSeasons(anyInt(), anyString())).thenReturn(observable);
    }

    private void stubFavoriteDao() {
        when(mMockShowFavoriteDao.deleteFavorit((Show) anyObject())).thenReturn(anyBoolean());
    }
}
