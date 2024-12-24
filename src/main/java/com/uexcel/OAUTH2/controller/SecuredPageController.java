package com.uexcel.OAUTH2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecuredPageController {
    @GetMapping("/dashboard")
    public String securedPageController() {
        return "securedPage";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false)
                            String error, @RequestParam(required = false) String logout, Model model){
        if(error != null) {
            model.addAttribute("msg", "Bad credentials");
        }
        if(logout != null) {
            model.addAttribute("msg", "You have been logged out successfully.");
        }
        return "signinPage";
    }
}
