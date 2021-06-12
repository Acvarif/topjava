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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final int CALORIES_PER_DAY = 2000;

    private MealDaoImpl mealDao = new MealDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        String action = request.getParameter("action");

        if (action == null) {
            List<MealTo> mealTos = MealsUtil.filteredByStreams(mealDao.getList(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("meals", mealTos);
            request.getRequestDispatcher("/meal/meals.jsp").forward(request, response);
        } else if (action.equals("create")) {
            request.getRequestDispatcher("/mealcreate.jsp").forward(request, response);
        } else if (action.equals("update")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Meal meal = MealDb.getMealMap().get(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealupdate.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDao.delete(id);
            response.sendRedirect("/meal/meals");
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
            response.sendRedirect("/meal/meals");
        } else if (action.equals("update")) {
            mealDao.update(Integer.parseInt(request.getParameter("id")),
                    new Meal(LocalDateTime.parse(request.getParameter("date")),
                            request.getParameter("description"),
                            Integer.parseInt(request.getParameter("calories"))));
            response.sendRedirect("/meal/meals");
        }
    }
}
