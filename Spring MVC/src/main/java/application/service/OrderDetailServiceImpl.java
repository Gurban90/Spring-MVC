/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import application.helper.OrderEdit;
import application.model.Cheese;
import application.model.OrderDetail;
import application.model.Orders;
import application.model.repository.CheeseRepository;
import application.model.repository.OrderDetailRepository;
import application.model.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gerben
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    private OrderDetailRepository orderDetailDao;

    @Autowired
    private CheeseRepository cheeseDao;

    @Autowired
    private OrdersRepository orderDao;

    @Autowired
    private OrderEdit edit;

    public void addOrderDetail(int orderID, int cheeseID, OrderDetail newOrderDetail) {
        Orders ord = orderDao.findOne(orderID);
        newOrderDetail.setOrders(ord);
        Cheese ch = cheeseDao.findOne(cheeseID);
        newOrderDetail.setCheese(ch);
        edit.setCheeseOrder(orderID, cheeseID, newOrderDetail.getQuantity());
        orderDetailDao.save(newOrderDetail);
    }

    public void removeOrderDetail(int orderDetailID, int quantity) {
        edit.deleteCheeseOrder(orderDetailID, quantity);
        orderDetailDao.delete(orderDetailID);
    }

    public void editOrderDetail(OrderDetail editOrderDetail) {
        edit.editCheeseOrder(editOrderDetail);
        orderDetailDao.save(editOrderDetail);
    }

}
