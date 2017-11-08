/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import application.helper.OrderEdit;
import application.model.Cheese;
import application.model.Client;
import application.model.OrderDetail;
import application.model.Orders;
import application.model.repository.CheeseRepository;
import application.model.repository.ClientRepository;
import application.model.repository.OrderDetailRepository;
import application.model.repository.OrdersRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private OrdersRepository orderDao;

    @Autowired
    private OrderEdit edit;

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
        Orders ord = orderDao.findOne(orderID);
        newOrderDetail.setOrders(ord);
        Cheese ch = cheeseDao.findOne(cheeseID);
        newOrderDetail.setCheese(ch);
        edit.setCheeseOrder(orderID, cheeseID, newOrderDetail.getQuantity());
        orderDetailDao.save(newOrderDetail);
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
        edit.deleteCheeseOrder(orderDetailID, orderDetailDao.findOne(orderDetailID).getQuantity());
        orderDetailDao.delete(orderDetailID);
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
        edit.editCheeseOrder(editOrderDetail);
        orderDetailDao.save(editOrderDetail);
        return "redirect:/order";
    }

}
