package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Cepro
 *         15.10.16
 */
@Controller
public class MvcMealController extends MealController {

  @RequestMapping(value = "/meals", method = GET)
  public String getAllMeals(Model model) {
    model.addAttribute("meals", getAll());
    return "meals";
  }
  
  @RequestMapping(value = "/meals/delete/{id}", method = GET)
  public String deleteMeal(@PathVariable("id") int id /*HttpServletRequest req*/) {
//    Integer id = getMealId(req);
//    if (id != null) {
//      delete(id);
//    } else {
//      LOG.error("Couldn't delete meal - ID is invalid!");
//    }
    delete(id);
    return "redirect:/meals";
  }
  
  @RequestMapping(value = "/meals/update/{id}", method = GET)
  public String editMeal(@PathVariable("id") int id, Model model) {
    model.addAttribute("meal", get(id));
    return "meal";
  }
  
  @RequestMapping(value = "/meals/create", method = GET)
  public String createMeal(Model model) {
    model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
    return "meal";
  }
  
  @RequestMapping(value = "meals/filter", method = POST)
  public String getFilteredMeal(HttpServletRequest req, Model model) {
    LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", req));
    LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", req));
    LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", req));
    LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", req));
    model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
    return "meals";
  }
  
  @RequestMapping(method = POST)
  public String saveMeal(HttpServletRequest request) {
    String id = request.getParameter("id");
    Meal meal = null;
    try {
      meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
          LocalDateTime.parse(request.getParameter("dateTime")),
          request.getParameter("description"),
          Integer.valueOf(request.getParameter("calories")));
      if (meal.isNew()) {
        create(meal);
      } else {
        update(meal, meal.getId());
      }
    } catch (Exception e) {
      LOG.error("Couldn't save meal - any parameters are invalid! {}", e.getMessage());
    }
  
    return "redirect:/meals";
  }
  
  @ExceptionHandler(Throwable.class)
  public ModelAndView handleException(HttpServletRequest req, Exception ex) {
    LOG.error("Request: " + req.getRequestURL() + " raised " + ex);
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", ex);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName("info");
    return mav;
  }
  
  private String resetParam(String param, HttpServletRequest request) {
    String value = request.getParameter(param);
    request.setAttribute(param, value);
    return value;
  }
  
  private Integer getMealId(HttpServletRequest req) {
    try {
      return Integer.valueOf(req.getParameter("id"));
    } catch (Exception e) {
      LOG.error("Couldn't perform the operation - ID is invalid!");
    }
    return null;
  }
}
