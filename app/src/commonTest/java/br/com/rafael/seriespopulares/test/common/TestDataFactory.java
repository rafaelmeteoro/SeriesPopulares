package br.com.rafael.seriespopulares.test.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

    public static Show newShow() {
        Show show = new Show();
        return show;
    }

    public static List<Show> newListShow(int size) {
        List<Show> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(newShow());
        }
        return list;
    }
}
