package br.com.rafael.seriespopulares.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.data.operator.WorkerOperator;
import br.com.rafael.seriespopulares.data.remote.ApiProvider;
import rx.Observable;

/**
 * Created by rafael on 9/25/16.
 **/
@Singleton
public class DataManager {

    private final ApiProvider mApiProvider;

    @Inject
    public DataManager(ApiProvider apiProvider) {
        mApiProvider = apiProvider;
    }

    public Observable<List<Show>> getShows() {
        return mApiProvider
                .getPopularShowsService()
                .getShows(1, 10, "images,full")
                .compose(new WorkerOperator<List<Show>>());
    }
}
