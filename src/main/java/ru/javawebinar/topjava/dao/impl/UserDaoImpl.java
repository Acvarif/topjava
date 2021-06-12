package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.UserDao;
import ru.javawebinar.topjava.db.UserDb;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDaoImpl implements UserDao {

    private AtomicInteger userAtomicId = new AtomicInteger(0);

    @Override
    public void create(User user) {
        user.setId(userAtomicId.incrementAndGet());
        UserDb.getUserMap().put(user.getId(), user);
    }

    @Override
    public void update(int id, User user) {
        user.setId(id);
        UserDb.getUserMap().put(user.getId(), user);
    }

    @Override
    public void delete(int id) {
        UserDb.getUserMap().remove(id);
    }

    @Override
    public List<User> getList() {
        return new ArrayList<User>(UserDb.getUserMap().values());
    }

}
