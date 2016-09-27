package br.com.rafael.seriespopulares.test.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import br.com.rafael.seriespopulares.data.model.Banner;
import br.com.rafael.seriespopulares.data.model.Episode;
import br.com.rafael.seriespopulares.data.model.Fanart;
import br.com.rafael.seriespopulares.data.model.Ids;
import br.com.rafael.seriespopulares.data.model.Images;
import br.com.rafael.seriespopulares.data.model.Season;
import br.com.rafael.seriespopulares.data.model.Show;

/**
 * Created by rafael on 9/26/16.
 **/

public class TestDataFactory {

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static int randomInt() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    public static Ids newIds() {
        Ids ids = new Ids();
        ids.setTrakt(randomInt());
        ids.setTvdb(randomInt());
        ids.setTmdb(randomInt());
        ids.setTvrage(randomInt());
        ids.setSlug(randomString());
        ids.setImdb(randomString());
        return ids;
    }

    public static Fanart newFanart() {
        Fanart fanart = new Fanart();
        fanart.setThumb(randomString());
        return fanart;
    }

    public static Banner newBanner() {
        Banner banner = new Banner();
        banner.setFull(randomString());
        return banner;
    }

    public static Images newImages() {
        Images images = new Images();
        images.setFanart(newFanart());
        images.setBanner(newBanner());
        return images;
    }

    public static Show newShow() {
        Show show = new Show();
        show.setYear(randomInt());
        show.setTitle(randomString());
        show.setOverview(randomString());
        show.setRating(randomInt());
        show.setIds(newIds());
        show.setImages(newImages());
        List<String> genres = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            genres.add(randomString());
        }
        show.setGenres(genres);
        return show;
    }

    public static List<Show> newListShow(int size) {
        List<Show> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(newShow());
        }
        return list;
    }

    public static Episode newEpisode() {
        Episode episode = new Episode();
        episode.setSeason(randomInt());
        episode.setNumber(randomInt());
        episode.setTitle(randomString());
        episode.setIds(newIds());
        return episode;
    }

    public static List<Episode> newListEpisode(int size) {
        List<Episode> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(newEpisode());
        }
        return list;
    }

    public static Season newSeason() {
        Season season = new Season();
        season.setNumber(randomInt());
        season.setIds(newIds());
        season.setEpisodes(newListEpisode(10));
        return season;
    }

    public static List<Season> newListSeason(int size) {
        List<Season> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(newSeason());
        }
        return list;
    }
}
