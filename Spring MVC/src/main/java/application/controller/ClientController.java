/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import application.model.Client;
import application.model.repository.AddressRepository;
import application.model.repository.ClientRepository;
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
@RequestMapping("client")//CLIENT WITH ADDRESS AND ORDERS
public class ClientController {

    @Autowired
    private ClientRepository clientDao;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("clients", clientDao.findAll());
        model.addAttribute("title", "Clients");
        return "client/index";
    }

    @RequestMapping(value = "/{clientID}", method = RequestMethod.GET)
    public String showClient(@PathVariable int clientID, Model model) {
        model.addAttribute("title", "Client");
        model.addAttribute("client", clientDao.findOne(clientID));
        model.addAttribute("addresses", clientDao.findOne(clientID).getAddresses());
        return "client/clientshow";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddClient(Model model) {
        model.addAttribute("title", "Add Client");
        model.addAttribute(new Client());
        return "client/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddClient(@ModelAttribute @Valid Client newClient,
            Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Client");
            errors.toString();
            return "client/add";
        }
        newClient.setAddresses(null);
        clientDao.save(newClient);
        return "redirect:/address/add";
    }

    @RequestMapping(value = "remove/{clientID}", method = RequestMethod.GET)
    public String displayRemoveClientForm(Model model, @PathVariable int clientID) {
        model.addAttribute("client", clientDao.findOne(clientID));
        model.addAttribute("title", "Delete Client");
        return "client/remove";
    }

    @RequestMapping(value = "remove/{clientID}", method = RequestMethod.POST)
    public String processRemoveClientForm(@PathVariable int clientID) {
        clientDao.delete(clientID);

        return "redirect:/client/";
    }

    @RequestMapping(value = "edit/{clientID}", method = RequestMethod.GET)
    public String displayEditClientForm(Model model, @PathVariable int clientID) {
        model.addAttribute("client", clientDao.findOne(clientID));
        model.addAttribute("title", "Edit Client");
        return "client/edit";
    }

    @RequestMapping(value = "edit/{clientID}", method = RequestMethod.POST)
    public String processEditClientForm(@ModelAttribute @Valid Client editClient,
            Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Client");
            errors.toString();
            return "client/edit";
        }

        clientDao.save(editClient);
        return "redirect:/client/";
    }
}
