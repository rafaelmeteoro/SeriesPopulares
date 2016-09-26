package br.com.rafael.seriespopulares.data.local;

import io.realm.Realm;
import io.realm.RealmObject;
import timber.log.Timber;

/**
 * Created by rafael on 9/26/16.
 **/

public class RealmHelper {

    public static final String SERIES_POPULARES_DB_NAME = "series_populares_db.realm";
    public static final int SERIES_POPULARES_DB_VERSION = 1;

    private static Realm realm;

    public static void instanceRealm() {
        realm = Realm.getDefaultInstance();
    }

    public static void closeRealm() {
        if (realm != null) {
            realm.close();
        }
    }

    public static void beginTransaction() {
        realm.beginTransaction();
    }

    public static void commitTransaction() {
        realm.commitTransaction();
    }

    /**
     * Salva um objeto no Realm.
     *
     * @param object
     * */
    public static boolean saveObject(RealmObject object) {
        try {
            beginTransaction();
            realm.copyToRealmOrUpdate(object);
            commitTransaction();
            return true;
        } catch (Exception e) {
            Timber.e(e, "Erro ao salvar objeto");
            return false;
        }
    }

    /**
     * Retorna um objeto do Realm.
     *
     * @param T
     * @param field
     * @param value
     * @return Retorna um objeto
     * */
    public static RealmObject findFirstByField(Class T, String field, int value) {
        try {
            return (RealmObject) realm.where(T).equalTo(field, value).findFirst();
        } catch (Exception e) {
            Timber.e(e, "Erro ao recuperar objeto");
            return null;
        }
    }
}
