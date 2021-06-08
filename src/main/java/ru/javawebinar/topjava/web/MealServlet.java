package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.db.MealDb;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final int CALORIES_PER_DAY = 2000;

    private AtomicInteger mealAtomicId = new AtomicInteger(0);

    private MealDao mealDao = new MealDao() {
        @Override
        public void createMeal(Meal meal) {
            MealDb.getMealMap().put(8, meal);
        }

        @Override
        public void updateMeal(Meal meal) {
            Meal mealNew = MealDb.getMealMap().get(meal.getId());
            mealNew.setId(meal.getId());
            mealNew.setDateTime(meal.getDateTime());
            mealNew.setDescription(meal.getDescription());
            mealNew.setCalories(meal.getCalories());
        }

        @Override
        public void deleteMeal(Meal meal) {
            MealDb.getMealMap().remove(meal.getId());
        }

        @Override
        public List<Meal> getMealList() {
            return null;
        }
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        String action = request.getParameter("action");
        System.out.println(action);

        if (action == null) {
            mealDao.createMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 610));

            List<Meal> meals = new ArrayList<Meal>(MealDb.getMealMap().values());

            List<MealTo> mealTos = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("meals", mealTos);

        } else if (action == "create") {

        } else if (action == "update") {

        } else if (action == "delete") {

        }

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
