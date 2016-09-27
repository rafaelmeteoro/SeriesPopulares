package br.com.rafael.seriespopulares.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.rafael.seriespopulares.data.local.ShowFavoriteDao;
import br.com.rafael.seriespopulares.data.model.Episode;
import br.com.rafael.seriespopulares.data.model.Season;
import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.data.operator.WorkerOperator;
import br.com.rafael.seriespopulares.data.remote.ApiProvider;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by rafael on 9/25/16.
 **/
@Singleton
public class DataManager {

    private final ApiProvider mApiProvider;
    private final ShowFavoriteDao mShowFavoriteDao;

    @Inject
    public DataManager(ApiProvider apiProvider, ShowFavoriteDao showFavoriteDao) {
        mApiProvider = apiProvider;
        mShowFavoriteDao = showFavoriteDao;
    }

    public Observable<List<Show>> getShows(int page, int limit, String extended) {
        return mApiProvider
                .getPopularShowsService()
                .getShows(page, limit, extended)
                .map(new Func1<List<Show>, List<Show>>() {
                    @Override
                    public List<Show> call(List<Show> shows) {
                        if (shows != null) {
                            for (Show show : shows) {
                                show.setFavorite(mShowFavoriteDao.isFavorited(show));
                            }
                        }
                        return shows;
                    }
                })
                .compose(new WorkerOperator<List<Show>>());
    }

    public Observable<String> getSeasons(int id, String extended) {
        return mApiProvider
                .getPopularShowsService()
                .getSeasons(id, extended)
                .map(new Func1<List<Season>, String>() {
                    @Override
                    public String call(List<Season> seasons) {
                        if (seasons == null || seasons.isEmpty()) {
                            return null;
                        } else {
                            return formatSeasons(seasons);
                        }
                    }
                })
                .compose(new WorkerOperator<String>());
    }

    private String formatSeasons(List<Season> seasons) {
        StringBuilder builder = new StringBuilder();
        for (Season season : seasons) {
            builder.append("Temporada: ").append(season.getNumber()).append("\n\n");

            for (Episode episode : season.getEpisodes()) {
                if (episode.getTitle() != null) {
                    builder.append("Epi ").append(episode.getNumber() < 10 ? ("0" + episode.getNumber()) : episode.getNumber())
                            .append(" - ").append(episode.getTitle()).append("\n");
                }
            }

            builder.append("\n");
        }
        return builder.toString();
    }

    public Observable<Show> favoriteOrUnfavoriteShow(Show show) {
        if (show.isFavorite()) {
            return unfavoriteShow(show)
                    .compose(new WorkerOperator<Show>());
        } else {
            return favoriteShow(show)
                    .compose(new WorkerOperator<Show>());
        }
    }

    private Observable<Show> unfavoriteShow(final Show show) {
        return Observable.just(mShowFavoriteDao.deleteFavorit(show))
                .map(new Func1<Boolean, Show>() {
                    @Override
                    public Show call(Boolean value) {
                        show.setFavorite(!value);
                        return show;
                    }
                });
    }

    private Observable<Show> favoriteShow(final Show show) {
        return Observable.just(mShowFavoriteDao.saveShowFavorite(show))
                .map(new Func1<Boolean, Show>() {
                    @Override
                    public Show call(Boolean value) {
                        show.setFavorite(value);
                        return show;
                    }
                });
    }
}
