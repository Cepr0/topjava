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
import java.util.Locale;

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

            if (!description.isEmpty() && calories != 0) {
                Meal meal = new Meal(dateTime, description, calories);
                meal.setId(id);
                repository.save(meal);
                LOG.info(meal.isNew() ? "Create new Meal {}" : "Update current meal {}", meal.toString());
            } else {
                LOG.warn("Couldn't save a meal with empty data!");
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            LOG.warn("Couldn't parse meal data: {}", e.getMessage());
        }
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("defaultLocale", Locale.getDefault().toString());
        req.setAttribute("isEditFormVisible", "none");
        String action = req.getParameter("action");

        if (action == null) {
            getAllMeals(req, resp);
            return;
        }

        Long id = getId(req);
        switch (action) {
            case "create":
                create(req, resp);
                break;
            case "update":
                update(req, resp, id);
                break;
            case "delete":
                delete(resp, id);
                break;
            case "populate":
                populate(resp);
                break;
            default:
                LOG.info("Get all meals...");
                getAllMeals(req, resp);
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Create new meal...");
        req.setAttribute("isEditFormVisible", "block");
        Meal curMeal = new Meal(LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute())), "", 0);
        req.setAttribute("curMeal", curMeal);
        getAllMeals(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp, Long id) throws ServletException, IOException {
        if (id != null) {
            LOG.info("Edit meal with ID = {} ...", id);
            req.setAttribute("isEditFormVisible", "block");
            Meal curMeal = repository.getById(id);
            req.setAttribute("curMeal", curMeal);
        } else {
            LOG.warn("Couldn't edit meal with null or incorrect ID");
        }
        getAllMeals(req, resp);
    }

    private void delete(HttpServletResponse resp, Long id) throws IOException {
        if (id != null) {
            LOG.info("Delete meal with ID = {} ...", id);
            repository.delete(id);
        } else {
            LOG.warn("Couldn't delete meal with null or incorrect ID");
        }
        resp.sendRedirect("meals");
    }

    private void populate(HttpServletResponse resp) throws IOException {
        LOG.info("Populate new data...");
        repository.populateData(TimeUtil.getRandomDate(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 8, 31)));
        resp.sendRedirect("meals");
    }

    private void getAllMeals(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mealList",
                    MealsUtil.getFilteredWithExceeded(repository.getAllSortedByDate(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher("mealList.jsp").forward(request, response);
    }

    private Long getId(HttpServletRequest request) {
        try {
            return Long.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
