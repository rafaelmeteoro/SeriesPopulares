package br.com.rafael.seriespopulares.data.local;


import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.rafael.seriespopulares.data.model.Show;
import br.com.rafael.seriespopulares.data.model.ShowFavorite;

/**
 * Created by rafael on 9/26/16.
 **/
@Singleton
public class ShowFavoriteDao {

    @Inject
    public ShowFavoriteDao() {

    }

    public boolean saveShowFavorite(Show show) {
        try {
            RealmHelper.instanceRealm();
            ShowFavorite showFavorite = new ShowFavorite(show.getIds().getTrakt());
            return RealmHelper.saveObject(showFavorite);
        } finally {
            RealmHelper.closeRealm();
        }
    }

    public boolean isFavorited(Show show) {
        try {
            RealmHelper.instanceRealm();
            ShowFavorite showFavorite =
                    (ShowFavorite) RealmHelper.findFirstByField(ShowFavorite.class, "trakt", show.getIds().getTrakt());
            return showFavorite != null;
        } finally {
            RealmHelper.closeRealm();
        }
    }
}
