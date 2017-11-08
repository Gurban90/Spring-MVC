/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import application.model.Account;
import application.model.repository.AccountRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Gerben
 */
@Controller
@RequestMapping("account")//NOG GEEN BEVEILIGING OF HASH OF IETS DERGELIJKS!!!
public class AccountController {
 
    @Autowired
    private AccountRepository accountDao;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("accounts", accountDao.findAll());
        model.addAttribute("title", "Accounts");
        return "account/index";
    }

    @RequestMapping(value = "/{accountID}", method = RequestMethod.GET)
    public String showAccount(@PathVariable int accountID, Model model) {
        model.addAttribute("title", "Account");
        model.addAttribute("account", accountDao.findOne(accountID));
        return "account/accountshow";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddAccount(Model model) {
        model.addAttribute("title", "Add Account");
        model.addAttribute(new Account());
        return "account/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddAccount(@ModelAttribute @Valid Account newAccount,
            Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Account");
            errors.toString();
            return "account/add";
        }

        accountDao.save(newAccount);
        return "redirect:";
    }

    @RequestMapping(value = "remove/{accountID}", method = RequestMethod.GET)
    public String displayRemoveAccountForm(Model model, @PathVariable int accountID) {
        model.addAttribute("account", accountDao.findOne(accountID));
        model.addAttribute("title", "Delete Account");
        return "account/remove";
    }

    @RequestMapping(value = "remove/{accountID}", method = RequestMethod.POST)
    public String processRemoveAccountForm(@PathVariable int accountID) {
        accountDao.delete(accountID);

        return "redirect:/account/";
    }

    @RequestMapping(value = "edit/{accountID}", method = RequestMethod.GET)
    public String displayEditAccountForm(Model model, @PathVariable int accountID) {
        model.addAttribute("account", accountDao.findOne(accountID));
        model.addAttribute("title", "Edit Account");
        return "account/edit";
    }

    @RequestMapping(value = "edit/{accountID}", method = RequestMethod.POST)
    public String processEditAccountForm(@ModelAttribute @Valid Account editAccount,
            Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Account");
            errors.toString();
            return "account/edit";
        }

        accountDao.save(editAccount);
        return "redirect:/account/";
    }
}

