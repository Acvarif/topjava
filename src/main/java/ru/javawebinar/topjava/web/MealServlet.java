package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.impl.MealDaoImpl;
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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final int CALORIES_PER_DAY = 2000;

    private static MealDaoImpl mealDao = new MealDaoImpl();

    static {
        mealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealDao.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        String action = request.getParameter("action");

        if (action == null) {
            List<MealTo> mealTos = MealsUtil.filteredByStreams(mealDao.getList(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("meals", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("create")) {
            request.getRequestDispatcher("create.jsp").forward(request, response);
        } else if (action.equals("update")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Meal meal = MealDb.getMealMap().get(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("update.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDao.delete(id);
            response.sendRedirect("/meals");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost for create and update");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action.equals("create")) {
            mealDao.create(new Meal(LocalDateTime.parse(request.getParameter("date")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))));
            response.sendRedirect("/meals");
        } else if (action.equals("update")) {
            mealDao.update(Integer.parseInt(request.getParameter("id")),
                    new Meal(LocalDateTime.parse(request.getParameter("date")),
                            request.getParameter("description"),
                            Integer.parseInt(request.getParameter("calories"))));
            response.sendRedirect("/meals");
        }
    }
}
