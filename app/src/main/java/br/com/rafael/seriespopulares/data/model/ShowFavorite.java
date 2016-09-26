package br.com.rafael.seriespopulares.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rafael on 9/26/16.
 **/

public class ShowFavorite extends RealmObject {

    @PrimaryKey
    private int trakt;

    public ShowFavorite() {
    }

    public ShowFavorite(int trakt) {
        this.trakt = trakt;
    }

    public int getTrakt() {
        return trakt;
    }
}