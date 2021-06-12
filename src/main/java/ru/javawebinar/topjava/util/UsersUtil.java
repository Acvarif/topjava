package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;

import java.util.Collection;
import java.util.List;

public class UsersUtil {

    public static UserTo createTo(User user) {
        return new UserTo(user.getName(), user.getEmail(), user.getPassword(), user.getCalories());
    }
}
