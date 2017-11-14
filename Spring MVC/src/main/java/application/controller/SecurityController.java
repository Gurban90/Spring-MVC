/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Gerben
 */
@Controller
@RequestMapping("security")
public class SecurityController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Login");
        return "security/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login() {
        return "redirect:/client/";
    }

    @RequestMapping("/error")
    public String error(Model model) {
        String errorMessage = "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        model.addAttribute("title", "Error 403");
        return "security/error";
    }

    @RequestMapping("/confirmLogout")
    public String logoutConfirmation(Model model) {
        String message = "You are logged out.";
        model.addAttribute("message", message);
        return "security/confirmLogout";
    }
}
