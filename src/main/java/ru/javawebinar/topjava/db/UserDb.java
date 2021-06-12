package ru.javawebinar.topjava.db;

import ru.javawebinar.topjava.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDb {

    private static final Map<Integer, User> userMap = new ConcurrentHashMap<>();

    public static Map<Integer, User> getUserMap() {
        return userMap;
    }

}
