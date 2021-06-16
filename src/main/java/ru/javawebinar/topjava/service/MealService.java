package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meal create(Meal meal) {
        return mealRepository.save(meal);
    }

    public void delete(int id) {
        checkNotFoundWithId(mealRepository.delete(id), id);
    }

    public Meal get(int id) {
        return checkNotFoundWithId(mealRepository.get(id), id);
    }

    public Collection<Meal> getAll() {
        return mealRepository.getAll();
    }

    public void update(Meal meal) {
        checkNotFoundWithId(mealRepository.save(meal), meal.getId());
    }
}