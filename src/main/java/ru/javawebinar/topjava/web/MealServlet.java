package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.db.MealDb;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
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

    private AtomicInteger mealAtomicId = new AtomicInteger(7);

    private MealDao mealDao = new MealDao() {
        @Override
        public void createMeal(Meal meal) {
            meal.setId(mealAtomicId.incrementAndGet());
            MealDb.getMealMap().put(mealAtomicId.get(), meal);
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
            return new ArrayList<Meal>(MealDb.getMealMap().values());
        }
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        String action = request.getParameter("action");
        System.out.println(action);

        if (action == null) {
            List<MealTo> mealTos = MealsUtil.filteredByStreams(mealDao.getMealList(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("meals", mealTos);

        } else if (action.equals("create")) {
            request.getRequestDispatcher("addMeal.jsp").forward(request, response);

        } else if (action.equals("update")) {

        } else if (action.equals("delete")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Meal meal = MealDb.getMealMap().get(id);
            mealDao.deleteMeal(meal);
        }

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        mealDao.createMeal(new Meal(LocalDateTime.parse(request.getParameter("date")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))));
        response.sendRedirect("/meals");
    }
}
