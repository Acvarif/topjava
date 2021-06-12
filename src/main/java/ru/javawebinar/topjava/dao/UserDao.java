package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface UserDao {

    void create(User user);
    void update(int id, User user);
    void delete(int id);
    List<User> getList();
}
