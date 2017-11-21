/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import application.model.Address;
import application.model.AddressType;
import application.model.Client;
import application.model.repository.AddressRepository;
import application.model.repository.AddressTypeRepository;
import application.model.repository.ClientRepository;
import application.service.AddressServiceImpl;
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
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressRepository addressDao;

    @Autowired
    private ClientRepository clientDao;

    @Autowired
    private AddressTypeRepository addressTypeDao;

    @Autowired
    private AddressServiceImpl addressService;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("addresses", addressDao.findAll());
        model.addAttribute("title", "Addresses");
        return "address/index";
    }

    @RequestMapping(value = "/{addressID}", method = RequestMethod.GET)
    public String showAddress(@PathVariable int addressID, Model model) {
        model.addAttribute("title", "Address");
        model.addAttribute("address", addressDao.findOne(addressID));
        model.addAttribute("addresstypes", addressDao.findOne(addressID).getAddresstype());
        return "address/addressshow";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddAddress(Model model) {
        model.addAttribute("title", "Add Address");
        model.addAttribute(new Address());
        model.addAttribute("clients", clientDao.findAll());
        model.addAttribute("addresstypes", addressTypeDao.findAll());
        return "address/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddAddress(Address newAddress,
            @RequestParam int clientID, @RequestParam int addressTypeID, Model model) {

        addressService.addAddress(clientID, addressTypeID, newAddress);
        return "redirect:/main";
    }

    @RequestMapping(value = "remove/{addressID}", method = RequestMethod.GET)
    public String displayRemoveAddressForm(Model model, @PathVariable int addressID) {
        model.addAttribute("address", addressDao.findOne(addressID));
        model.addAttribute("title", "Delete Address");
        return "address/remove";
    }

    @RequestMapping(value = "remove/{addressID}", method = RequestMethod.POST)
    public String processRemoveAddressForm(@PathVariable int addressID) {
        addressDao.delete(addressID);

        return "redirect:/address/";
    }

    @RequestMapping(value = "edit/{addressID}", method = RequestMethod.GET)
    public String displayEditAddressForm(Model model, @PathVariable int addressID) {
        model.addAttribute("address", addressDao.findOne(addressID));
        model.addAttribute("title", "Edit Address");
        model.addAttribute("clients", clientDao.findAll());
        model.addAttribute("addresstypes", addressTypeDao.findAll());
        addressService.setClientAddressType(addressDao.findOne(addressID).getClient().getClientID(),
                addressDao.findOne(addressID).getAddresstype().getAddressTypeID());
        return "address/edit";
    }

    @RequestMapping(value = "edit/{addressID}", method = RequestMethod.POST)
    public String processEditAddressForm(Address editAddress, Model model) {

        addressService.editAddress(editAddress);
        return "redirect:/address/";
    }
}
