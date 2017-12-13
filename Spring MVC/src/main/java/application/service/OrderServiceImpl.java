/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import application.helper.OrderEdit;
import application.model.Client;
import application.model.OrderDetail;
import application.model.Orders;
import application.model.repository.ClientRepository;
import application.model.repository.OrderDetailRepository;
import application.model.repository.OrdersRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gerben
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailDao;

    @Autowired
    private OrdersRepository orderDao;

    @Autowired
    private ClientRepository clientDao;

    @Autowired
    private OrderEdit edit;

    @Autowired
    private OrderDetail orderdetail;

    public void addOrder(int clientID, Orders newOrder) {
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setProcessedDate(LocalDateTime.now().plusDays(7));
        newOrder.setTotalPrice(BigDecimal.ZERO);
        Client cat = clientDao.findOne(clientID);
        newOrder.setClient(cat);
        orderDao.save(newOrder);
    }

    public void adminAddOrder(int clientID, Orders newOrder) {
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setProcessedDate(LocalDateTime.now().plusDays(7));
        newOrder.setTotalPrice(BigDecimal.ZERO);
        Client cat = clientDao.findOne(clientID);
        newOrder.setClient(cat);
        orderDao.save(newOrder);
    }

    public void removeOrder(int orderID) {
        List<OrderDetail> orderDetails = orderDao.findOne(orderID).getOrderDetails();
        Iterator<OrderDetail> iterator = orderDetails.iterator();
        while (iterator.hasNext()) {
            orderdetail = iterator.next();
            int id = orderdetail.getOrderDetailID();
            int q = orderdetail.getQuantity();
            edit.deleteCheeseOrder(id, q);
            orderDetailDao.delete(id);
        }
        orderDao.delete(orderID);
    }

}
