/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller.clientcontrollers;

import application.model.Cheese;
import application.model.repository.CheeseRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Gerben
 */
@Controller
@RequestMapping("clientcheese")
public class CheeseClientController {

    @Autowired
    private CheeseRepository cheeseDao;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Cheeses");
        return "cheese/clientindex";
    }

    @RequestMapping(value = "/{cheeseID}", method = RequestMethod.GET)
    public String showCheese(@PathVariable int cheeseID, Model model) {
        model.addAttribute("title", "Cheese");
        model.addAttribute("cheese", cheeseDao.findOne(cheeseID));
        return "cheese/cheeseshow";
    }

}