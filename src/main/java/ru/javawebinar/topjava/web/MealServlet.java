package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Cepro on 08.09.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private InMemoryMealRepositoryImpl repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new InMemoryMealRepositoryImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Long id = req.getParameter("id").isEmpty() ? null : Long.valueOf(req.getParameter("id"));
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
            String description = req.getParameter("description");
            Integer calories = Integer.valueOf(req.getParameter("calories"));

            Meal meal = new Meal(dateTime, description, calories);
            meal.setId(id);
            repository.save(meal);
            LOG.info(meal.isNew() ? "Create new Meal {}" : "Update current meal {}", meal);
        } catch (NumberFormatException | DateTimeParseException e) {
            LOG.warn("Couldn't parse meal data: {}", e.getMessage());
        }
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("isEditFormVisible", "none");
        Meal curMeal = new Meal(LocalDateTime.now(), "", 0);
        curMeal.setId(0);
        req.setAttribute("curMeal", curMeal);

        String action = req.getParameter("action");
        if (action == null) {
            getAllMeals(req, resp);
            return;
        }

        Long id = getId(req);
        switch (action) {
            case "create":
                if (id != null) {
                    LOG.info("Create new meal...");
                    // TODO Implement new meal action
                }
                break;
            case "update":
                if (id != null) {
                    LOG.info("Edit meal with ID = {} ...", id);
                    // TODO Implement edit current meal action
                    req.setAttribute("isEditFormVisible", "block");

                    curMeal = repository.getById(id);
                    req.setAttribute("curMeal", curMeal);
                    getAllMeals(req, resp);
                } else {
                    LOG.warn("Couldn't edit meal with null or incorrect ID");
                }

                break;
            case "delete":
                if (id != null) {
                    LOG.info("Delete meal with ID = {} ...", id);
                    repository.delete(id);
                    resp.sendRedirect("meals");
                } else {
                    LOG.warn("Couldn't delete meal with null or incorrect ID");
                }
                break;
            case "populate":
                LOG.info("Populate new data...");
                repository.populateData(TimeUtil.getRandomDate(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 8, 31)));
                resp.sendRedirect("meals");
                break;

            default:
                LOG.info("Get all meals...");
                getAllMeals(req, resp);
        }
    }

    private void getAllMeals(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mealList",
                MealsUtil.getFilteredWithExceeded(repository.getAllSortedByDate(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher("mealList.jsp").forward(request, response);
    }

    private Long getId(HttpServletRequest request) {
        Long id = null;
        try {
            id = Long.valueOf(request.getParameter("id"));
        } catch (Exception e) {
            id = null;
        }
        return id;
    }
}
