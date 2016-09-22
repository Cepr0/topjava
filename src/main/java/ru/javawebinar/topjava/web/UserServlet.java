package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.AppContextListener;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);
    private AdminRestController userController;

    @Override
    public void init() throws ServletException {
        super.init();
        userController = AppContextListener.context.getBean(AdminRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        if (!isAdmin(req, resp)) return;

//        Integer userId = (Integer) req.getSession().getAttribute("userId"); // parameter from user session
//        if (userId == null) {
//            LOG.warn("User id invalid - redirecting to root");
//            resp.sendRedirect("./");
//            return;
//        }
//
        String state = Optional.ofNullable(req.getParameter("state")).orElse("cancel");
        if (state.equals("ok")) {
            try {
                String id = req.getParameter("id"); // id of created or updated user
                User user;
                if (id.isEmpty()) {
                    user = new User(
                            null,
                            req.getParameter("name"),
                            req.getParameter("email"),
                            req.getParameter("password"),
                            Integer.valueOf(req.getParameter("caloriesPerDay")),
                            Role.ROLE_USER);
                } else {
                    user = userController.get(Integer.valueOf(id)); // Catch an exception below
                    user.setName(req.getParameter("name"));
                    user.setEmail(req.getParameter("email"));
                    user.setPassword(req.getParameter("password"));
                    user.setCaloriesPerDay(Integer.valueOf(req.getParameter("caloriesPerDay")));
                }

                if (StringUtils.isEmpty(user.getName())
                        || StringUtils.isEmpty(user.getEmail())
                        //|| StringUtils.isEmpty(user.getPassword())
                        || user.getCaloriesPerDay() <= 0) {
                    LOG.error("User parameters are empty or wrong!");
                    req.setAttribute("message", "Ups! A user parameters cannot be empty or wrong!");
                    req.getRequestDispatcher("info.jsp").forward(req, resp);
                    return;
                } else {
                    if (user.isNew()) {
                        LOG.info("Create {}", user);
                        userController.create(user);
                    } else {
                        LOG.info("Update {}", user);
                        userController.update(user, user.getId());
                    }
                }
            } catch (NumberFormatException e) {
                // user = userController.get(Integer.valueOf(id));
                LOG.error("User ID is wrong!");
                req.setAttribute("message", "Ups! A user ID is wrong!");
                req.getRequestDispatcher("info.jsp").forward(req, resp);
                return;
            }
        }

        resp.sendRedirect("users");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req, resp)) return;

        String id = req.getParameter("id"); // id parameter of request: user?action=create&user={id}
        Integer userId = null;
        try {
            userId = Integer.valueOf(id);
        } catch (NumberFormatException ignored) {
        }

        String action = Optional.ofNullable(req.getParameter("action")).orElse("");

        switch (action) {

            case "delete":
                if (userId != null) {
                    try {
                        User user = userController.get(userId);

                        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
                            LOG.warn("An attempt to delete Administrator. Prevented.");
                            req.setAttribute("message", "Ups! It's not possible to delete the Administrator!");
                            req.getRequestDispatcher("info.jsp").forward(req, resp);
                        } else {
                            userController.delete(userId);
                            LOG.info("Deleted user with ID {}", userId);
                            req.setAttribute("message", "A user has been successful deleted.");
                            req.getRequestDispatcher("info.jsp").forward(req, resp);
                        }
                    } catch (Exception e) {
                        LOG.error("User with ID {} is not found - could not perform deletion!", id);
                        req.setAttribute("message", "Ups! A user is not found!");
                        req.getRequestDispatcher("info.jsp").forward(req, resp);
                    }
                } else {
                    LOG.error("User ID is invalid - could not perform deletion!");
                    req.setAttribute("message", "Ups! User ID is invalid!");
                    req.getRequestDispatcher("info.jsp").forward(req, resp);
                }
                break;

            case "create":
                req.setAttribute("user", new User());
                req.getRequestDispatcher("userEdit.jsp").forward(req, resp);
                break;
            case "update":
                try {
                    if (userId == null) throw new NotFoundException("User ID is invalid.");
                    req.setAttribute("user", userController.get(userId));
                    req.getRequestDispatcher("userEdit.jsp").forward(req, resp);
                } catch (Exception e) {
                    LOG.error("Could not perform user {} operation  - a user is not found!", action);
                    req.setAttribute("message", "Ups! A user is not found!");
                    req.getRequestDispatcher("info.jsp").forward(req, resp);
                }
                break;

            default:
                LOG.debug("get all users");
                req.setAttribute("userList", userController.getAll());
                req.getRequestDispatcher("userList.jsp").forward(req, resp);
        }
    }

    private boolean isAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Boolean isAdmin = (Boolean) Optional.ofNullable(req.getSession().getAttribute("isAdmin")).orElse(false);
        if (!isAdmin) {
            LOG.warn("User is not an Administrator - forwarding to meals");
            resp.sendRedirect("meals");
            return false;
        }
        return true;
    }
}
