/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import application.model.Orders;
import application.model.repository.ClientRepository;
import application.model.repository.OrdersRepository;
import application.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Gerben
 */
@Controller
@RequestMapping("order")
public class OrdersController {

    @Autowired
    private OrdersRepository orderDao;

    @Autowired
    private ClientRepository clientDao;

    @Autowired
    private OrderServiceImpl orderService;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("orders", orderDao.findAll());
        model.addAttribute("title", "Orders");
        return "order/index";
    }

    @RequestMapping(value = "/{orderID}", method = RequestMethod.GET)
    public String showOrder(@PathVariable int orderID, Model model) {
        model.addAttribute("title", "Orders");
        model.addAttribute("order", orderDao.findOne(orderID));
        model.addAttribute("orderdetails", orderDao.findOne(orderID).getOrderDetails());
        return "order/ordershow";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddOrder(Model model) {
        model.addAttribute("title", "Add Order");
        model.addAttribute(new Orders());
        model.addAttribute("clients", clientDao.findAll());
        return "order/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddOrder(@RequestParam int clientID, Orders newOrder, Model model) {

        orderService.addOrder(clientID, newOrder);
        return "redirect:/order/";
    }

    @RequestMapping(value = "remove/{orderID}", method = RequestMethod.GET)
    public String displayRemoveOrderForm(Model model, @PathVariable int orderID) {
        model.addAttribute("order", orderDao.findOne(orderID));
        model.addAttribute("title", "Delete Order");
        return "order/remove";
    }

    @RequestMapping(value = "remove/{orderID}", method = RequestMethod.POST)
    public String processRemoveOrderForm(@PathVariable int orderID) {
        orderDao.delete(orderID);
        return "redirect:/order/";
    }

    @RequestMapping(value = "edit/{orderID}", method = RequestMethod.GET)
    public String displayEditOrderForm(Model model, @PathVariable int orderID) {
        model.addAttribute("order", orderDao.findOne(orderID));
        model.addAttribute("title", "Edit Order");
        model.addAttribute("clients", clientDao.findAll());
        return "order/edit";
    }

}
