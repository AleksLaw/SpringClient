package main.controller;


import main.model.Role;
import main.model.User;
import main.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

import static main.service.ServiseUserImp.listReq;


@Controller
public class UserController {
    @Autowired
    private ServiceUser serviceUser;

    @GetMapping("/")
    public String greeting() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (authorities.contains(Role.ADMIN)) {
            return "redirect:/admin/adminPage";
        }
        return "redirect:/user/userPageInfo";
    }

    @RequestMapping(value = "/admin/adminPage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listUsers() {
        serviceUser.start();
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.getModelMap().addAttribute("listUsers", listReq);
        modelAndView.getModelMap().addAttribute("currentUser", currentUser);
        return modelAndView;
    }

    @GetMapping("/user/userPageInfo")
    public ModelAndView printWelcome() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView("userPageInfo");
        modelAndView.getModelMap().addAttribute("currentUser", currentUser);
        return modelAndView;
    }
}
