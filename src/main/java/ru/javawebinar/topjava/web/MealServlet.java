package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Cepro on 08.09.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //LOG.debug("Forward to mealList");
        LOG.info("Get all meals...");
        request.setAttribute("mealList",
                MealsUtil.getFilteredWithExceeded(MealsUtil.MEAL_LIST, LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher("mealList.jsp").forward(request, response);
    }
}
