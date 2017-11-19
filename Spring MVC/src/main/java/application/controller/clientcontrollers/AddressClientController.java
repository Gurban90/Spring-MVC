/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller.clientcontrollers;

import application.helper.AccountChecker;
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
@RequestMapping("clientaddress")
public class AddressClientController {

    @Autowired
    private AddressRepository addressDao;

    @Autowired
    private ClientRepository clientDao;

    @Autowired
    private AddressTypeRepository addressTypeDao;

    @Autowired
    private AddressServiceImpl addressService;

    @Autowired
    private AccountChecker checker;

    @RequestMapping("")
    public String index(Model model) {
        try {
            model.addAttribute("addresses", clientDao.findOne(checker.getClientIDofUser()).getAddresses());
            model.addAttribute("title", "Addresses");
            return "address/clientindex";
        } catch (Exception E) {
            return "security/error2";
        }
    }

    @RequestMapping(value = "/{addressID}", method = RequestMethod.GET)
    public String showAddress(@PathVariable int addressID, Model model) {
        if (addressDao.findOne(addressID).getClient().getClientID() == checker.getClientIDofUser()) {
            model.addAttribute("title", "Address");
            model.addAttribute("address", addressDao.findOne(addressID));
            model.addAttribute("addresstypes", addressDao.findOne(addressID).getAddresstype());
            return "address/addressshow";
        } else {
            return "security/error";
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddAddress(Model model) {
        model.addAttribute("title", "Add Address");
        model.addAttribute(new Address());
        model.addAttribute("addresstypes", addressTypeDao.findAll());
        return "address/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddAddress(Address newAddress,
            @RequestParam int addressTypeID, Model model) {

        addressService.addAddress(checker.getClientIDofUser(), addressTypeID, newAddress);
        return "redirect:/main";
    }

    @RequestMapping(value = "remove/{addressID}", method = RequestMethod.GET)
    public String displayRemoveAddressForm(Model model, @PathVariable int addressID) {
        if (addressDao.findOne(addressID).getClient().getClientID() == checker.getClientIDofUser()) {
            model.addAttribute("address", addressDao.findOne(addressID));
            model.addAttribute("title", "Delete Address");
            return "address/remove";
        } else {
            return "security/error";
        }
    }

    @RequestMapping(value = "remove/{addressID}", method = RequestMethod.POST)
    public String processRemoveAddressForm(@PathVariable int addressID) {
        if (addressDao.findOne(addressID).getClient().getClientID() == checker.getClientIDofUser()) {
            addressDao.delete(addressID);
            return "redirect:/address/";
        } else {
            return "security/error";
        }
    }

    @RequestMapping(value = "edit/{addressID}", method = RequestMethod.GET)
    public String displayEditAddressForm(Model model, @PathVariable int addressID) {
        if (addressDao.findOne(addressID).getClient().getClientID() == checker.getClientIDofUser()) {
            model.addAttribute("address", addressDao.findOne(addressID));
            model.addAttribute("title", "Edit Address");
            model.addAttribute("clients", clientDao.findAll());
            model.addAttribute("addresstypes", addressTypeDao.findAll());
            addressService.setClientAddressType(addressDao.findOne(addressID).getClient().getClientID(),
                    addressDao.findOne(addressID).getAddresstype().getAddressTypeID());
            return "address/edit";
        } else {
            return "security/error";
        }
    }

    @RequestMapping(value = "edit/{addressID}", method = RequestMethod.POST)
    public String processEditAddressForm(Address editAddress, Model model) {
        if (addressDao.findOne(editAddress.getAddressID()).getClient().getClientID() == checker.getClientIDofUser()) {
            addressService.editAddress(editAddress);
            return "redirect:/address/";
        } else {
            return "security/error";
        }
    }
}
