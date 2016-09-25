package br.com.rafael.seriespopulares.data.remote;

import java.util.List;

import br.com.rafael.seriespopulares.data.model.Serie;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rafael on 9/25/16.
 **/

public interface SeriesPopularesService {

    @GET("shows/popular")
    Observable<List<Serie>> getSeries(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("extended") String extended
    );
}
