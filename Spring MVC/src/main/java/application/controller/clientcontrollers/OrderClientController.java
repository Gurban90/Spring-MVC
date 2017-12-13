/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller.clientcontrollers;

import application.helper.AccountChecker;
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

/**
 *
 * @author Gerben
 */
@Controller
@RequestMapping("clientorder")
public class OrderClientController {

    @Autowired
    private OrdersRepository orderDao;

    @Autowired
    private ClientRepository clientDao;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private AccountChecker checker;

    @RequestMapping("")
    public String index(Model model) {
        try {
            model.addAttribute("orders", clientDao.findOne(checker.getClientIDofUser()).getOrders());
            model.addAttribute("title", "Orders");
            return "order/clientindex";
        } catch (Exception E) {
            return "security/error2";
        }
    }

    @RequestMapping(value = "/{orderID}", method = RequestMethod.GET)
    public String showOrder(@PathVariable int orderID, Model model) {
        if (orderDao.findOne(orderID).getClient().getClientID() == checker.getClientIDofUser()) {
            model.addAttribute("title", "Orders");
            model.addAttribute("order", orderDao.findOne(orderID));
            model.addAttribute("orderdetails", orderDao.findOne(orderID).getOrderDetails());
            return "order/ordershow";
        } else {
            return "security/error";
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddOrder(Model model) {
        model.addAttribute("title", "Add Order");
        model.addAttribute(new Orders());
        model.addAttribute("clients", clientDao.findAll());
        return "order/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddOrder(Orders newOrder, Model model) {
        orderService.addOrder(checker.getClientIDofUser(), newOrder);
        return "redirect:/clientorder";
    }

    @RequestMapping(value = "remove/{orderID}", method = RequestMethod.GET)
    public String displayRemoveOrderForm(Model model, @PathVariable int orderID) {
        if (orderDao.findOne(orderID).getClient().getClientID() == checker.getClientIDofUser()) {
            model.addAttribute("order", orderDao.findOne(orderID));
            model.addAttribute("title", "Delete Order");
            return "order/remove";
        } else {
            return "security/error";
        }
    }

    @RequestMapping(value = "remove/{orderID}", method = RequestMethod.POST)
    public String processRemoveOrderForm(@PathVariable int orderID) {
        if (orderDao.findOne(orderID).getClient().getClientID() == checker.getClientIDofUser()) {
            orderService.removeOrder(orderID);
            return "redirect:/clientorder";
        } else {
            return "security/error";
        }
    }

}
