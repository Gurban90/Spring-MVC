/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.helper;

import application.model.Cheese;
import application.model.OrderDetail;
import application.model.Orders;
import application.model.repository.CheeseRepository;
import application.model.repository.OrderDetailRepository;
import application.model.repository.OrdersRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 *
 * @author Gerben
 */
@Controller
public class OrderEdit {

    @Autowired
    private OrderDetailRepository orderDetailDao;

    @Autowired
    private OrdersRepository orderDao;

    @Autowired
    private CheeseRepository cheeseDao;

    private int quantity;

    public void setCheeseOrder(int orderID, int cheeseID, int quantity) {
        this.quantity = quantity;

        Cheese cheese = cheeseDao.findOne(cheeseID);
        cheese.setStock(cheese.getStock() - this.quantity);

        Orders order = orderDao.findOne(orderID);
        BigDecimal quantityBD = new BigDecimal(this.quantity);
        order.setTotalPrice(order.getTotalPrice().add(quantityBD.multiply(cheese.getPrice())));
    }

    public void deleteCheeseOrder(int OrderDetailID, int quantity) {
        OrderDetail orderDetail = orderDetailDao.findOne(OrderDetailID);
        this.quantity = quantity;

        Cheese cheese = orderDetail.getCheese();
        cheese.setStock(cheese.getStock() + this.quantity);

        Orders order = orderDetail.getOrders();
        BigDecimal quantityBD = new BigDecimal(this.quantity);
        order.setTotalPrice(order.getTotalPrice().subtract(quantityBD.multiply(cheese.getPrice())));
    }

    public void editCheeseOrder(OrderDetail orderDetail) {
        OrderDetail orderDetail1 = orderDetailDao.findOne(orderDetail.getOrderDetailID());

        if (orderDetail1.getQuantity() <= orderDetail.getQuantity()) {
            int extraQuantity = orderDetail.getQuantity() - orderDetail1.getQuantity();
            int orderID = orderDetail.getOrders().getOrdersID();
            int cheeseID = orderDetail.getCheese().getCheeseID();
            setCheeseOrder(orderID, cheeseID, extraQuantity);
        } else {
            int minQuantity = orderDetail1.getQuantity() - orderDetail.getQuantity();
            deleteCheeseOrder(orderDetail.getOrderDetailID(), minQuantity);
        }
    }
}
