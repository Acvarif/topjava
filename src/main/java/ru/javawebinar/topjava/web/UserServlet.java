package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.impl.UserDaoImpl;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UsersUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private UserDaoImpl userDao = new UserDaoImpl();

    public static List<UserTo> listUsersTo(List<User> users) {

        return new User(userDao.getList());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");

        String action = request.getParameter("action");

        if (action == null) {
            List<User> users = userDao.getList();
//            List<UserTo> userTos = ;
            request.setAttribute("users", userTos);
            request.getRequestDispatcher("/user/users.jsp").forward(request, response);
        } else if (action.equals("create")) {
            request.getRequestDispatcher("/user/create.jsp").forward(request, response);
        } else if (action.equals("update")) {
//            Integer id = Integer.parseInt(request.getParameter("id"));
//            Meal meal = MealDb.getMealMap().get(id);
//            request.setAttribute("meal", meal);
//            request.getRequestDispatcher("/meal/update.jsp").forward(request, response);
        } else if (action.equals("delete")) {
//            int id = Integer.parseInt(request.getParameter("id"));
//            mealDao.delete(id);
//            response.sendRedirect("/meals");
        }

//        request.getRequestDispatcher("/users.jsp").forward(request, response);
        response.sendRedirect("/user/users.jsp");
    }
}
