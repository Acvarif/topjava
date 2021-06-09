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
            meal.setId(mealAtomicId.incrementAndGet());
            MealDb.getMealMap().put(mealAtomicId.get(), meal);
        }

        @Override
        public void updateMeal(Integer id, Meal meal) {
            meal.setId(id);
            MealDb.getMealMap().put(id, meal);
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

        if (action == null) {
            List<MealTo> mealTos = MealsUtil.filteredByStreams(mealDao.getMealList(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("meals", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("create")) {
            request.getRequestDispatcher("create.jsp").forward(request, response);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("update")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Meal meal = MealDb.getMealMap().get(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("update.jsp").forward(request, response);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Meal meal = MealDb.getMealMap().get(id);
            mealDao.deleteMeal(meal);
            response.sendRedirect("/meals");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost for create and update");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action.equals("create")) {
            mealDao.createMeal(new Meal(LocalDateTime.parse(request.getParameter("date")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))));
            response.sendRedirect("/meals");
        } else if (action.equals("update")) {
            mealDao.updateMeal(Integer.parseInt(request.getParameter("id")),
                    new Meal(LocalDateTime.parse(request.getParameter("date")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))));
            response.sendRedirect("/meals");
        }
    }
}
