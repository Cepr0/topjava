package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.AppContextListener;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealController = AppContextListener.context.getBean(MealRestController.class);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect("./");
            return;
        }

        String state = Optional.ofNullable(req.getParameter("state")).orElse("cancel");
        if(state.equals("cancel")) {
            resp.sendRedirect("meals");
            return;
        }

        String form = req.getParameter("form");
        switch (form) {
            case "edit": // edit meal form
                String id = req.getParameter("id");
                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        userId,
                        LocalDateTime.parse(req.getParameter("dateTime")),
                        req.getParameter("description"),
                        Integer.valueOf(req.getParameter("calories")));

                if(StringUtils.isEmpty(meal.getDescription())) {
                    LOG.warn("Empty meal description!");
                    req.setAttribute("message", "Ups! A meal description cannot be empty!");
                    req.getRequestDispatcher("info.jsp").forward(req, resp);
                    return;
                }

                if (meal.isNew()) {
                    String date = String.valueOf(meal.getDate());
                    req.getSession().setAttribute("fromDate", date);
                    req.getSession().setAttribute("toDate", date);
                }

                LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
                mealController.save(meal);
                break;

            default: // filter form
                if (state.equals("ok")) {
                    req.getSession().setAttribute("fromDate", req.getParameter("fromDate"));
                    req.getSession().setAttribute("toDate", req.getParameter("toDate"));
                    req.getSession().setAttribute("fromTime", req.getParameter("fromTime"));
                    req.getSession().setAttribute("toTime", req.getParameter("toTime"));
                }

                if (state.equals("reset")) {
                    req.getSession().setAttribute("fromDate", null);
                    req.getSession().setAttribute("toDate", null);
                    req.getSession().setAttribute("fromTime", null);
                    req.getSession().setAttribute("toTime", null);
                }
        }
        resp.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect("./");
            return;
        }

        if (StringUtils.isEmpty(action)) {
            LOG.info("getAll");
            req.setAttribute("mealList",
                    mealController.getWithFilter(userId,
                            TimeUtil.parseDate((String) req.getSession().getAttribute("fromDate")),
                            TimeUtil.parseDate((String) req.getSession().getAttribute("toDate")),
                            TimeUtil.parseTime((String) req.getSession().getAttribute("fromTime")),
                            TimeUtil.parseTime((String) req.getSession().getAttribute("toTime"))));

            req.getRequestDispatcher("mealList.jsp").forward(req, resp);

        } else if ("delete".equals(action)) {
            Integer mealId = null;

            try {
                mealId = getId(req);
                LOG.info("Delete {}", mealId);
                mealController.delete(mealId, userId);
                resp.sendRedirect("meals");

            } catch (Exception e) {
                LOG.error(String.format("Could not perform deleting - a meal with id %d is not found!", mealId));
                req.setAttribute("message", "Ups! A meal is not found!");
                req.getRequestDispatcher("info.jsp").forward(req, resp);

            }
        } else if ("create".equals(action) || "update".equals(action)) {

            try {
                Meal meal = action.equals("create") ?
                        new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                        mealController.get(getId(req), userId);
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("mealEdit.jsp").forward(req, resp);

            } catch (Exception e) {
                LOG.error("Could not perform updating - a meal is not found!");
                req.setAttribute("message", "Ups! A meal is not found!");
                req.getRequestDispatcher("info.jsp").forward(req, resp);
            }
        }
    }

    private int getId(HttpServletRequest req) {
        String paramId = Objects.requireNonNull(req.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
