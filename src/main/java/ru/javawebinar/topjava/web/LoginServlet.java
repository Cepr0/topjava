package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.AppContextListener;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.LoginRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        req.getSession().setAttribute("userId", Integer.valueOf(req.getParameter("userId")));
        req.getSession().setAttribute("fromDate", null);
        req.getSession().setAttribute("toDate", null);
        req.getSession().setAttribute("fromTime", null);
        req.getSession().setAttribute("toTime", null);
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = loginController.getAll();
        req.setAttribute("userList", userList);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
