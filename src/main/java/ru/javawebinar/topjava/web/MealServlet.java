package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
import java.util.Arrays;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private ConfigurableApplicationContext appCtx;
    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        String[] beanDefinitionNames = appCtx.getBeanDefinitionNames();
        System.err.println("Bean definition names:");
        Arrays.stream(beanDefinitionNames).forEach(System.err::println);

        mealController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String form = request.getParameter("form");
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("./");
            return;
        }

        switch (form) {
            case "edit":
                String id = request.getParameter("id");
                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        userId,
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.valueOf(request.getParameter("calories")));

                if (meal.isNew()) {
                    String date = String.valueOf(meal.getDate());
                    request.getSession().setAttribute("fromDate", date);
                    request.getSession().setAttribute("toDate", date);
                }

                LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
                mealController.save(meal);
                break;

            default:
                if (request.getParameter("filter") != null) {
                    request.getSession().setAttribute("fromDate", request.getParameter("fromDate"));
                    request.getSession().setAttribute("toDate", request.getParameter("toDate"));
                    request.getSession().setAttribute("fromTime", request.getParameter("fromTime"));
                    request.getSession().setAttribute("toTime", request.getParameter("toTime"));
                }

                if (request.getParameter("reset") != null) {
                    request.getSession().setAttribute("fromDate", null);
                    request.getSession().setAttribute("toDate", null);
                    request.getSession().setAttribute("fromTime", null);
                    request.getSession().setAttribute("toTime", null);
                }
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("./");
            return;
        }

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList",
                    mealController.getWithFilter(userId,
                            TimeUtil.parseDate((String) request.getSession().getAttribute("fromDate")),
                            TimeUtil.parseDate((String) request.getSession().getAttribute("toDate")),
                            TimeUtil.parseTime((String) request.getSession().getAttribute("fromTime")),
                            TimeUtil.parseTime((String) request.getSession().getAttribute("toTime"))));

            request.getRequestDispatcher("mealList.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                    mealController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
