package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
  private static final Logger LOG = LoggerFactory.getLogger(RootController.class);
  
  @Autowired
  private UserService service;
  
  @RequestMapping(value = "/", method = GET)
  public String root() {
    return "index";
  }
  
  @RequestMapping(value = "/users", method = GET)
  public String users(Model model) {
    model.addAttribute("users", service.getAll());
    return "users";
  }
  
  @RequestMapping(value = "/users", method = POST)
  public String setUser(HttpServletRequest request) {
    int userId = Integer.valueOf(request.getParameter("userId"));
    AuthorizedUser.setId(userId);
    return "redirect:meals";
  }
  
  //https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
  //https://www.mkyong.com/spring-mvc/spring-mvc-exception-handling-example/
//  @ExceptionHandler(Throwable.class)
//  public ModelAndView handleException(HttpServletRequest req, Exception ex) {
//    LOG.error("Request: " + req.getRequestURL() + " raised " + ex);
//    ModelAndView mav = new ModelAndView();
//    mav.addObject("exception", ex);
//    mav.addObject("url", req.getRequestURL());
//    mav.setViewName("info");
//    return mav;
//  }
}
