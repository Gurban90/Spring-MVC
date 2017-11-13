/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import application.model.OrderDetail;
import application.model.repository.CheeseRepository;
import application.model.repository.OrderDetailRepository;
import application.service.OrderDetailServiceImpl;
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
@RequestMapping("orderdetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailDao;

    @Autowired
    private CheeseRepository cheeseDao;

    @Autowired
    private OrderDetailServiceImpl orderDetailService;

    
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("orderdetails", orderDetailDao.findAll());
        model.addAttribute("title", "Orderdetails");
        return "orderdetail/index";
    }

    @RequestMapping(value = "/{orderDetailID}", method = RequestMethod.GET)
    public String showOrderDetail(@PathVariable int orderDetailID, Model model) {
        model.addAttribute("title", "Orderdetails");
        model.addAttribute("order", orderDetailDao.findOne(orderDetailID));
        return "order/ordershow";
    }

    @RequestMapping(value = "add/{orderID}", method = RequestMethod.GET)
    public String displayAddOrderDetail(Model model, @PathVariable int orderID) {
        model.addAttribute("title", "Add OrderDetail");
        model.addAttribute("orderdetail", new OrderDetail());
        model.addAttribute("cheeses", cheeseDao.findAll());
        return "orderdetail/add";
    }

    @RequestMapping(value = "add/{orderID}", method = RequestMethod.POST)
    public String processAddOrderDetail(@RequestParam int cheeseID, @PathVariable int orderID, OrderDetail newOrderDetail,
            @RequestParam int quantity, Model model) {

        orderDetailService.addOrderDetail(orderID, cheeseID, newOrderDetail);
        return "redirect:/order";
    }

    @RequestMapping(value = "remove/{orderDetailID}", method = RequestMethod.GET)
    public String displayRemoveOrderDetailForm(Model model, @PathVariable int orderDetailID) {
        model.addAttribute("title", "Delete OrderDetail");
        model.addAttribute("orderdetail", orderDetailDao.findOne(orderDetailID));
        return "orderdetail/remove";
    }

    @RequestMapping(value = "remove/{orderDetailID}", method = RequestMethod.POST)
    public String processRemoveOrderDetailForm(@PathVariable int orderDetailID) {
        orderDetailService.removeOrderDetail(orderDetailID, orderDetailDao.findOne(orderDetailID).getQuantity());
        return "redirect:/order";
    }

    @RequestMapping(value = "edit/{orderDetailID}", method = RequestMethod.GET)
    public String displayEditOrderDetailForm(Model model, @PathVariable int orderDetailID) {
        model.addAttribute("title", "Edit OrderDetail");
        model.addAttribute("orderdetail", orderDetailDao.findOne(orderDetailID));
        return "orderdetail/edit";
    }

    @RequestMapping(value = "edit/{orderDetailID}", method = RequestMethod.POST)
    public String processEditOrderDetailForm(@ModelAttribute @Valid OrderDetail editOrderDetail,
            Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit OrderDetail");
            errors.toString();
            return "orderdetail/edit";
        }
        orderDetailService.editOrderDetail(editOrderDetail);
        return "redirect:/order";
    }

}
