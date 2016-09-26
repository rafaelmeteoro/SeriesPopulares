package br.com.rafael.seriespopulares.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.rafael.seriespopulares.data.model.Season;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.helper.AssetsHelper;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rafael on 9/25/16.
 **/

public class MockPopularShowsService implements PopularShowsService {

    private Context mContext;

    public MockPopularShowsService(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<Show>> getShows(@Query("page") int page, @Query("limit") int limit, @Query("extended") String extended) {
        String showsJson;
        List<Show> shows;
        try {
            // Envia um alista vazia se a page é 3
            if (page >= 3) {
                shows = new ArrayList<>();
                return Observable.just(shows);
            }

            showsJson = AssetsHelper.getStringFromAssets(mContext, "shows_success.json");
            Gson gson = new Gson();
            Type showsType = new TypeToken<List<Show>>() {
            }.getType();
            shows = gson.fromJson(showsJson, showsType);
            return Observable.just(shows);
        } catch (Exception e) {
            return Observable.error(e);
        }
    }

    @Override
    public Observable<List<Season>> getSeasons(@Path("id") int id, @Query("extended") String extended) {
        String seasonsJson;
        List<Season> seasons;
        try {
            seasonsJson = AssetsHelper.getStringFromAssets(mContext, "shows_episodes_success.json");
            Gson gson = new Gson();
            Type seasonsType = new TypeToken<List<Season>>() {
            }.getType();
            seasons = gson.fromJson(seasonsJson, seasonsType);
            return Observable.just(seasons);
        } catch (Exception e) {
            return Observable.error(e);
        }
    }
}
