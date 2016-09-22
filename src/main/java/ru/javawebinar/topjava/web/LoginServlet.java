package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.AppContextListener;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.LoginRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private LoginRestController loginController;

    @Override
    public void init() throws ServletException {
        super.init();
        loginController = AppContextListener.context.getBean(LoginRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            Integer userId = Integer.valueOf(req.getParameter("userId")); // parameter of login form
            try {
                User user = loginController.get(userId);

                if(user.getRoles().contains(Role.ROLE_ADMIN)) {
                    req.getSession().setAttribute("isAdmin", true);
                } else {
                    req.getSession().setAttribute("isAdmin", false);
                }

                LOG.info("Log in user with ID {}", userId);

                req.getSession().setAttribute("fromDate", null);
                req.getSession().setAttribute("toDate", null);
                req.getSession().setAttribute("fromTime", null);
                req.getSession().setAttribute("toTime", null);

                req.getSession().setAttribute("userId", userId);

                resp.sendRedirect("meals");
            } catch (NotFoundException e) {

                LOG.error("User with ID {} is not found!", userId);
                req.setAttribute("message", "Ups! User is not found!");
                req.getRequestDispatcher("info.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {

            LOG.error("User ID is invalid!");
            req.setAttribute("message", "Ups! User ID is invalid!");
            req.getRequestDispatcher("info.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userList", loginController.getAll());
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
