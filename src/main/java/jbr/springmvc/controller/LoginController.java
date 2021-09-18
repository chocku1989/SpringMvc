package jbr.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jbr.springmvc.model.Login;
import jbr.springmvc.model.User;
import jbr.springmvc.service.UserService;

@Controller
public class LoginController {

  @Autowired
  UserService userService;

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView("login"); //login view name is assigned to modelView Object
    mav.addObject("login", new Login());  //now login is model, whose object is created and assigned to MV object

    return mav;
  }

  @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
  public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
      @ModelAttribute("login") Login login) {
    ModelAndView mav = null;

    User user = userService.validateUser(login);
    
    

    if (null != user) {
      mav = new ModelAndView("welcome");
      mav.addObject("firstname", user.getFirstname());
     mav.addObject("username", user.getUsername());
    } else {
      mav = new ModelAndView("login");
      mav.addObject("message", "Username or Password is wrong!!");
     
    }

    return mav;
  }
  
  @RequestMapping(value = "/deleteProcess", method = RequestMethod.POST)
  public ModelAndView deleteProcess(HttpServletRequest request, HttpServletResponse response,
      @ModelAttribute("username") String  userName) {
    ModelAndView mav = null;

   // User user = userService.validateUser(login);
    int deleteCount = userService.deleteUser(userName);
    mav = new ModelAndView("login"); 
    if(deleteCount>0) {
    mav.addObject("deletemessage", "User"+userName+" ;has been deleted "); 
    } else {
    	 mav.addObject("deletemessage", "User"+userName+"has been not been deleted "); 	
    }
    
    

    return mav;
  }

}
