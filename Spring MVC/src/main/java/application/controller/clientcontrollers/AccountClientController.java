/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller.clientcontrollers;

import application.helper.AccountChecker;
import application.model.Account;
import application.model.repository.AccountRepository;
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
@RequestMapping("clientaccount")
public class AccountClientController {

    @Autowired
    private AccountRepository accountDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AccountChecker checker;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("account", accountDao.findByUsername(checker.getUserName()));
        model.addAttribute("title", "Account");
        return "account/clientindex";
    }

    @RequestMapping(value = "/{accountID}", method = RequestMethod.GET)
    public String showAccount(@PathVariable long accountID, Model model) {
        if (accountDao.findOne(accountID).getUsername().equals(checker.getUserName())) {
            model.addAttribute("title", "Account");
            model.addAttribute("account", accountDao.findOne(accountID));
            return "account/accountshow";
        } else {
            return "security/error";
        }
    }

    @RequestMapping(value = "remove/{accountID}", method = RequestMethod.GET)
    public String displayRemoveAccountForm(Model model, @PathVariable long accountID) {
        if (accountDao.findOne(accountID).getUsername().equals(checker.getUserName())) {
            model.addAttribute("account", accountDao.findOne(accountID));
            model.addAttribute("title", "Delete Account");
            return "account/remove";
        } else {
            return "security/error";
        }
    }

    @RequestMapping(value = "remove/{accountID}", method = RequestMethod.POST)
    public String processRemoveAccountForm(@PathVariable long accountID) {
        if (accountDao.findOne(accountID).getUsername().equals(checker.getUserName())) {
            accountDao.delete(accountID);
            return "redirect:/clientaccount";
        } else {
            return "security/error";
        }
    }

    @RequestMapping(value = "edit/{accountID}", method = RequestMethod.GET)
    public String displayEditAccountForm(Model model, @PathVariable long accountID) {
        if (accountDao.findOne(accountID).getUsername().equals(checker.getUserName())) {
            model.addAttribute("account", accountDao.findOne(accountID));
            model.addAttribute("title", "Edit Account");
            return "account/edit";
        } else {
            return "security/error";
        }
    }

    @RequestMapping(value = "edit/{accountID}", method = RequestMethod.POST)
    public String processEditAccountForm(@ModelAttribute Account editAccount,
            Errors errors, Model model) {
        if (accountDao.findOne(editAccount.getAccountID()).getUsername().equals(checker.getUserName())) {

            String Pass = editAccount.getPassword();
            editAccount = accountDao.findOne(editAccount.getAccountID());
            editAccount.setPassword(bCryptPasswordEncoder.encode(Pass));
            accountDao.save(editAccount);
            return "redirect:/clientaccount";
        } else {
            return "security/error";
        }
    }
}
