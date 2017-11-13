/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

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
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CheeseRepository cheeseDao;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Cheeses");
        return "cheese/index";
    }

    @RequestMapping(value = "/{cheeseID}", method = RequestMethod.GET)
    public String showCheese(@PathVariable int cheeseID, Model model) {
        model.addAttribute("title", "Cheese");
        model.addAttribute("cheese", cheeseDao.findOne(cheeseID));
        return "cheese/cheeseshow";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheese(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheese(@ModelAttribute @Valid Cheese newCheese,
            Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            errors.toString();
            return "cheese/add";
        }

        cheeseDao.save(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove/{cheeseID}", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model, @PathVariable int cheeseID) {
        model.addAttribute("cheese", cheeseDao.findOne(cheeseID));
        model.addAttribute("title", "Delete Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove/{cheeseID}", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@PathVariable int cheeseID) {
        cheeseDao.delete(cheeseID);

        return "redirect:/cheese/";
    }

    @RequestMapping(value = "edit/{cheeseID}", method = RequestMethod.GET)
    public String displayEditCheeseForm(Model model, @PathVariable int cheeseID) {
        model.addAttribute("cheese", cheeseDao.findOne(cheeseID));
        model.addAttribute("title", "Edit Cheese");
        return "cheese/edit";
    }

    @RequestMapping(value = "edit/{cheeseID}", method = RequestMethod.POST)
    public String processEditCheeseForm(@ModelAttribute @Valid Cheese editCheese,
            Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Cheese");
            errors.toString();
            return "cheese/edit";
        }

        cheeseDao.save(editCheese);
        return "redirect:/cheese/";
    }
}
