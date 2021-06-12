package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private UserRepository userRepository;

    @Override
    public void init() {
        userRepository = new InMemoryUserRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "create":
                request.getRequestDispatcher("userCreate.jsp").forward(request, response);
                break;
            case "update":
//                final User user = "create".equals(action) ?
//                        new User(id, "", "", "") :
//                        userRepository.get(getId(request));
//                request.setAttribute("user", user);
//                request.getRequestDispatcher("/userCreate.jsp").forward(request, response);
//                break;
//            case "all":
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                userRepository.delete(id);
                response.sendRedirect("users");
                break;
            default:
                log.info("getAll");
                request.setAttribute("users", userRepository.getAll());
                request.getRequestDispatcher("/users.jsp").forward(request, response);
                break;
        }

//        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost for create and update");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action.equals("create")) {
            userRepository.create(new User(request.getParameter("name"),
                    request.getParameter("email"),
                    request.getParameter("password"),
                    Integer.parseInt(request.getParameter("calories"))
            ));
            response.sendRedirect("/users");
        } else if (action.equals("update")) {
//            mealDao.update(Integer.parseInt(request.getParameter("id")),
//                    new Meal(LocalDateTime.parse(request.getParameter("date")),
//                            request.getParameter("description"),
//                            Integer.parseInt(request.getParameter("calories"))));
//            response.sendRedirect("/meals");
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
