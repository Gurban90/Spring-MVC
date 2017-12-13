/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import application.model.AddressType;
import application.model.repository.AddressTypeRepository;
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
@RequestMapping("addresstype")
public class AddressTypeController {

    @Autowired
    private AddressTypeRepository addressTypeDao;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("addresstypes", addressTypeDao.findAll());
        model.addAttribute("title", "AddressTypes");
        return "addresstype/index";
    }

    @RequestMapping(value = "/{addressTypeID}", method = RequestMethod.GET)
    public String showAddressType(@PathVariable int addressTypeID, Model model) {
        model.addAttribute("title", "AddressType");
        model.addAttribute("addresstype", addressTypeDao.findOne(addressTypeID));
        return "addresstype/addresstypeshow";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddAddressType(Model model) {
        model.addAttribute("title", "Add AddressType");
        model.addAttribute("addresstype", new AddressType());
        return "addresstype/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddAddressType(@ModelAttribute @Valid AddressType newAddressType,
            Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add AddressType");
            errors.toString();
            return "addresstype/add";
        }

        addressTypeDao.save(newAddressType);
        return "redirect:";
    }

    @RequestMapping(value = "remove/{addressTypeID}", method = RequestMethod.GET)
    public String displayRemoveAddressTypeForm(Model model, @PathVariable int addressTypeID) {
        model.addAttribute("addresstype", addressTypeDao.findOne(addressTypeID));
        model.addAttribute("title", "Delete AddressType");
        return "addresstype/remove";
    }

    @RequestMapping(value = "remove/{addressTypeID}", method = RequestMethod.POST)
    public String processRemoveAddressTypeForm(@PathVariable int addressTypeID) {
        addressTypeDao.delete(addressTypeID);
        return "redirect:/addresstype/";
    }

    @RequestMapping(value = "edit/{addressTypeID}", method = RequestMethod.GET)
    public String displayEditAddressTypeForm(Model model, @PathVariable int addressTypeID) {
        model.addAttribute("addresstype", addressTypeDao.findOne(addressTypeID));
        model.addAttribute("title", "Edit AddressType");
        return "addresstype/edit";
    }

    @RequestMapping(value = "edit/{addressTypeID}", method = RequestMethod.POST)
    public String processEditAddressTypeForm(@ModelAttribute @Valid AddressType editAddressType,
            Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit AddressType");
            errors.toString();
            return "addresstype/edit";
        }

        addressTypeDao.save(editAddressType);
        return "redirect:/addresstype/";
    }
}
