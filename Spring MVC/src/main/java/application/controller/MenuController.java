/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Gerben
 */
@Controller
@RequestMapping("")
public class MenuController {
    
    @RequestMapping("")
    public String index(Model model) {
        
        model.addAttribute("title", "KaasApplicatie");
        return "main/MainMenu";
    }
}
