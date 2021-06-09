package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.db.MealDb;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImpl implements MealDao {

    private AtomicInteger mealAtomicId = new AtomicInteger(0);

    @Override
    public void create(Meal meal) {
//            meal.setId(mealAtomicId.incrementAndGet());
//            MealDb.getMealMap().put(meal.getId(), meal);
        meal.setId(mealAtomicId.addAndGet(1));
        MealDb.getMealMap().put(mealAtomicId.get(), meal);
    }

    @Override
    public void update(int id, Meal meal) {
        meal.setId(id);
        MealDb.getMealMap().put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        MealDb.getMealMap().remove(id);
    }

    @Override
    public List<Meal> getList() {
        return new ArrayList<Meal>(MealDb.getMealMap().values());
    }

//    static {
//        MealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
//        MealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
//        MealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
//        MealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
//        MealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
//        MealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
//        MealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
//    }

}
