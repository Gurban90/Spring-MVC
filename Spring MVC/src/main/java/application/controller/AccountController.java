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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountRepository accountDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("accounts", accountDao.findAll());
        model.addAttribute("title", "Accounts");
        return "account/index";
    }

    @RequestMapping(value = "/{accountID}", method = RequestMethod.GET)
    public String showAccount(@PathVariable long accountID, Model model) {
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
        newAccount.setTheRole("USER");
        newAccount.setPassword(bCryptPasswordEncoder.encode(newAccount.getPassword()));
        accountDao.save(newAccount);
        return "redirect:/main";
    }

    @RequestMapping(value = "addAdmin", method = RequestMethod.GET)
    public String displayAddADMINAccount(Model model) {
        model.addAttribute("title", "Add ADMIN Account");
        model.addAttribute(new Account());
        return "account/addAdmin";
    }

    @RequestMapping(value = "addAdmin", method = RequestMethod.POST)
    public String processAddADMINAccount(@ModelAttribute @Valid Account newAccount,
            Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add ADMIN Account");
            errors.toString();
            return "account/addAdmin";
        }
        newAccount.setTheRole("ADMIN");
        newAccount.setPassword(bCryptPasswordEncoder.encode(newAccount.getPassword()));
        accountDao.save(newAccount);
        return "redirect:/main";
    }

    @RequestMapping(value = "remove/{accountID}", method = RequestMethod.GET)
    public String displayRemoveAccountForm(Model model, @PathVariable long accountID) {
        model.addAttribute("account", accountDao.findOne(accountID));
        model.addAttribute("title", "Delete Account");
        return "account/remove";
    }

    @RequestMapping(value = "remove/{accountID}", method = RequestMethod.POST)
    public String processRemoveAccountForm(@PathVariable long accountID) {
        accountDao.delete(accountID);
        return "redirect:/account/";
    }

    @RequestMapping(value = "edit/{accountID}", method = RequestMethod.GET)
    public String displayEditAccountForm(Model model, @PathVariable long accountID) {
        model.addAttribute("account", accountDao.findOne(accountID));
        model.addAttribute("title", "Edit Account");
        return "account/edit";
    }

    @RequestMapping(value = "edit/{accountID}", method = RequestMethod.POST)
    public String processEditAccountForm(@ModelAttribute Account editAccount,
            Errors errors, Model model) {

        String Pass = editAccount.getPassword();
        editAccount = accountDao.findOne(editAccount.getAccountID());
        editAccount.setPassword(bCryptPasswordEncoder.encode(Pass));
        accountDao.save(editAccount);
        return "redirect:/account/";
    }
}
